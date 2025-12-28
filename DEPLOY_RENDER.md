# Deploy no Render - VeniFretes Backend

Este guia mostra como fazer deploy do backend VeniFretes no Render.

## Por que Render?

- ✅ Suporte nativo a Spring Boot/Java
- ✅ Plano gratuito generoso (750 horas/mês)
- ✅ Deploy automático via Git
- ✅ SSL/HTTPS gratuito
- ✅ PostgreSQL integrado ou use NeonDB
- ✅ Configuração simples via arquivo `render.yaml`

## Pré-requisitos

- Conta no [Render](https://render.com)
- Conta no GitHub com o repositório VeniFretes Backend
- Banco de dados PostgreSQL (Render ou NeonDB)

---

## 🚀 Opção A: Deploy Rápido com NeonDB (Recomendado)

### Passo 1: Preparar NeonDB

1. Acesse [neon.tech](https://neon.tech) e crie um projeto
2. Copie a **Connection String**:
   ```
   postgresql://user:password@ep-xxx.region.aws.neon.tech/neondb?sslmode=require
   ```

### Passo 2: Deploy no Render

1. Acesse [render.com](https://render.com) e faça login
2. Clique em **"New +"** → **"Web Service"**
3. Conecte seu repositório GitHub `veni-fretes-backend`
4. Render detectará automaticamente o `render.yaml`
5. Configure as variáveis de ambiente:

```bash
DATABASE_URL=postgresql://user:pass@ep-xxx.neon.tech/neondb?sslmode=require
JWT_SECRET=seu-secret-super-seguro-min-512-bits-change-this
CORS_ALLOWED_ORIGINS=https://seu-frontend.vercel.app,http://localhost:3000
```

6. Clique em **"Create Web Service"**
7. Aguarde o deploy (5-10 minutos na primeira vez)

### Passo 3: Verificar Deploy

```bash
curl https://venifretes-backend.onrender.com/actuator/health
```

**Swagger UI**: `https://venifretes-backend.onrender.com/swagger-ui.html`

---

## 🚀 Opção B: Deploy com PostgreSQL do Render

### Passo 1: Criar Banco de Dados

1. No Render dashboard, clique em **"New +"** → **"PostgreSQL"**
2. Configure:
   - **Name**: `venifretes-db`
   - **Database**: `venifretes`
   - **User**: `venifretes`
   - **Region**: escolha a mais próxima
3. Clique em **"Create Database"**
4. Aguarde a criação (1-2 minutos)
5. Copie a **Internal Database URL**

### Passo 2: Criar Web Service

1. Clique em **"New +"** → **"Web Service"**
2. Conecte o repositório `veni-fretes-backend`
3. Configure as variáveis de ambiente:

```bash
DATABASE_URL=<cole-a-internal-database-url-aqui>
JWT_SECRET=seu-secret-super-seguro-min-512-bits
CORS_ALLOWED_ORIGINS=https://seu-frontend.vercel.app
SPRING_PROFILES_ACTIVE=prod
```

4. Clique em **"Create Web Service"**

---

## ⚙️ Configuração Detalhada

### Variáveis de Ambiente Obrigatórias

| Variável | Descrição | Exemplo |
|----------|-----------|---------|
| `DATABASE_URL` | Connection string do PostgreSQL | `postgresql://user:pass@host/db` |
| `JWT_SECRET` | Secret para JWT (mín 512 bits) | Gere com `openssl rand -base64 64` |
| `CORS_ALLOWED_ORIGINS` | Domínios permitidos (separados por vírgula) | `https://app.com,https://www.app.com` |
| `SPRING_PROFILES_ACTIVE` | Profile do Spring | `prod` |

### Variáveis de Ambiente Opcionais

```bash
# Java Runtime
MAVEN_OPTS=-Xmx512m
JAVA_OPTS=-Xmx512m -Xms256m

# WhatsApp (integração futura)
WHATSAPP_ENABLED=false
WHATSAPP_API_URL=
WHATSAPP_API_TOKEN=

# PagBank (integração futura)
PAGBANK_API_URL=https://api.pagseguro.com
PAGBANK_TOKEN=
PAGBANK_WEBHOOK_TOKEN=
```

### Configuração do render.yaml

O arquivo `render.yaml` já está configurado com:
- **Build Command**: `mvn clean install -DskipTests`
- **Start Command**: `java -Dserver.port=$PORT -jar target/*.jar`
- **Health Check**: `/actuator/health`
- **Plan**: Free (750 horas/mês)

---

## 🔧 Configurações Avançadas

### Custom Domain

1. No painel do serviço, vá em **"Settings"** → **"Custom Domain"**
2. Adicione seu domínio: `api.seudominio.com`
3. Configure DNS (CNAME ou A Record):
   ```
   CNAME api.seudominio.com -> seu-app.onrender.com
   ```
4. Aguarde propagação DNS (até 48h, geralmente minutos)

### Auto Deploy

Por padrão, Render faz deploy automático quando você:
- Faz push para o branch `main`
- Faz merge de Pull Request

Para desabilitar:
1. Vá em **"Settings"** → **"Build & Deploy"**
2. Desative **"Auto-Deploy"**

### Environment Groups

Para reutilizar variáveis entre serviços:
1. Vá em **"Environment Groups"** no dashboard
2. Crie um grupo: `venifretes-env`
3. Adicione variáveis compartilhadas
4. Link ao serviço em **"Environment"** → **"Environment Groups"**

---

## 🐛 Troubleshooting

### Deploy falha com "Build failed"

**Erro**: `mvn clean install` falha

**Soluções**:

1. **Out of Memory durante build**:
   ```bash
   # Adicione nas variáveis de ambiente:
   MAVEN_OPTS=-Xmx1024m
   ```

2. **Testes falhando**:
   - O build já usa `-DskipTests`
   - Verifique se o `render.yaml` está correto

3. **Dependências não baixadas**:
   - Render usa cache do Maven
   - Force rebuild: **"Manual Deploy"** → **"Clear build cache & deploy"**

### Aplicação não inicia

**Erro**: `Application failed to start`

**Soluções**:

1. **Porta incorreta**:
   - Render injeta `$PORT` automaticamente
   - Verifique se `application.yml` tem: `server.port: ${PORT:8080}`

2. **Conexão com banco falha**:
   ```bash
   # Verifique a DATABASE_URL
   # Para NeonDB, deve ter ?sslmode=require
   DATABASE_URL=postgresql://user:pass@host/db?sslmode=require
   ```

3. **Migrações Flyway falhando**:
   - Verifique logs: **"Logs"** no dashboard
   - Conecte ao banco e verifique tabela `flyway_schema_history`

### Aplicação muito lenta ou "sleeps"

**Problema**: No plano gratuito, serviços dormem após 15 min de inatividade

**Soluções**:

1. **Upgrade para plano pago** ($7/mês - sem sleep)
2. **Use um uptime monitor** (ping a cada 10 min):
   - [UptimeRobot](https://uptimerobot.com) (grátis)
   - [Cron-job.org](https://cron-job.org) (grátis)
   - Configure para fazer GET em `/actuator/health`

### Erro 503 - Service Unavailable

**Causas**:
- Serviço está "dormindo" (plano free)
- Deploy em andamento
- Aplicação crashou

**Solução**: Aguarde 30-60 segundos para o serviço "acordar"

---

## 📊 Monitoramento

### Logs

Visualize logs em tempo real:
1. Vá em **"Logs"** no dashboard do serviço
2. Filtre por nível: INFO, WARN, ERROR
3. Download logs: **"Download Logs"**

### Metrics

No plano gratuito você tem acesso a:
- CPU usage
- Memory usage
- Network I/O
- Request count

### Health Checks

Render monitora automaticamente `/actuator/health`:
- **Intervalo**: 60 segundos
- **Timeout**: 30 segundos
- **Ação**: Restart automático se falhar 3x

---

## 💰 Custos

### Plano Free
- **750 horas/mês** (suficiente para 1 serviço 24/7)
- **Serviço dorme** após 15 min de inatividade
- **PostgreSQL**: 90 dias grátis, depois $7/mês
- **Custo**: $0/mês (com limitações)

### Plano Starter ($7/mês)
- **Sem sleep** (sempre ativo)
- **1 GB RAM**
- **0.5 CPU**
- Melhor para produção

### Com NeonDB (Recomendado)
- **Render Web Service Free**: 750h/mês
- **NeonDB Free**: 0.5 GB storage
- **Custo total**: $0/mês

---

## 🔐 Boas Práticas de Produção

### 1. Gerar JWT Secret Seguro

```bash
# Nunca use o secret padrão!
openssl rand -base64 64
```

### 2. Configurar CORS Corretamente

```bash
# Apenas domínios específicos:
CORS_ALLOWED_ORIGINS=https://app.seudominio.com,https://www.seudominio.com

# NUNCA use "*" em produção
```

### 3. Habilitar HTTPS Only

No frontend, use apenas URLs HTTPS:
```javascript
const API_URL = 'https://api.seudominio.com'
```

### 4. Configurar Backups

**Se usar PostgreSQL do Render**:
- Backups automáticos diários (retidos por 7 dias no free tier)
- Backups manuais: **"Backups"** → **"Create Backup"**

**Se usar NeonDB**:
- Backups automáticos inclusos
- Point-in-time recovery disponível

### 5. Variables Secrets

Use o recurso de **Secret Files** do Render para arquivos sensíveis:
1. **"Environment"** → **"Secret Files"**
2. Upload de arquivos `.env`, certificados, etc.

---

## 🚀 Deploy Workflow Recomendado

### Desenvolvimento
```bash
git checkout develop
# Faça suas alterações
git commit -m "feat: nova funcionalidade"
git push origin develop
```

### Staging (Opcional)
```bash
git checkout staging
git merge develop
git push origin staging
# Render pode fazer deploy automático de staging
```

### Produção
```bash
git checkout main
git merge develop
git push origin main
# Render faz deploy automático para produção
```

---

## 🔗 Integração com Frontend (Vercel)

### No Vercel, configure:

```bash
# .env.production no projeto frontend
NEXT_PUBLIC_API_URL=https://venifretes-backend.onrender.com
```

### No Render, configure CORS:

```bash
CORS_ALLOWED_ORIGINS=https://seu-app.vercel.app,https://seu-app-preview.vercel.app
```

---

## 📚 Recursos

- [Render Documentation](https://render.com/docs)
- [Render Status](https://status.render.com)
- [Render Community](https://community.render.com)
- [Spring Boot on Render Guide](https://render.com/docs/deploy-spring-boot)

---

## ✅ Checklist de Deploy

- [ ] Criar conta no Render
- [ ] Preparar banco de dados (NeonDB ou Render PostgreSQL)
- [ ] Conectar repositório GitHub
- [ ] Configurar variáveis de ambiente
- [ ] Gerar JWT_SECRET seguro
- [ ] Configurar CORS_ALLOWED_ORIGINS
- [ ] Fazer primeiro deploy
- [ ] Testar `/actuator/health`
- [ ] Testar endpoints principais
- [ ] Configurar domínio customizado (opcional)
- [ ] Configurar uptime monitor (plano free)
- [ ] Configurar backups

---

## 🎉 Próximos Passos

1. ✅ Backend no Render
2. 🔄 Frontend no Vercel
3. 🔄 Conectar frontend ↔ backend
4. 🔄 Configurar CI/CD
5. 🔄 Monitoramento e logs
6. 🔄 Documentação da API (Swagger)
