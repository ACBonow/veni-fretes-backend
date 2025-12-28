# QuickStart - VeniFretes Backend com NeonDB

## ✅ Backend está FUNCIONANDO!

O backend está configurado e rodando com sucesso no NeonDB!

### 🚀 Como Executar

#### Opção 1: Execução Simples (com .env) - RECOMENDADO

Agora que o `spring-dotenv` foi adicionado, basta executar:

```bash
mvn clean install
mvn spring-boot:run
```

O arquivo `.env` será carregado automaticamente! ✨

#### Opção 2: Passar credenciais via argumentos

```bash
mvn spring-boot:run -Dspring-boot.run.arguments="--spring.datasource.url=jdbc:postgresql://ep-small-night-achuun0e-pooler.sa-east-1.aws.neon.tech:5432/neondb?sslmode=require --spring.datasource.username=neondb_owner --spring.datasource.password=npg_UwaICz65sJek"
```

---

## 📊 Banco de Dados Criado

As seguintes tabelas foram criadas com sucesso no NeonDB:

### Tabelas Principais
- ✅ `pessoas` - Tabela base da hierarquia
- ✅ `usuarios` - Herda de Pessoa
- ✅ `freteiros` - Herda de Usuario (tabela principal)
- ✅ `admins` - Herda de Usuario
- ✅ `contratantes` - Herda de Usuario
- ✅ `planos` - 4 planos criados: BASICO, PADRAO, PREMIUM, MASTER
- ✅ `assinaturas` - Gestão de assinaturas dos freteiros
- ✅ `avaliacoes` - Sistema de avaliações
- ✅ `eventos_tracking` - Rastreamento de eventos
- ✅ `banners` - Sistema de banners

### Verificar no NeonDB Console

1. Acesse: https://console.neon.tech
2. Selecione seu projeto `neondb`
3. Vá em **SQL Editor**
4. Execute:

```sql
-- Ver todas as tabelas
SELECT tablename FROM pg_tables WHERE schemaname = 'public';

-- Ver planos cadastrados
SELECT * FROM planos;

-- Ver estrutura da tabela freteiros
\d freteiros;
```

---

## 🧪 Testar API

### Health Check
```bash
curl http://localhost:8080/actuator/health
```

**Resposta esperada**: `{"status":"UP"}`

### Registrar Novo Freteiro
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

### Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "joao@example.com",
    "password": "senha123"
  }'
```

### Listar Freteiros (Público)
```bash
curl http://localhost:8080/api/freteiros
```

---

## 📚 Documentação Interativa

### Swagger UI
Acesse: http://localhost:8080/swagger-ui.html

Aqui você pode:
- Ver todos os endpoints disponíveis
- Testar requisições diretamente no navegador
- Ver exemplos de Request/Response

### API Docs (JSON)
Acesse: http://localhost:8080/api-docs

---

## 🔐 Endpoints Principais

### Públicos (sem autenticação)
- `POST /api/auth/register` - Registrar freteiro
- `POST /api/auth/login` - Login
- `GET /api/freteiros` - Listar freteiros (com ranking)
- `GET /api/freteiros/{id}` - Buscar por ID
- `GET /api/freteiros/slug/{slug}` - Buscar por slug
- `POST /api/tracking` - Registrar evento de tracking

### Autenticados (requer Bearer Token)
- `GET /api/auth/me` - Usuário logado
- `GET /api/freteiro/perfil` - Perfil do freteiro logado
- `PUT /api/freteiro/perfil` - Atualizar perfil
- `POST /api/freteiros/{id}/avaliacoes` - Avaliar freteiro

### Admin (requer role ADMIN)
- `GET /api/admin/dashboard` - Dashboard administrativo

---

## 📦 Status do Projeto

### ✅ Concluído
- [x] Configuração Spring Boot + Java 21
- [x] Conexão com NeonDB
- [x] Hierarquia de entidades (Joined Table)
- [x] Migrações Flyway (6 migrations)
- [x] Autenticação JWT
- [x] Repositories com queries customizadas
- [x] Services com lógica de negócio
- [x] Controllers REST
- [x] Sistema de ranking de freteiros
- [x] Tracking de eventos
- [x] CORS configurado
- [x] Swagger/OpenAPI
- [x] Exception handling global

### 🔄 Próximos Passos
- [ ] Integrar com frontend Next.js
- [ ] Implementar upload de imagens
- [ ] Configurar WhatsApp real (Evolution API ou Z-API)
- [ ] Configurar PagBank para pagamentos
- [ ] Testes unitários e de integração
- [ ] Deploy em produção

---

## 🌐 Conexão NeonDB

**Região**: South America East 1 (São Paulo)
**Host**: `ep-small-night-achuun0e-pooler.sa-east-1.aws.neon.tech`
**Database**: `neondb`
**SSL**: Obrigatório

---

## 🛠️ Troubleshooting

### Erro: "Connection refused"
- Verifique se o arquivo `.env` existe na raiz do projeto
- Verifique se as credenciais estão corretas
- Teste a conexão direto com psql: `psql "postgresql://neondb_owner:npg_UwaICz65sJek@ep-small-night-achuun0e-pooler.sa-east-1.aws.neon.tech/neondb?sslmode=require"`

### Erro: "Port 8080 already in use"
- Pare outros serviços na porta 8080
- Ou altere a porta em `application-dev.yml`:
  ```yaml
  server:
    port: 8081
  ```

### Re-compilar após mudanças
```bash
mvn clean install
```

---

## 🎯 Performance

- **Startup time**: ~20 segundos
- **Flyway migrations**: ~2 segundos (6 migrations)
- **Connection pool**: HikariCP (otimizado)

---

## 📞 Suporte

Para mais detalhes, consulte:
- `CONFIGURACAO_NEONDB.md` - Guia completo de configuração NeonDB
- `GUIA_DE_TESTE.md` - Guia detalhado de testes
- `README.md` - Documentação geral do projeto
