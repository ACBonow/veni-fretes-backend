# Render + NeonDB - Setup em 5 Minutos

Deploy do VeniFretes Backend usando Render + NeonDB.

## Por que Render + NeonDB?

| Feature | Benefício |
|---------|-----------|
| ✅ **Custo** | $0/mês para começar |
| ✅ **Setup** | 5 minutos |
| ✅ **Deploy** | Automático via Git |
| ✅ **SSL** | HTTPS incluído |
| ✅ **Escalável** | Upgrade fácil quando crescer |

---

## 🚀 Passo a Passo

### 1️⃣ Preparar Banco de Dados (2 min)

**NeonDB** - https://neon.tech

1. Faça login no NeonDB
2. **"Create a project"**
   - Name: `venifretes-backend`
   - Region: US East (ou mais próximo)
3. **Copie a Connection String**:
   ```
   postgresql://user:pass@ep-xxx.region.aws.neon.tech/neondb?sslmode=require
   ```
   💾 **Guarde essa string!**

---

### 2️⃣ Deploy no Render (3 min)

**Render** - https://render.com

#### A. Criar Web Service

1. Login no Render
2. **"New +"** → **"Web Service"**
3. **"Connect repository"** → Selecione `veni-fretes-backend`
4. Render detecta automaticamente o `render.yaml` ✅

#### B. Configurar Variáveis

Na seção **"Environment"**, adicione:

```bash
# Database - COLE A CONNECTION STRING DO NEONDB
DATABASE_URL=postgresql://user:pass@ep-xxx.neon.tech/neondb?sslmode=require

# Spring Profile
SPRING_PROFILES_ACTIVE=prod

# JWT Secret - GERE UM NOVO!
JWT_SECRET=seu-secret-super-seguro-change-this-min-512-bits

# CORS - Adicione domínio do frontend
CORS_ALLOWED_ORIGINS=https://seu-frontend.vercel.app,http://localhost:3000
```

💡 **Gerar JWT Secret seguro**:
```bash
openssl rand -base64 64
```

#### C. Deploy

1. Clique em **"Create Web Service"**
2. Aguarde o build (5-10 minutos primeira vez) ⏳
3. Pronto! Você receberá uma URL: `https://seu-app.onrender.com`

---

### 3️⃣ Verificar Deploy (30 seg)

```bash
# Substitua pela sua URL
curl https://seu-app.onrender.com/actuator/health

# Deve retornar:
{"status":"UP"}
```

**Swagger UI**: `https://seu-app.onrender.com/swagger-ui.html`

---

## ✅ Checklist

- [ ] Criar projeto NeonDB
- [ ] Copiar connection string
- [ ] Criar web service no Render
- [ ] Configurar `DATABASE_URL`
- [ ] Configurar `JWT_SECRET` (gerar novo!)
- [ ] Configurar `CORS_ALLOWED_ORIGINS`
- [ ] Configurar `SPRING_PROFILES_ACTIVE=prod`
- [ ] Aguardar deploy
- [ ] Testar `/actuator/health`
- [ ] Testar `/swagger-ui.html`

---

## 📋 Variáveis Obrigatórias

| Variável | O que colocar |
|----------|--------------|
| `DATABASE_URL` | Connection string do NeonDB (com `?sslmode=require`) |
| `SPRING_PROFILES_ACTIVE` | `prod` |
| `JWT_SECRET` | Gere com: `openssl rand -base64 64` |
| `CORS_ALLOWED_ORIGINS` | URLs do frontend separadas por vírgula |

---

## 🐛 Problemas Comuns

### Deploy falha: "Connection refused"

**Causa**: `DATABASE_URL` incorreta ou sem `?sslmode=require`

**Solução**:
```bash
# Formato correto:
DATABASE_URL=postgresql://user:pass@host.neon.tech/neondb?sslmode=require
                                                              ↑↑↑ IMPORTANTE!
```

### App muito lenta

**Causa**: Plano gratuito "dorme" após 15 min sem uso

**Soluções**:
1. **Upgrade para $7/mês** (sem sleep)
2. **Use uptime monitor gratuito**:
   - [UptimeRobot](https://uptimerobot.com)
   - Configure ping a cada 10 min

### Flyway migration error

**Causa**: Banco já tem tabelas de outro projeto

**Solução**:
1. No NeonDB, vá em **SQL Editor**
2. Execute:
   ```sql
   DROP SCHEMA public CASCADE;
   CREATE SCHEMA public;
   ```
3. Redeploy no Render

---

## 💰 Custos

### Configuração Grátis
- **Render Free**: 750 horas/mês
- **NeonDB Free**: 0.5 GB
- **Total**: **$0/mês** 🎉

### Limitações do Free Tier
- ⏸️ Serviço dorme após 15 min inativo
- ⏱️ Cold start: 30-60 seg ao acordar
- 💾 NeonDB: 0.5 GB storage

### Produção (Recomendado)
- **Render Starter**: $7/mês (sem sleep, 1GB RAM)
- **NeonDB Free**: $0/mês (0.5 GB suficiente)
- **Total**: **$7/mês**

---

## 🔄 CI/CD Automático

Render faz deploy automático quando você:
1. Faz `git push origin main`
2. Merge de Pull Request no GitHub

**Configuração**: Nenhuma! Funciona automaticamente. ✅

---

## 🌐 Conectar Frontend (Vercel)

### No seu projeto frontend:

```javascript
// .env.production
NEXT_PUBLIC_API_URL=https://seu-app.onrender.com
```

### Atualizar CORS no Render:

```bash
CORS_ALLOWED_ORIGINS=https://seu-frontend.vercel.app
```

---

## 📊 Monitoramento

### Logs em Tempo Real
1. Dashboard do Render
2. Aba **"Logs"**
3. Filtre por erro, warning, etc.

### Uptime Monitor (Recomendado)
1. Cadastre no [UptimeRobot](https://uptimerobot.com)
2. Adicione monitor:
   - **URL**: `https://seu-app.onrender.com/actuator/health`
   - **Interval**: 10 minutos
3. Receba alertas se cair

---

## 🔗 Links Úteis

- [Dashboard NeonDB](https://console.neon.tech)
- [Dashboard Render](https://dashboard.render.com)
- [Guia Completo - Render](./DEPLOY_RENDER.md)
- [Guia Completo - NeonDB](./CONFIGURACAO_NEONDB.md)

---

## 🎉 Pronto!

Seu backend está no ar:

✅ **Backend**: Render (Spring Boot)
✅ **Database**: NeonDB (PostgreSQL)
✅ **HTTPS**: Incluído
✅ **Deploy**: Automático
✅ **Custo**: $0/mês

**Próximo passo**: Deploy do frontend no Vercel!
