# Relatório de Integração - Endpoints Administrativos

## 📋 Sumário Executivo

Este documento descreve os novos endpoints administrativos criados para o dashboard admin e gerenciamento de avaliações. Todos os endpoints requerem autenticação com role `ADMIN`.

**Data:** 2026-01-02
**Versão da API:** 1.0
**Base URL:** `http://localhost:8080/api/admin`

---

## 🔐 Autenticação

Todos os endpoints requerem:
- **Token JWT** válido no header `Authorization: Bearer {token}`
- **Role:** `ADMIN`

### Exemplo de Header
```http
Authorization: Bearer eyJhbGciOiJIUzM4NCJ9...
Content-Type: application/json
```

---

## 📊 Endpoints de Estatísticas

### 1. Estatísticas de Usuários

**Endpoint:** `GET /api/admin/stats/users`

**Descrição:** Retorna estatísticas gerais sobre usuários cadastrados no sistema.

**Request:**
```http
GET /api/admin/stats/users HTTP/1.1
Host: localhost:8080
Authorization: Bearer {token}
```

**Response:** `200 OK`
```json
{
  "totalUsuarios": 1250,
  "totalFreteiros": 850,
  "totalContratantes": 380,
  "totalAdmins": 5,
  "usuariosAtivos": 1100,
  "usuariosInativos": 150,
  "freteirosVerificados": 320,
  "freteirosAtivos": 750
}
```

**Campos:**
| Campo | Tipo | Descrição |
|-------|------|-----------|
| `totalUsuarios` | Long | Total de usuários cadastrados |
| `totalFreteiros` | Long | Total de freteiros |
| `totalContratantes` | Long | Total de contratantes |
| `totalAdmins` | Long | Total de administradores |
| `usuariosAtivos` | Long | Usuários com status ativo |
| `usuariosInativos` | Long | Usuários com status inativo |
| `freteirosVerificados` | Long | Freteiros com badge verificado |
| `freteirosAtivos` | Long | Freteiros ativos |

---

### 2. Métricas de Engajamento

**Endpoint:** `GET /api/admin/stats/engagement`

**Descrição:** Retorna métricas de visualizações, cliques e taxa de conversão.

**Request:**
```http
GET /api/admin/stats/engagement HTTP/1.1
Host: localhost:8080
Authorization: Bearer {token}
```

**Response:** `200 OK`
```json
{
  "totalVisualizacoes": 15430,
  "visualizacoesUltimos30Dias": 3250,
  "totalCliques": 4820,
  "cliquesUltimos30Dias": 980,
  "cliquesWhatsApp": 3100,
  "cliquesTelefone": 1720,
  "taxaConversaoGeral": 0.312
}
```

**Campos:**
| Campo | Tipo | Descrição |
|-------|------|-----------|
| `totalVisualizacoes` | Long | Total histórico de visualizações |
| `visualizacoesUltimos30Dias` | Long | Visualizações dos últimos 30 dias |
| `totalCliques` | Long | Total histórico de cliques |
| `cliquesUltimos30Dias` | Long | Cliques dos últimos 30 dias |
| `cliquesWhatsApp` | Long | Cliques no botão WhatsApp |
| `cliquesTelefone` | Long | Cliques no botão Telefone |
| `taxaConversaoGeral` | Double | Taxa de conversão (cliques/visualizações) |

---

### 3. Métricas Financeiras

**Endpoint:** `GET /api/admin/stats/financial`

**Descrição:** Retorna receita de assinaturas, vendas de pontos e totais financeiros.

**Request:**
```http
GET /api/admin/stats/financial HTTP/1.1
Host: localhost:8080
Authorization: Bearer {token}
```

**Response:** `200 OK`
```json
{
  "receitaTotalAssinaturas": 12500.00,
  "receitaUltimos30Dias": 3450.00,
  "assinaturasAtivas": 125,
  "receitaPontos": 5680.00,
  "totalPontosVendidos": 15420
}
```

**Campos:**
| Campo | Tipo | Descrição |
|-------|------|-----------|
| `receitaTotalAssinaturas` | BigDecimal | Receita total de assinaturas ativas |
| `receitaUltimos30Dias` | BigDecimal | Receita dos últimos 30 dias |
| `assinaturasAtivas` | Long | Quantidade de assinaturas ativas |
| `receitaPontos` | BigDecimal | Receita total de vendas de pontos |
| `totalPontosVendidos` | Integer | Total de pontos vendidos |

---

### 4. Métricas de Crescimento

**Endpoint:** `GET /api/admin/stats/growth`

**Descrição:** Retorna estatísticas de crescimento e novos cadastros por período.

**Request:**
```http
GET /api/admin/stats/growth HTTP/1.1
Host: localhost:8080
Authorization: Bearer {token}
```

**Response:** `200 OK`
```json
{
  "cadastrosUltimos7Dias": 45,
  "cadastrosUltimos30Dias": 182,
  "cadastrosUltimos90Dias": 534,
  "cadastrosPorDia": null,
  "taxaCrescimentoMensal": 12.5
}
```

**Campos:**
| Campo | Tipo | Descrição |
|-------|------|-----------|
| `cadastrosUltimos7Dias` | Long | Cadastros dos últimos 7 dias |
| `cadastrosUltimos30Dias` | Long | Cadastros dos últimos 30 dias |
| `cadastrosUltimos90Dias` | Long | Cadastros dos últimos 90 dias |
| `cadastrosPorDia` | Array | Lista de cadastros por dia (atualmente null) |
| `taxaCrescimentoMensal` | Double | Taxa de crescimento mensal em % |

---

### 5. Estatísticas de Avaliações

**Endpoint:** `GET /api/admin/stats/reviews`

**Descrição:** Retorna total de avaliações, aprovadas, pendentes e distribuição por nota.

**Request:**
```http
GET /api/admin/stats/reviews HTTP/1.1
Host: localhost:8080
Authorization: Bearer {token}
```

**Response:** `200 OK`
```json
{
  "totalAvaliacoes": 456,
  "avaliacoesAprovadas": 398,
  "avaliacoesPendentes": 58,
  "notaMediaGeral": 4.30,
  "distribuicaoPorNota": {
    "1": 5,
    "2": 12,
    "3": 45,
    "4": 156,
    "5": 180
  }
}
```

**Campos:**
| Campo | Tipo | Descrição |
|-------|------|-----------|
| `totalAvaliacoes` | Long | Total de avaliações no sistema |
| `avaliacoesAprovadas` | Long | Avaliações aprovadas e publicadas |
| `avaliacoesPendentes` | Long | Avaliações pendentes de moderação |
| `notaMediaGeral` | BigDecimal | Nota média geral (0-5) |
| `distribuicaoPorNota` | Map | Distribuição de avaliações por nota |

---

## 📝 Endpoints de Gerenciamento de Avaliações

### 1. Listar Todas as Avaliações

**Endpoint:** `GET /api/admin/reviews`

**Descrição:** Lista todas as avaliações com paginação e filtro opcional por status.

**Query Parameters:**
| Parâmetro | Tipo | Obrigatório | Default | Descrição |
|-----------|------|-------------|---------|-----------|
| `aprovado` | Boolean | Não | null | Filtrar por status (true=aprovadas, false=pendentes, null=todas) |
| `page` | Integer | Não | 0 | Número da página (inicia em 0) |
| `size` | Integer | Não | 20 | Tamanho da página |
| `sortBy` | String | Não | createdAt | Campo para ordenação |
| `direction` | String | Não | DESC | Direção da ordenação (ASC/DESC) |

**Request:**
```http
GET /api/admin/reviews?aprovado=false&page=0&size=20 HTTP/1.1
Host: localhost:8080
Authorization: Bearer {token}
```

**Response:** `200 OK`
```json
{
  "content": [
    {
      "id": 123,
      "freteiroNome": "João Silva",
      "freteiroSlug": "joao-silva-sp",
      "contratanteNome": "Maria Santos",
      "nota": 5,
      "comentario": "Excelente serviço, muito profissional!",
      "aprovado": false,
      "createdAt": "2026-01-01T14:30:00"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 20,
    "sort": {
      "sorted": true,
      "unsorted": false,
      "empty": false
    }
  },
  "totalPages": 3,
  "totalElements": 58,
  "last": false,
  "first": true,
  "numberOfElements": 20
}
```

---

### 2. Buscar Avaliação por ID

**Endpoint:** `GET /api/admin/reviews/{id}`

**Descrição:** Retorna os detalhes de uma avaliação específica.

**Request:**
```http
GET /api/admin/reviews/123 HTTP/1.1
Host: localhost:8080
Authorization: Bearer {token}
```

**Response:** `200 OK`
```json
{
  "id": 123,
  "freteiroNome": "João Silva",
  "freteiroSlug": "joao-silva-sp",
  "contratanteNome": "Maria Santos",
  "nota": 5,
  "comentario": "Excelente serviço, muito profissional!",
  "aprovado": false,
  "createdAt": "2026-01-01T14:30:00"
}
```

**Error Response:** `404 Not Found`
```json
{
  "timestamp": "2026-01-02T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Avaliação não encontrada"
}
```

---

### 3. Aprovar Avaliação

**Endpoint:** `PUT /api/admin/reviews/{id}/approve`

**Descrição:** Marca uma avaliação como aprovada e a publica no perfil do freteiro.

**Request:**
```http
PUT /api/admin/reviews/123/approve HTTP/1.1
Host: localhost:8080
Authorization: Bearer {token}
```

**Response:** `200 OK`
```json
{
  "id": 123,
  "freteiroNome": "João Silva",
  "freteiroSlug": "joao-silva-sp",
  "contratanteNome": "Maria Santos",
  "nota": 5,
  "comentario": "Excelente serviço, muito profissional!",
  "aprovado": true,
  "createdAt": "2026-01-01T14:30:00"
}
```

---

### 4. Rejeitar Avaliação

**Endpoint:** `PUT /api/admin/reviews/{id}/reject`

**Descrição:** Marca uma avaliação como rejeitada e a remove da publicação.

**Request:**
```http
PUT /api/admin/reviews/123/reject HTTP/1.1
Host: localhost:8080
Authorization: Bearer {token}
```

**Response:** `200 OK`
```json
{
  "id": 123,
  "freteiroNome": "João Silva",
  "freteiroSlug": "joao-silva-sp",
  "contratanteNome": "Maria Santos",
  "nota": 5,
  "comentario": "Excelente serviço, muito profissional!",
  "aprovado": false,
  "createdAt": "2026-01-01T14:30:00"
}
```

---

### 5. Deletar Avaliação

**Endpoint:** `DELETE /api/admin/reviews/{id}`

**Descrição:** Remove permanentemente uma avaliação do sistema.

**Request:**
```http
DELETE /api/admin/reviews/123 HTTP/1.1
Host: localhost:8080
Authorization: Bearer {token}
```

**Response:** `204 No Content`

**Error Response:** `404 Not Found`
```json
{
  "timestamp": "2026-01-02T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Avaliação não encontrada"
}
```

---

## 🔒 Códigos de Status HTTP

| Código | Descrição | Quando ocorre |
|--------|-----------|---------------|
| `200 OK` | Sucesso | Requisição processada com sucesso |
| `204 No Content` | Sucesso sem conteúdo | Deletado com sucesso |
| `401 Unauthorized` | Não autorizado | Token inválido ou ausente |
| `403 Forbidden` | Acesso negado | Usuário não é ADMIN |
| `404 Not Found` | Não encontrado | Recurso não existe |
| `500 Internal Server Error` | Erro do servidor | Erro interno da aplicação |

---

## 💻 Exemplos de Integração Frontend

### React/TypeScript - Interfaces

```typescript
// Types para as respostas
interface UserStatsResponse {
  totalUsuarios: number;
  totalFreteiros: number;
  totalContratantes: number;
  totalAdmins: number;
  usuariosAtivos: number;
  usuariosInativos: number;
  freteirosVerificados: number;
  freteirosAtivos: number;
}

interface EngagementStatsResponse {
  totalVisualizacoes: number;
  visualizacoesUltimos30Dias: number;
  totalCliques: number;
  cliquesUltimos30Dias: number;
  cliquesWhatsApp: number;
  cliquesTelefone: number;
  taxaConversaoGeral: number;
}

interface FinancialStatsResponse {
  receitaTotalAssinaturas: number;
  receitaUltimos30Dias: number;
  assinaturasAtivas: number;
  receitaPontos: number;
  totalPontosVendidos: number;
}

interface GrowthStatsResponse {
  cadastrosUltimos7Dias: number;
  cadastrosUltimos30Dias: number;
  cadastrosUltimos90Dias: number;
  cadastrosPorDia: Array<{data: string; quantidade: number}> | null;
  taxaCrescimentoMensal: number;
}

interface ReviewStatsResponse {
  totalAvaliacoes: number;
  avaliacoesAprovadas: number;
  avaliacoesPendentes: number;
  notaMediaGeral: number;
  distribuicaoPorNota: Record<number, number>;
}

interface ReviewListResponse {
  id: number;
  freteiroNome: string;
  freteiroSlug: string;
  contratanteNome: string;
  nota: number;
  comentario: string;
  aprovado: boolean;
  createdAt: string;
}

interface PaginatedResponse<T> {
  content: T[];
  pageable: {
    pageNumber: number;
    pageSize: number;
  };
  totalPages: number;
  totalElements: number;
  last: boolean;
  first: boolean;
  numberOfElements: number;
}
```

### React - Service Layer

```typescript
// adminService.ts
import axios from 'axios';

const API_URL = 'http://localhost:8080/api/admin';

// Configurar interceptor para adicionar token
axios.interceptors.request.use((config) => {
  const token = localStorage.getItem('adminToken');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

export class AdminService {
  // Estatísticas
  static async getUserStats(): Promise<UserStatsResponse> {
    const response = await axios.get(`${API_URL}/stats/users`);
    return response.data;
  }

  static async getEngagementStats(): Promise<EngagementStatsResponse> {
    const response = await axios.get(`${API_URL}/stats/engagement`);
    return response.data;
  }

  static async getFinancialStats(): Promise<FinancialStatsResponse> {
    const response = await axios.get(`${API_URL}/stats/financial`);
    return response.data;
  }

  static async getGrowthStats(): Promise<GrowthStatsResponse> {
    const response = await axios.get(`${API_URL}/stats/growth`);
    return response.data;
  }

  static async getReviewStats(): Promise<ReviewStatsResponse> {
    const response = await axios.get(`${API_URL}/stats/reviews`);
    return response.data;
  }

  // Gerenciamento de avaliações
  static async listReviews(
    aprovado?: boolean,
    page: number = 0,
    size: number = 20
  ): Promise<PaginatedResponse<ReviewListResponse>> {
    const params = new URLSearchParams({
      page: page.toString(),
      size: size.toString(),
    });

    if (aprovado !== undefined) {
      params.append('aprovado', aprovado.toString());
    }

    const response = await axios.get(`${API_URL}/reviews?${params}`);
    return response.data;
  }

  static async getReview(id: number): Promise<ReviewListResponse> {
    const response = await axios.get(`${API_URL}/reviews/${id}`);
    return response.data;
  }

  static async approveReview(id: number): Promise<ReviewListResponse> {
    const response = await axios.put(`${API_URL}/reviews/${id}/approve`);
    return response.data;
  }

  static async rejectReview(id: number): Promise<ReviewListResponse> {
    const response = await axios.put(`${API_URL}/reviews/${id}/reject`);
    return response.data;
  }

  static async deleteReview(id: number): Promise<void> {
    await axios.delete(`${API_URL}/reviews/${id}`);
  }
}
```

### React - Exemplo de Componente Dashboard

```tsx
// AdminDashboard.tsx
import React, { useEffect, useState } from 'react';
import { AdminService } from './services/adminService';

export const AdminDashboard: React.FC = () => {
  const [userStats, setUserStats] = useState<UserStatsResponse | null>(null);
  const [engagementStats, setEngagementStats] = useState<EngagementStatsResponse | null>(null);
  const [financialStats, setFinancialStats] = useState<FinancialStatsResponse | null>(null);
  const [reviewStats, setReviewStats] = useState<ReviewStatsResponse | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadStats();
  }, []);

  const loadStats = async () => {
    try {
      setLoading(true);
      const [users, engagement, financial, reviews] = await Promise.all([
        AdminService.getUserStats(),
        AdminService.getEngagementStats(),
        AdminService.getFinancialStats(),
        AdminService.getReviewStats(),
      ]);

      setUserStats(users);
      setEngagementStats(engagement);
      setFinancialStats(financial);
      setReviewStats(reviews);
    } catch (error) {
      console.error('Erro ao carregar estatísticas:', error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) return <div>Carregando...</div>;

  return (
    <div className="admin-dashboard">
      <h1>Dashboard Administrativo</h1>

      {/* Cards de Estatísticas */}
      <div className="stats-grid">
        <div className="stat-card">
          <h3>Total de Usuários</h3>
          <p className="stat-value">{userStats?.totalUsuarios}</p>
          <p className="stat-label">cadastrados</p>
        </div>

        <div className="stat-card">
          <h3>Freteiros Ativos</h3>
          <p className="stat-value">{userStats?.freteirosVerificados}</p>
          <p className="stat-label">verificados</p>
        </div>

        <div className="stat-card">
          <h3>Avaliações</h3>
          <p className="stat-value">{reviewStats?.totalAvaliacoes}</p>
          <p className="stat-label">no total</p>
        </div>

        <div className="stat-card">
          <h3>Visualizações</h3>
          <p className="stat-value">{engagementStats?.visualizacoesUltimos30Dias}</p>
          <p className="stat-label">últimos 30 dias</p>
        </div>
      </div>

      {/* Mais seções... */}
    </div>
  );
};
```

### React - Exemplo de Componente de Gerenciamento de Avaliações

```tsx
// ReviewsManagement.tsx
import React, { useEffect, useState } from 'react';
import { AdminService } from './services/adminService';

export const ReviewsManagement: React.FC = () => {
  const [reviews, setReviews] = useState<PaginatedResponse<ReviewListResponse> | null>(null);
  const [filter, setFilter] = useState<boolean | undefined>(undefined);
  const [page, setPage] = useState(0);

  useEffect(() => {
    loadReviews();
  }, [filter, page]);

  const loadReviews = async () => {
    try {
      const data = await AdminService.listReviews(filter, page, 20);
      setReviews(data);
    } catch (error) {
      console.error('Erro ao carregar avaliações:', error);
    }
  };

  const handleApprove = async (id: number) => {
    try {
      await AdminService.approveReview(id);
      loadReviews(); // Recarregar lista
    } catch (error) {
      console.error('Erro ao aprovar avaliação:', error);
    }
  };

  const handleReject = async (id: number) => {
    try {
      await AdminService.rejectReview(id);
      loadReviews();
    } catch (error) {
      console.error('Erro ao rejeitar avaliação:', error);
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('Tem certeza que deseja deletar esta avaliação?')) {
      try {
        await AdminService.deleteReview(id);
        loadReviews();
      } catch (error) {
        console.error('Erro ao deletar avaliação:', error);
      }
    }
  };

  return (
    <div className="reviews-management">
      <h1>Gerenciamento de Avaliações</h1>

      {/* Filtros */}
      <div className="filters">
        <button onClick={() => setFilter(undefined)}>Todas</button>
        <button onClick={() => setFilter(true)}>Aprovadas</button>
        <button onClick={() => setFilter(false)}>Pendentes</button>
      </div>

      {/* Lista de avaliações */}
      <div className="reviews-list">
        {reviews?.content.map((review) => (
          <div key={review.id} className="review-item">
            <div className="review-header">
              <h3>{review.freteiroNome}</h3>
              <span className="rating">{'⭐'.repeat(review.nota)}</span>
            </div>
            <p className="reviewer">Por: {review.contratanteNome}</p>
            <p className="comment">{review.comentario}</p>
            <p className="date">{new Date(review.createdAt).toLocaleDateString('pt-BR')}</p>

            <div className="actions">
              {!review.aprovado && (
                <button onClick={() => handleApprove(review.id)}>
                  Aprovar
                </button>
              )}
              {review.aprovado && (
                <button onClick={() => handleReject(review.id)}>
                  Rejeitar
                </button>
              )}
              <button onClick={() => handleDelete(review.id)} className="danger">
                Deletar
              </button>
            </div>
          </div>
        ))}
      </div>

      {/* Paginação */}
      <div className="pagination">
        <button
          disabled={reviews?.first}
          onClick={() => setPage(page - 1)}
        >
          Anterior
        </button>
        <span>Página {(reviews?.pageable.pageNumber || 0) + 1} de {reviews?.totalPages}</span>
        <button
          disabled={reviews?.last}
          onClick={() => setPage(page + 1)}
        >
          Próxima
        </button>
      </div>
    </div>
  );
};
```

---

## 🧪 Testando os Endpoints

### Usando cURL

```bash
# 1. Fazer login como admin e obter token
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao.silva@example.com",
    "password": "senha123"
  }'

# 2. Usar o token para acessar estatísticas
curl -X GET http://localhost:8080/api/admin/stats/users \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"

# 3. Listar avaliações pendentes
curl -X GET "http://localhost:8080/api/admin/reviews?aprovado=false&page=0&size=20" \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"

# 4. Aprovar uma avaliação
curl -X PUT http://localhost:8080/api/admin/reviews/123/approve \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"

# 5. Deletar uma avaliação
curl -X DELETE http://localhost:8080/api/admin/reviews/123 \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

### Usando Postman

1. **Criar Collection:** "Admin API"
2. **Configurar Authorization:**
   - Type: Bearer Token
   - Token: `{{adminToken}}`
3. **Criar Requests:**
   - Importar os exemplos de cURL acima
   - Salvar o token em uma variável de ambiente

---

## 📌 Notas Importantes

### Segurança
- ✅ Todos os endpoints requerem autenticação JWT
- ✅ Apenas usuários com role `ADMIN` podem acessar
- ✅ Tokens expiram após 24 horas
- ⚠️ Não compartilhe tokens de admin

### Performance
- ✅ Queries otimizadas com índices no banco
- ✅ Paginação implementada para grandes volumes
- ✅ Transações read-only para estatísticas
- ⚠️ Cache pode ser implementado no futuro

### Limitações Conhecidas
- `cadastrosPorDia` em GrowthStats retorna `null` (implementação futura)
- Estatísticas são calculadas em tempo real (sem cache)
- Não há filtro por data customizada (apenas períodos fixos)

### Próximos Passos Sugeridos
1. Implementar cache Redis para estatísticas
2. Adicionar filtros de data customizados
3. Implementar gráficos de cadastros por dia
4. Adicionar exportação de relatórios (CSV/PDF)
5. Implementar audit log de ações administrativas

---

## 📞 Suporte

Para dúvidas ou problemas com a integração:
- Verificar logs do backend em `application.log`
- Consultar Swagger UI: `http://localhost:8080/swagger-ui.html`
- Verificar se o usuário tem role `ADMIN` correta

---

**Documento gerado em:** 2026-01-02
**Última atualização:** Commit `5116cf8`
