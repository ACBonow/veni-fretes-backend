# Propostas de Melhorias - VeniFretes Backend

## 📊 Status Atual do Sistema de Tracking

### ✅ O que JÁ temos implementado:

1. **EventoTracking** - Tabela que registra eventos:
   - `VISUALIZACAO_PERFIL` - Quando alguém visualiza o perfil do freteiro
   - `CLIQUE_WHATSAPP` - Quando clicam no botão WhatsApp
   - `CLIQUE_TELEFONE` - Quando clicam no telefone
   - `CLIQUE_LINK_EXTERNO` - Cliques em links externos

2. **Dados capturados por evento**:
   - IP do usuário
   - User Agent (navegador/dispositivo)
   - Origem (de onde veio o clique: "listagem", "perfil_detalhado", "home", etc.)
   - Referer (URL anterior)
   - Timestamp (data/hora)

3. **Contadores no Freteiro**:
   - `totalVisualizacoes` - Total de visualizações do perfil
   - `totalCliques` - Total de cliques (WhatsApp + Telefone)
   - `cliquesWhatsApp` - Cliques específicos no WhatsApp
   - `cliquesTelefone` - Cliques específicos no telefone

4. **Sistema de Notificações**:
   - Quando há CLIQUE_WHATSAPP ou CLIQUE_TELEFONE, o freteiro recebe notificação (via WhatsApp stub)

---

## 🎯 MELHORIA 1: Sistema de Pontuação/Leilão para Ranking

### Problema Atual
O ranking atual considera apenas:
1. Tipo de plano (Master > Premium > Padrão > Básico)
2. Completude do perfil (%)
3. Avaliação média
4. ID (rotação em caso de empate)

**Limitação**: Freteiros do mesmo plano na mesma cidade ficam empatados pela completude/avaliação.

### Solução: Sistema de Pontos Extras

#### 1. Nova entidade: `PontosRanking`

```java
@Entity
@Table(name = "pontos_ranking")
public class PontosRanking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "freteiro_id", nullable = false)
    private Freteiro freteiro;

    @Column(nullable = false)
    private String cidade; // Pontos são por cidade

    @Column(nullable = false)
    private Integer pontosAtivos = 0; // Pontos comprados e ativos

    @Column
    private Integer pontosGastos = 0; // Total histórico gasto

    @Column
    private LocalDateTime expiraEm; // Data de expiração dos pontos (ex: 30 dias)

    @Column
    private LocalDateTime ultimaCompra;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
```

#### 2. Nova entidade: `HistoricoCompraPontos`

```java
@Entity
@Table(name = "historico_compra_pontos")
public class HistoricoCompraPontos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "freteiro_id")
    private Freteiro freteiro;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private Integer quantidadePontos;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorPago;

    @Enumerated(EnumType.STRING)
    private MetodoPagamento metodoPagamento;

    @Column
    private String transacaoId; // ID da transação PagBank

    @Enumerated(EnumType.STRING)
    private StatusPagamento status; // PENDENTE, APROVADO, RECUSADO

    @Column
    private Integer posicaoEstimadaAntes;

    @Column
    private Integer posicaoEstimadaDepois;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
```

#### 3. Nova coluna em `Freteiro`:

```sql
ALTER TABLE freteiros ADD COLUMN pontos_ranking_total INTEGER DEFAULT 0;
```

#### 4. Novo Ranking (Query Atualizada):

```java
@Query("""
    SELECT DISTINCT f, pr.pontosAtivos as pontos
    FROM Freteiro f
    LEFT JOIN f.assinaturas a
    LEFT JOIN PontosRanking pr ON pr.freteiro = f AND pr.cidade = :cidade
    WHERE f.ativo = true
    AND (:cidade IS NULL OR LOWER(f.cidade) = LOWER(:cidade))
    AND (a IS NULL OR a.status = 'ATIVA')
    ORDER BY
        -- 1. Tipo de Plano
        CASE COALESCE(a.plano.tipo, 'BASICO')
            WHEN 'MASTER' THEN 4
            WHEN 'PREMIUM' THEN 3
            WHEN 'PADRAO' THEN 2
            ELSE 1
        END DESC,
        -- 2. Pontos Extras (NOVO!)
        COALESCE(pr.pontosAtivos, 0) DESC,
        -- 3. Completude
        f.porcentagemCompletude DESC,
        -- 4. Avaliação
        f.avaliacaoMedia DESC,
        -- 5. Rotação por ID
        f.id ASC
""")
Page<Freteiro> findFreteirosRankeadosComPontos(...);
```

#### 5. Endpoints Novos:

**a) Simular Posição com Pontos**
```
GET /api/freteiro/ranking/simular?cidade=Pelotas&pontosAdicionais=100

Response:
{
  "posicaoAtual": 8,
  "posicaoComPontos": 3,
  "pontosNecessariosProximaPosicao": 50,
  "valorPorPonto": 0.50,
  "valorTotal": 50.00,
  "concorrentesNaFrente": [
    {
      "nome": "José Santos",
      "pontos": 150,
      "plano": "MASTER"
    },
    {
      "nome": "Maria Oliveira",
      "pontos": 120,
      "plano": "MASTER"
    }
  ]
}
```

**b) Comprar Pontos**
```
POST /api/freteiro/pontos/comprar

Request:
{
  "cidade": "Pelotas",
  "quantidadePontos": 100,
  "metodoPagamento": "PIX"
}

Response:
{
  "transacaoId": "TXN-12345",
  "valorTotal": 50.00,
  "qrCodePix": "00020126580014BR.GOV.BCB.PIX...",
  "linkPagamento": "https://pagbank.com/...",
  "posicaoEstimada": 3,
  "expiraEm": "2025-01-28T00:00:00Z"
}
```

**c) Histórico de Compras**
```
GET /api/freteiro/pontos/historico?cidade=Pelotas

Response:
{
  "pontosAtivos": 100,
  "pontosGastos": 50,
  "expiraEm": "2025-01-28T00:00:00Z",
  "compras": [
    {
      "data": "2024-12-28T10:00:00Z",
      "pontos": 100,
      "valorPago": 50.00,
      "status": "APROVADO",
      "posicaoAntes": 8,
      "posicaoDepois": 3
    }
  ]
}
```

#### 6. Regras de Negócio:

- **Só planos MASTER podem comprar pontos**
- **Pontos são específicos por cidade** (não transferíveis)
- **Pontos expiram em 30 dias** (ou renovação mensal)
- **Valor por ponto**: R$ 0,50 (ajustável)
- **Mínimo de compra**: 10 pontos (R$ 5,00)
- **Máximo de compra**: 500 pontos por transação
- **Limite mensal**: 1000 pontos por cidade

#### 7. Vantagens do Sistema:

✅ **Freteiros Master** podem se destacar ainda mais pagando extra
✅ **Leilão natural** - quem paga mais fica mais visível
✅ **Temporário** - pontos expiram, incentivando compras recorrentes
✅ **Por cidade** - competição justa em cada região
✅ **Simulação antes de pagar** - transparência total
✅ **Receita extra** para a plataforma

---

## 📈 MELHORIA 2: Analytics Avançado

### O que adicionar ao `TipoEvento`:

```java
public enum TipoEvento {
    // Já existentes
    VISUALIZACAO_PERFIL,
    CLIQUE_WHATSAPP,
    CLIQUE_TELEFONE,
    CLIQUE_LINK_EXTERNO,

    // NOVOS - Navegação
    CLIQUE_CARD,              // Clique no card do freteiro na listagem
    VISUALIZACAO_LISTA,       // Apareceu na lista (impressão)
    BUSCA_REALIZADA,          // Usuário fez busca (track query)
    FILTRO_APLICADO,          // Aplicou filtros

    // NOVOS - Ações
    COMPARTILHAR_PERFIL,      // Compartilhou perfil
    FAVORITAR,                // Adicionou aos favoritos
    COPIAR_TELEFONE,          // Copiou telefone

    // NOVOS - Páginas
    ACESSO_HOME,              // Entrou na home
    ACESSO_SOBRE,             // Página sobre
    ACESSO_FAQ,               // FAQ
    ACESSO_PLANOS,            // Página de planos

    // NOVOS - Conversão
    INICIO_CADASTRO,          // Começou cadastro
    CADASTRO_CONCLUIDO,       // Concluiu cadastro
    UPGRADE_PLANO,            // Fez upgrade de plano
}
```

### Nova Tabela: `AnalyticsGeral`

Para métricas globais da plataforma (não ligadas a freteiro específico):

```java
@Entity
@Table(name = "analytics_geral")
public class AnalyticsGeral {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoEventoGeral tipo;

    private String pagina;        // "/", "/freteiros", "/sobre"
    private String ip;
    private String userAgent;
    private String cidade;        // Derivado do IP (opcional)
    private String estado;
    private String referer;
    private String utmSource;     // Google, Facebook, etc
    private String utmMedium;     // CPC, organic, email
    private String utmCampaign;   // Nome da campanha

    @CreationTimestamp
    private LocalDateTime createdAt;
}
```

### Dashboard - Backend vs Frontend

#### **Backend deve fornecer:**

✅ **APIs de agregação** (dados já processados)
✅ **Filtros por período** (hoje, semana, mês, custom)
✅ **Cache** (Redis) para queries pesadas
✅ **Segurança** (apenas admin/freteiro dono)

#### **Frontend deve:**

✅ **Enviar eventos** via API REST
✅ **Exibir gráficos** (charts.js, recharts, etc)
✅ **Atualização em tempo real** (opcional - polling ou SSE)

---

## 📊 MELHORIA 3: Endpoints de Dashboard/Analytics

### Para ADMIN (Visão Geral da Plataforma)

```
GET /api/admin/analytics/overview?periodo=30d

Response:
{
  "totalFreteiros": 150,
  "totalFreteirosPorPlano": {
    "BASICO": 100,
    "PADRAO": 30,
    "PREMIUM": 15,
    "MASTER": 5
  },
  "totalUsuarios": 500,
  "novosCadastrosHoje": 5,
  "totalVisualizacoesHoje": 1500,
  "totalCliquesHoje": 300,
  "taxaConversaoVisualiza\u00e7\u00e3oParaClique": 20.0,
  "cidadesMaisAtivas": [
    {"cidade": "Pelotas", "freteiros": 30, "visualizacoes": 500},
    {"cidade": "Porto Alegre", "freteiros": 25, "visualizacoes": 400}
  ],
  "receitaMensal": 5000.00,
  "receitaPontos": 500.00
}
```

```
GET /api/admin/analytics/eventos?periodo=7d&tipo=CLIQUE_WHATSAPP

Response:
{
  "totalEventos": 1500,
  "eventosPorDia": [
    {"data": "2024-12-21", "total": 200},
    {"data": "2024-12-22", "total": 250},
    ...
  ],
  "topFreteiros": [
    {"nome": "João Silva", "total": 50},
    {"nome": "Maria Santos", "total": 45}
  ]
}
```

### Para FRETEIRO (Visão do Próprio Perfil)

```
GET /api/freteiro/analytics?periodo=30d

Response:
{
  "visualizacoes": {
    "total": 500,
    "porDia": [...],
    "porOrigem": {
      "listagem": 300,
      "busca_google": 150,
      "compartilhamento": 50
    }
  },
  "cliques": {
    "whatsapp": 100,
    "telefone": 50,
    "total": 150
  },
  "taxaConversao": 30.0,
  "posicaoRankingAtual": 3,
  "historicoRanking": [
    {"data": "2024-12-21", "posicao": 5},
    {"data": "2024-12-22", "posicao": 4},
    {"data": "2024-12-28", "posicao": 3}
  ],
  "avaliacoes": {
    "media": 4.8,
    "total": 25,
    "distribuicao": {
      "5": 20,
      "4": 3,
      "3": 2,
      "2": 0,
      "1": 0
    }
  },
  "completudePerfil": 85,
  "pontuacao": {
    "pontosAtivos": 100,
    "expiraEm": "2025-01-28"
  }
}
```

---

## 🔧 Implementação Recomendada

### Fase 1: Sistema de Pontos (Prioridade ALTA)
- [ ] Criar entidades `PontosRanking` e `HistoricoCompraPontos`
- [ ] Migration Flyway para novas tabelas
- [ ] Atualizar query de ranking
- [ ] Criar endpoints de simulação e compra
- [ ] Integrar com PagBank (PIX/cartão)
- [ ] Testes

### Fase 2: Analytics Avançado (Prioridade MÉDIA)
- [ ] Adicionar novos tipos de evento
- [ ] Criar tabela `AnalyticsGeral`
- [ ] Endpoints de agregação para admin
- [ ] Endpoints de analytics para freteiro
- [ ] Cache com Redis (opcional)

### Fase 3: Dashboard Frontend (Prioridade BAIXA)
- [ ] Componentes de gráficos
- [ ] Integração com APIs de analytics
- [ ] Página de admin
- [ ] Página de dashboard do freteiro

---

## 🎨 Onde Implementar: Frontend vs Backend

### **BACKEND** (Recomendado para analytics):

✅ **Vantagens**:
- Dados centralizados e seguros
- Fácil adicionar novos clientes (mobile app, etc)
- Processamento pesado no servidor
- Auditoria e compliance
- Cache e otimização

❌ **Desvantagens**:
- Mais chamadas de API
- Latência de rede

### **FRONTEND** (Complementar):

✅ **Vantagens**:
- Eventos de UI/UX (tempo na página, scroll, hover)
- Não precisa backend para tudo
- Google Analytics integration

❌ **Desvantagens**:
- Pode ser bloqueado (AdBlock)
- Menos seguro
- Múltiplas fontes de verdade

### **RECOMENDAÇÃO: Híbrido**

1. **Frontend**: Captura eventos e envia para backend
2. **Backend**: Armazena, processa e fornece APIs
3. **Frontend**: Consome APIs e exibe gráficos

**Exemplo de fluxo**:
```
[Usuário clica em card]
  → Frontend detecta
  → POST /api/tracking { tipo: "CLIQUE_CARD", freteiroId: 1 }
  → Backend salva no banco
  → Backend atualiza contadores
  → Dashboard consulta GET /api/freteiro/analytics
  → Frontend renderiza gráfico
```

---

## 💰 Monetização com Pontos

### Tabela de Preços Sugerida:

| Quantidade | Valor Unitário | Total | Desconto |
|------------|---------------|-------|----------|
| 10 pontos  | R$ 0,50       | R$ 5,00   | -        |
| 50 pontos  | R$ 0,45       | R$ 22,50  | 10%      |
| 100 pontos | R$ 0,40       | R$ 40,00  | 20%      |
| 200 pontos | R$ 0,35       | R$ 70,00  | 30%      |
| 500 pontos | R$ 0,30       | R$ 150,00 | 40%      |

### Exemplo Prático:

**Cenário**: Cidade de Pelotas com 10 freteiros Master

- Freteiro A: Plano MASTER + 0 pontos → Posição 7
- Freteiro B: Plano MASTER + 50 pontos → Posição 4
- Freteiro C: Plano MASTER + 150 pontos → Posição 1

**Freteiro A quer subir para posição 1**:
- Sistema calcula: precisa de 151 pontos
- Custo: 100 pontos (R$ 40) + 51 pontos (R$ 25,50) = **R$ 65,50**
- Sistema mostra: "Você ficará na posição 1 por 30 dias"

---

## 🚀 Próximos Passos

1. **Definir prioridades** com você
2. **Criar migrations** para novas tabelas
3. **Implementar services e controllers**
4. **Testar** com Postman
5. **Integrar com PagBank** (pagamento)
6. **Criar dashboard** no frontend
