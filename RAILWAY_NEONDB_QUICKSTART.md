# Railway + NeonDB - Guia Rápido

Deploy do VeniFretes Backend usando Railway + NeonDB em 5 minutos.

## Por que usar NeonDB com Railway?

- ✅ Plano gratuito do NeonDB: **0.5 GB** vs Railway: **~100 MB**
- ✅ Melhor performance para PostgreSQL serverless
- ✅ Backups automáticos inclusos
- ✅ Branching de banco de dados (útil para testes)

---

## 🚀 Passo a Passo

### 1️⃣ Preparar Banco de Dados no NeonDB

**Tempo: 2 minutos**

1. Acesse [neon.tech](https://neon.tech) e faça login
2. Clique em **"Create a project"**
3. Configure:
   - **Name**: `venifretes-backend`
   - **Region**: escolha a mais próxima (ex: US East)
4. Clique em **"Create project"**
5. **Copie a Connection String** que aparece:
   ```
   postgresql://user:pass@ep-xxx.region.aws.neon.tech/neondb?sslmode=require
   ```
   💡 **Guarde essa string** - você vai usar no Railway!

---

### 2️⃣ Deploy no Railway

**Tempo: 3 minutos**

#### A. Criar Projeto

1. Acesse [railway.app](https://railway.app) e faça login
2. Clique em **"New Project"**
3. Selecione **"Deploy from GitHub repo"**
4. Escolha `veni-fretes-backend`

#### B. Configurar Variáveis

1. No serviço criado, vá em **"Variables"**
2. Adicione as seguintes variáveis:

```bash
# Banco de Dados - COLE A CONNECTION STRING DO NEONDB AQUI
DATABASE_URL=postgresql://user:pass@ep-xxx.region.aws.neon.tech/neondb?sslmode=require

# Spring Profile
SPRING_PROFILES_ACTIVE=prod

# JWT Secret - GERE UM NOVO (nunca use o padrão!)
JWT_SECRET=seu-secret-super-seguro-aqui-change-this-min-512-bits

# CORS - Adicione o domínio do seu frontend
CORS_ALLOWED_ORIGINS=https://seu-frontend.vercel.app,http://localhost:3000

# Java/Maven (opcional mas recomendado)
MAVEN_OPTS=-Xmx512m
JAVA_OPTS=-Xmx512m
```

#### C. Deploy Automático

O Railway vai:
1. Detectar o projeto Spring Boot
2. Executar `mvn clean install -DskipTests`
3. Fazer deploy automaticamente

Aguarde 3-5 minutos. ⏳

---

### 3️⃣ Verificar Deploy

Após o deploy, o Railway fornecerá uma URL. Teste:

```bash
# Substitua pela sua URL do Railway
curl https://seu-app.up.railway.app/actuator/health
```

Resposta esperada:
```json
{"status":"UP"}
```

**Swagger UI**: `https://seu-app.up.railway.app/swagger-ui.html`

---

## ✅ Checklist Rápido

- [ ] Criar projeto no NeonDB
- [ ] Copiar connection string do NeonDB
- [ ] Criar projeto no Railway via GitHub
- [ ] Adicionar variável `DATABASE_URL` no Railway
- [ ] Adicionar variável `SPRING_PROFILES_ACTIVE=prod`
- [ ] Adicionar variável `JWT_SECRET`
- [ ] Adicionar variável `CORS_ALLOWED_ORIGINS`
- [ ] Aguardar deploy (3-5 min)
- [ ] Testar endpoint `/actuator/health`

---

## 🔧 Troubleshooting

### Deploy falha com erro de conexão ao banco

**Problema**: `Connection to database failed`

**Solução**:
1. Verifique se a variável `DATABASE_URL` está correta
2. Certifique-se que tem `?sslmode=require` no final da URL
3. Formato correto:
   ```
   postgresql://user:pass@host.neon.tech/neondb?sslmode=require
   ```

### Build falha com "Out of Memory"

**Problema**: `OutOfMemoryError during build`

**Solução**: Adicione a variável:
```bash
MAVEN_OPTS=-Xmx1024m
```

### Aplicação não inicia

**Problema**: Deploy completa mas app não responde

**Solução**: Verifique os logs no Railway:
1. Vá em **"Logs"** no Railway
2. Procure por erros de:
   - Conexão com banco de dados
   - Migrações Flyway
   - Porta (deve usar `$PORT` do Railway)

### Flyway migration failed

**Problema**: `Flyway migration failed`

**Solução**:
1. Verifique se o banco NeonDB está acessível
2. No NeonDB, vá em SQL Editor e execute:
   ```sql
   SELECT tablename FROM pg_tables WHERE schemaname = 'public';
   ```
3. Se necessário, limpe as tabelas e redeploy

---

## 🎯 Variáveis de Ambiente - Resumo

| Variável | Obrigatória? | Descrição |
|----------|--------------|-----------|
| `DATABASE_URL` | ✅ Sim | Connection string do NeonDB |
| `SPRING_PROFILES_ACTIVE` | ✅ Sim | Use `prod` |
| `JWT_SECRET` | ✅ Sim | Secret para JWT (min 512 bits) |
| `CORS_ALLOWED_ORIGINS` | ✅ Sim | Domínios permitidos |
| `MAVEN_OPTS` | ⚠️ Recomendado | Memória para build |
| `JAVA_OPTS` | ⚠️ Recomendado | Memória para runtime |

---

## 💰 Custos

### NeonDB (Plano Gratuito)
- **Storage**: 0.5 GB
- **Compute**: 0.25 vCPU
- **Custo**: $0/mês

### Railway (Plano Hobby)
- **500 horas/mês grátis**
- Após isso: ~$5/mês baseado no uso
- **Custo inicial**: $0/mês

**Total para começar**: $0/mês 🎉

---

## 🔗 Links Úteis

- [NeonDB Console](https://console.neon.tech)
- [Railway Dashboard](https://railway.app/dashboard)
- [Guia Completo - NeonDB](./CONFIGURACAO_NEONDB.md)
- [Guia Completo - Railway](./DEPLOY_RAILWAY.md)

---

## 🎉 Pronto!

Seu backend VeniFretes agora está rodando em produção:

- ✅ Railway (backend)
- ✅ NeonDB (banco de dados PostgreSQL)
- ✅ HTTPS automático
- ✅ Deploy automático via Git
- ✅ Plano gratuito

**Próximo passo**: Deploy do frontend no Vercel!
