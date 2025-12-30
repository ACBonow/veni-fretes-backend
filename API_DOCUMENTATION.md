# VeniFretes API - Documentação Frontend

**Base URL**: `https://veni-fretes-backend.onrender.com`

**Última atualização**: 2025-12-30

---

## 📋 Índice

1. [Autenticação](#autenticação)
2. [Freteiros (Público)](#freteiros-público)
3. [Perfil do Freteiro](#perfil-do-freteiro)
4. [Avaliações](#avaliações)
5. [Planos](#planos)
6. [Localização](#localização)
7. [Tracking](#tracking)
8. [Health Check](#health-check)
9. [Tipos e Enums](#tipos-e-enums)

---

## 🔐 Autenticação

### Registrar Freteiro

**POST** `/api/auth/register`

Cria uma nova conta de freteiro.

**Request Body:**
```json
{
  "nome": "João Silva",
  "email": "joao@example.com",
  "telefone": "51999887766",
  "password": "senha123",
  "cidade": "Pelotas",
  "estado": "RS"
}
```

**Validações:**
- `nome`: 3-100 caracteres
- `email`: formato válido de email
- `telefone`: 10-15 dígitos (apenas números)
- `password`: mínimo 6 caracteres
- `estado`: exatamente 2 caracteres

**Response:** `201 Created`
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIs...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIs...",
  "tokenType": "Bearer",
  "expiresIn": 86400000
}
```

---

### Login

**POST** `/api/auth/login`

Realiza login na plataforma.

**Request Body:**
```json
{
  "email": "joao@example.com",
  "password": "senha123"
}
```

**Response:** `200 OK`
```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiIs...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIs...",
  "tokenType": "Bearer",
  "expiresIn": 86400000
}
```

---

### Obter Usuário Atual

**GET** `/api/auth/me`

Retorna dados do usuário autenticado.

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Response:** `200 OK`
```json
{
  "id": 123,
  "nome": "João Silva",
  "email": "joao@example.com",
  "telefone": "51999887766",
  "role": "ROLE_FRETEIRO",
  "emailVerificado": false,
  "ativo": true,
  "createdAt": "2025-12-30T10:00:00Z"
}
```

---

## 🚚 Freteiros (Público)

Todos os endpoints desta seção são **públicos** (não requerem autenticação).

### Listar Freteiros

**GET** `/api/freteiros`

Lista freteiros com filtros e paginação.

**Query Parameters:**
- `page` (int, default: 0): número da página
- `size` (int, default: 20): itens por página
- `sort` (string, default: "id,asc"): campo e direção de ordenação
- `cidade` (string, opcional): filtrar por cidade
- `estado` (string, opcional): filtrar por estado
- `avaliacaoMinima` (decimal, opcional): filtrar por avaliação mínima
- `busca` (string, opcional): busca textual

**Exemplo:**
```
GET /api/freteiros?page=0&size=12&sort=nome,asc&cidade=Pelotas&estado=RS
```

**Response:** `200 OK`
```json
{
  "content": [
    {
      "id": 36,
      "nome": "João Silva",
      "slug": "joao-silva",
      "telefone": "51999887766",
      "cidade": "Pelotas",
      "estado": "RS",
      "fotoPerfil": "https://example.com/photo.jpg",
      "avaliacaoMedia": 4.5,
      "totalAvaliacoes": 28,
      "tiposVeiculo": ["CAMINHONETE", "VAN"],
      "verificado": true
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 12
  },
  "totalElements": 186,
  "totalPages": 16,
  "last": false
}
```

---

### Buscar Freteiro por ID

**GET** `/api/freteiros/{id}`

Retorna detalhes completos de um freteiro.

**Response:** `200 OK`
```json
{
  "id": 36,
  "nome": "João Silva",
  "slug": "joao-silva",
  "telefone": "51999887766",
  "email": "joao@example.com",
  "cidade": "Pelotas",
  "estado": "RS",
  "areasAtendidas": ["Pelotas", "Rio Grande", "Porto Alegre"],
  "descricao": "Freteiro com 10 anos de experiência...",
  "fotoPerfil": "https://example.com/photo.jpg",
  "fotosVeiculo": [
    "https://example.com/veiculo1.jpg",
    "https://example.com/veiculo2.jpg"
  ],
  "tiposVeiculo": ["CAMINHONETE", "VAN"],
  "tiposServico": ["MUDANCA", "FRETES_LOCAIS", "CARRETO"],
  "porcentagemCompletude": 85,
  "avaliacaoMedia": 4.5,
  "totalAvaliacoes": 28,
  "verificado": true
}
```

---

### Buscar Freteiro por Slug

**GET** `/api/freteiros/slug/{slug}`

Retorna detalhes completos usando o slug do freteiro.

**Exemplo:**
```
GET /api/freteiros/slug/joao-silva
```

**Response:** `200 OK` (mesmo formato do endpoint anterior)

---

## 👤 Perfil do Freteiro

Endpoints para o freteiro **autenticado** gerenciar seu próprio perfil.

**Requer autenticação:** Sim (Bearer Token)

### Obter Meu Perfil

**GET** `/api/freteiro/perfil`

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Response:** `200 OK`
```json
{
  "id": 36,
  "nome": "João Silva",
  "email": "joao@example.com",
  "telefone": "51999887766",
  "role": "ROLE_FRETEIRO",
  "emailVerificado": false,
  "ativo": true,
  "createdAt": "2025-12-30T10:00:00Z",
  "slug": "joao-silva",
  "cidade": "Pelotas",
  "estado": "RS",
  "areasAtendidas": ["Pelotas", "Rio Grande"],
  "descricao": "Freteiro experiente...",
  "fotoPerfil": "https://example.com/photo.jpg",
  "fotosVeiculo": ["https://example.com/veiculo1.jpg"],
  "tiposVeiculo": ["CAMINHONETE"],
  "tiposServico": ["MUDANCA", "CARRETO"],
  "porcentagemCompletude": 75,
  "avaliacaoMedia": 4.5,
  "totalAvaliacoes": 28,
  "verificado": true
}
```

---

### Atualizar Perfil

**PUT** `/api/freteiro/perfil`

Atualiza informações do perfil. Todos os campos são opcionais.

**Headers:**
```
Authorization: Bearer {accessToken}
```

**Request Body:**
```json
{
  "nome": "João Silva Junior",
  "telefone": "51999887766",
  "cidade": "Porto Alegre",
  "estado": "RS",
  "areasAtendidas": ["Porto Alegre", "Canoas", "Gravataí"],
  "descricao": "Atualização da descrição...",
  "tiposVeiculo": ["CAMINHONETE", "VAN"],
  "tiposServico": ["MUDANCA", "FRETES_LOCAIS", "CARRETO"]
}
```

**Response:** `200 OK` (mesmo formato do GET `/api/freteiro/perfil`)

**Nota:** A porcentagem de completude é recalculada automaticamente após a atualização.

---

## ⭐ Avaliações

### Listar Avaliações de um Freteiro

**GET** `/api/freteiros/{id}/avaliacoes`

Lista todas as avaliações **aprovadas** de um freteiro.

**Query Parameters:**
- `page` (int, default: 0)
- `size` (int, default: 10)
- `sort` (string, default: "createdAt,desc")

**Response:** `200 OK`
```json
{
  "content": [
    {
      "id": 1,
      "nota": 5,
      "nomeAvaliador": "Maria Oliveira",
      "comentario": "Excelente serviço! Pontual e cuidadoso.",
      "createdAt": "2025-12-25T14:30:00Z",
      "aprovado": true
    }
  ],
  "totalElements": 28,
  "totalPages": 3
}
```

---

### Criar Avaliação

**POST** `/api/freteiros/{id}/avaliacoes`

Cria uma nova avaliação para o freteiro. A avaliação fica **pendente de aprovação**.

**Request Body:**
```json
{
  "nota": 5,
  "nomeAvaliador": "Maria Oliveira",
  "comentario": "Excelente serviço! Muito cuidadoso com meus móveis."
}
```

**Validações:**
- `nota`: obrigatório, entre 1 e 5
- `nomeAvaliador`: opcional, máximo 100 caracteres
- `comentario`: opcional, máximo 1000 caracteres

**Response:** `201 Created`
```json
{
  "id": 29,
  "nota": 5,
  "nomeAvaliador": "Maria Oliveira",
  "comentario": "Excelente serviço!...",
  "createdAt": "2025-12-30T18:00:00Z",
  "aprovado": false
}
```

---

### Estatísticas de Avaliações

**GET** `/api/freteiros/{id}/avaliacoes/stats`

Retorna estatísticas detalhadas das avaliações.

**Response:** `200 OK`
```json
{
  "totalAvaliacoes": 28,
  "avaliacaoMedia": 4.5,
  "distribuicao": {
    "5": 18,
    "4": 7,
    "3": 2,
    "2": 1,
    "1": 0
  }
}
```

---

## 💳 Planos

### Listar Planos

**GET** `/api/planos`

Lista todos os planos de assinatura disponíveis.

**Response:** `200 OK`
```json
[
  {
    "id": "GRATUITO",
    "nome": "Plano Gratuito",
    "preco": 0.00,
    "features": [
      "Perfil básico",
      "Aparecer nas buscas",
      "Até 2 fotos"
    ],
    "posicaoRanking": 4,
    "limiteFotos": 2,
    "ordem": 1
  },
  {
    "id": "PREMIUM",
    "nome": "Plano Premium",
    "preco": 49.90,
    "features": [
      "Destaque no ranking",
      "Perfil completo",
      "Até 10 fotos",
      "Selo verificado"
    ],
    "posicaoRanking": 1,
    "limiteFotos": 10,
    "ordem": 2
  }
]
```

---

### Buscar Plano por Tipo

**GET** `/api/planos/{tipo}`

Retorna detalhes de um plano específico.

**Tipos disponíveis:**
- `GRATUITO`
- `BASICO`
- `PREMIUM`
- `PROFISSIONAL`

**Exemplo:**
```
GET /api/planos/PREMIUM
```

**Response:** `200 OK` (mesmo formato do objeto de plano acima)

---

## 📍 Localização

### Listar Cidades por Estado

**GET** `/api/location/cidades/{estadoSigla}`

Lista todas as cidades de um estado.

**Exemplo:**
```
GET /api/location/cidades/RS
```

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "nome": "Pelotas",
    "estadoSigla": "RS",
    "estadoNome": "Rio Grande do Sul",
    "codigoIbge": 4314407
  },
  {
    "id": 2,
    "nome": "Porto Alegre",
    "estadoSigla": "RS",
    "estadoNome": "Rio Grande do Sul",
    "codigoIbge": 4314902
  }
]
```

---

### Buscar Cidades

**GET** `/api/location/cidades/search`

Busca cidades por nome (com autocomplete).

**Query Parameters:**
- `nome` (string, obrigatório): termo de busca
- `estado` (string, opcional, default: "RS"): sigla do estado

**Exemplo:**
```
GET /api/location/cidades/search?nome=pel&estado=RS
```

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "nome": "Pelotas",
    "estadoSigla": "RS",
    "estadoNome": "Rio Grande do Sul",
    "codigoIbge": 4314407
  }
]
```

**Nota:** Retorna no máximo 10 resultados.

---

## 📊 Tracking

Endpoints para rastreamento de eventos (visualizações, cliques). **Públicos** (não requerem autenticação).

### Registrar Visualização de Perfil

**POST** `/api/tracking/view`

Registra quando um usuário visualiza o perfil de um freteiro.

**Request Body:**
```json
{
  "freteiroId": 36,
  "origem": "home",
  "referer": "https://veni-fretes-frontend.vercel.app"
}
```

**Campos:**
- `freteiroId` (obrigatório): ID do freteiro visualizado
- `origem` (opcional): origem da visualização (ex: "home", "search", "direct")
- `referer` (opcional): URL de referência

**Response:** `200 OK` (vazio)

**Nota:** IP e User-Agent são capturados automaticamente pelo backend.

---

### Registrar Clique

**POST** `/api/tracking/click`

Registra quando um usuário clica em WhatsApp, telefone ou outro link do freteiro.

**Request Body:**
```json
{
  "freteiroId": 36,
  "tipo": "CLIQUE_WHATSAPP",
  "origem": "perfil"
}
```

**Campos:**
- `freteiroId` (obrigatório): ID do freteiro
- `tipo` (opcional, default: "CLIQUE_WHATSAPP"): tipo do clique
- `origem` (opcional): origem do clique
- `referer` (opcional): URL de referência

**Tipos de clique disponíveis:**
- `CLIQUE_WHATSAPP`: clique no botão WhatsApp
- `CLIQUE_TELEFONE`: clique no número de telefone
- `CLIQUE_LINK_EXTERNO`: clique em link externo
- `CLIQUE_CARD`: clique no card do freteiro

**Response:** `200 OK` (vazio)

---

### Registrar Evento Genérico (Avançado)

**POST** `/api/tracking`

Endpoint genérico para registrar qualquer tipo de evento.

**Request Body:**
```json
{
  "freteiroId": 36,
  "tipo": "VISUALIZACAO_PERFIL",
  "origem": "search",
  "referer": "https://veni-fretes-frontend.vercel.app/busca"
}
```

**Campos:**
- `freteiroId` (obrigatório)
- `tipo` (obrigatório): tipo do evento
- `ip` (opcional, preenchido automaticamente)
- `userAgent` (opcional, preenchido automaticamente)
- `origem` (opcional)
- `referer` (opcional)

**Tipos de evento:**
- `VISUALIZACAO_PERFIL`
- `CLIQUE_WHATSAPP`
- `CLIQUE_TELEFONE`
- `CLIQUE_LINK_EXTERNO`
- `CLIQUE_CARD`

**Response:** `200 OK` (vazio)

---

## 🏥 Health Check

### Root

**GET** `/`

Retorna informações básicas da API.

**Response:** `200 OK`
```json
{
  "message": "VeniFretes API",
  "version": "1.0.0",
  "status": "running"
}
```

---

### Health

**GET** `/health`

Endpoint de health check para monitoramento.

**Response:** `200 OK`
```json
{
  "status": "UP"
}
```

---

## 🔧 Tipos e Enums

### TipoVeiculo

Tipos de veículos disponíveis para freteiros:

```typescript
enum TipoVeiculo {
  MOTO = "MOTO",
  CARRO = "CARRO",
  VAN = "VAN",
  CAMINHONETE = "CAMINHONETE",
  CAMINHAO_TOCO = "CAMINHAO_TOCO",
  CAMINHAO_TRUCK = "CAMINHAO_TRUCK",
  CAMINHAO_CARRETA = "CAMINHAO_CARRETA",
  BAU = "BAU",
  REFRIGERADO = "REFRIGERADO"
}
```

---

### TipoServico

Tipos de serviços oferecidos:

```typescript
enum TipoServico {
  MUDANCA = "MUDANCA",
  ENTREGA = "ENTREGA",
  TRANSPORTE_CARGA = "TRANSPORTE_CARGA",
  FRETES_LOCAIS = "FRETES_LOCAIS",
  FRETES_ESTADUAIS = "FRETES_ESTADUAIS",
  FRETES_INTERESTADUAIS = "FRETES_INTERESTADUAIS",
  CARRETO = "CARRETO",
  TRANSPORTE_MOVEIS = "TRANSPORTE_MOVEIS",
  EMPACOTAMENTO = "EMPACOTAMENTO",
  TRANSPORTE_ANIMAIS = "TRANSPORTE_ANIMAIS"
}
```

---

### Role

Papéis de usuários no sistema:

```typescript
enum Role {
  ROLE_FRETEIRO = "ROLE_FRETEIRO",
  ROLE_ADMIN = "ROLE_ADMIN"
}
```

---

### PlanoTipo

Tipos de planos de assinatura:

```typescript
enum PlanoTipo {
  GRATUITO = "GRATUITO",
  BASICO = "BASICO",
  PREMIUM = "PREMIUM",
  PROFISSIONAL = "PROFISSIONAL"
}
```

---

## 🔒 Autenticação com Bearer Token

Para endpoints que requerem autenticação, inclua o token JWT no header:

```http
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

**Como obter o token:**
1. Faça login via `POST /api/auth/login`
2. Use o campo `accessToken` da resposta
3. Inclua no header de todas as requisições autenticadas

---

## ❌ Tratamento de Erros

A API retorna erros no seguinte formato:

```json
{
  "timestamp": "2025-12-30T18:00:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Email é obrigatório",
  "path": "/api/auth/register"
}
```

**Códigos HTTP comuns:**
- `200 OK`: Sucesso
- `201 Created`: Recurso criado com sucesso
- `400 Bad Request`: Dados de entrada inválidos
- `401 Unauthorized`: Autenticação necessária ou token inválido
- `403 Forbidden`: Sem permissão para acessar o recurso
- `404 Not Found`: Recurso não encontrado
- `500 Internal Server Error`: Erro interno do servidor

---

## 📝 Notas Importantes

1. **Paginação**: A maioria dos endpoints de listagem usa paginação baseada em Spring Data:
   - Parâmetros: `page` (começa em 0), `size`, `sort`
   - Response inclui: `content`, `totalElements`, `totalPages`, `last`

2. **Filtros**: Use query parameters para filtrar resultados (ex: `cidade`, `estado`, `avaliacaoMinima`)

3. **Ordenação**: Use o formato `campo,direção` (ex: `nome,asc`, `createdAt,desc`)

4. **CORS**: A API está configurada para aceitar requisições de `https://veni-fretes-frontend.vercel.app`

5. **Rate Limiting**: Não implementado no momento

6. **Upload de Imagens**: Ainda não implementado. Os campos `fotoPerfil` e `fotosVeiculo` aceitam URLs por enquanto.

---

## 🚀 Próximas Implementações

Funcionalidades planejadas mas ainda não implementadas:

- [ ] Upload de imagens (Cloudinary)
- [ ] Sistema de assinaturas (criar/cancelar)
- [ ] Integração PagBank para pagamentos
- [ ] Webhooks de pagamento
- [ ] Notificações WhatsApp
- [ ] Sistema de favoritos
- [ ] Recuperação de senha
- [ ] Gestão de banners (admin)

---

**Dúvidas ou problemas?** Entre em contato com a equipe de desenvolvimento.
