# Guia de Teste - VeniFretes Backend

## 🚀 Como Executar o Backend

### 1. Subir o Banco de Dados

```bash
cd C:\Users\Arthur\Documents\GitHub\veni-fretes-backend
docker-compose up -d
```

Aguarde alguns segundos até o PostgreSQL estar pronto.

### 2. Compilar o Projeto

```bash
mvn clean install
```

### 3. Executar a Aplicação

```bash
mvn spring-boot:run
```

A aplicação estará rodando em: **http://localhost:8080**

---

## 📚 Acessar Documentação

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs (JSON)**: http://localhost:8080/api-docs
- **pgAdmin**: http://localhost:5050
  - Email: `admin@venifretes.com`
  - Senha: `admin123`

---

## 🧪 Testar Endpoints

### 1. Registrar Novo Freteiro

**Endpoint**: `POST /api/auth/register`

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João Silva",
    "email": "joao@example.com",
    "telefone": "51999999999",
    "password": "senha123",
    "cidade": "Pelotas",
    "estado": "RS"
  }'
```

**Resposta esperada**:
```json
{
  "accessToken": "eyJhbGciOiJIUzI1...",
  "refreshToken": "eyJhbGciOiJIUzI1...",
  "tokenType": "Bearer",
  "expiresIn": 86400000
}
```

### 2. Fazer Login

**Endpoint**: `POST /api/auth/login`

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao@example.com",
    "password": "senha123"
  }'
```

### 3. Listar Freteiros (Público)

**Endpoint**: `GET /api/freteiros`

```bash
curl http://localhost:8080/api/freteiros
```

**Com filtros**:
```bash
curl "http://localhost:8080/api/freteiros?cidade=Pelotas&estado=RS&page=0&size=10"
```

### 4. Buscar Freteiro por ID

**Endpoint**: `GET /api/freteiros/{id}`

```bash
curl http://localhost:8080/api/freteiros/1
```

### 5. Buscar Freteiro por Slug

**Endpoint**: `GET /api/freteiros/slug/{slug}`

```bash
curl http://localhost:8080/api/freteiros/slug/joao-silva
```

### 6. Registrar Evento de Tracking

**Endpoint**: `POST /api/tracking`

```bash
curl -X POST http://localhost:8080/api/tracking \
  -H "Content-Type: application/json" \
  -d '{
    "freteiroId": 1,
    "tipo": "CLIQUE_WHATSAPP",
    "origem": "listagem"
  }'
```

---

## 🔐 Testar Endpoints Autenticados

Para endpoints que requerem autenticação, use o token JWT retornado no login/register:

```bash
curl -X GET http://localhost:8080/api/freteiro/perfil \
  -H "Authorization: Bearer SEU_TOKEN_AQUI"
```

---

## 🐛 Verificar Logs

Os logs da aplicação aparecerão no terminal onde você executou `mvn spring-boot:run`.

Para verificar logs do PostgreSQL:
```bash
docker logs venifretes-postgres
```

---

## 🗄️ Acessar Banco de Dados

### Via psql (linha de comando):

```bash
docker exec -it venifretes-postgres psql -U venifretes -d venifretes
```

### Queries úteis:

```sql
-- Ver todas as tabelas
\dt

-- Ver freteiros cadastrados
SELECT id, nome, email, cidade, slug FROM freteiros;

-- Ver planos disponíveis
SELECT * FROM planos;

-- Ver eventos de tracking
SELECT * FROM eventos_tracking;
```

---

## ✅ Checklist de Funcionalidades

- [ ] Registrar novo freteiro
- [ ] Login com email/senha
- [ ] Listar freteiros (com ranqueamento)
- [ ] Buscar freteiro por ID
- [ ] Buscar freteiro por slug
- [ ] Registrar evento de tracking
- [ ] Verificar notificação WhatsApp nos logs (modo stub)
- [ ] Acessar Swagger UI
- [ ] Verificar dados no banco via pgAdmin

---

## 🔧 Troubleshooting

### Erro: "Cannot find java"
- Verifique se o Java 21 está instalado: `java -version`

### Erro: "Port 8080 already in use"
- Mude a porta em `application-dev.yml`:
```yaml
server:
  port: 8081
```

### Erro: "Connection refused" ao banco
- Verifique se o Docker está rodando
- Rode: `docker-compose up -d`

### Erro de compilação Maven
- Limpe o cache: `mvn clean`
- Re-compile: `mvn clean install -DskipTests`

---

## 📊 Monitoramento

### Actuator Endpoints

- **Health Check**: http://localhost:8080/actuator/health
- **Info**: http://localhost:8080/actuator/info
- **Metrics**: http://localhost:8080/actuator/metrics

---

## 🎯 Próximos Passos

1. Testar todos os endpoints via Swagger
2. Integrar com o frontend Next.js
3. Implementar upload de imagens
4. Configurar integração real com WhatsApp
5. Configurar integração com PagBank
6. Deploy em produção
