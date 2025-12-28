# Deploy no Railway - VeniFretes Backend

Este guia mostra como fazer deploy do backend VeniFretes no Railway.

## Pré-requisitos

- Conta no [Railway](https://railway.app)
- Conta no GitHub com o repositório VeniFretes Backend
- Banco de dados PostgreSQL (pode ser criado no Railway)

## Passo 1: Criar Projeto no Railway

1. Acesse [railway.app](https://railway.app) e faça login
2. Clique em **"New Project"**
3. Selecione **"Deploy from GitHub repo"**
4. Autorize o Railway a acessar seu GitHub
5. Selecione o repositório `veni-fretes-backend`

## Passo 2: Adicionar PostgreSQL

1. No projeto Railway, clique em **"New"**
2. Selecione **"Database"** → **"Add PostgreSQL"**
3. Aguarde a criação do banco de dados
4. O Railway criará automaticamente a variável `DATABASE_URL`

## Passo 3: Configurar Variáveis de Ambiente

No painel do seu serviço backend, vá em **"Variables"** e adicione:

### Variáveis Obrigatórias

```bash
# DATABASE_URL já é criada automaticamente pelo Railway quando você adiciona PostgreSQL

# JWT Configuration
JWT_SECRET=seu-secret-super-seguro-aqui-min-512-bits-change-this
JWT_EXPIRATION=86400000

# CORS - adicione o domínio do seu frontend
CORS_ALLOWED_ORIGINS=https://seu-frontend.vercel.app,http://localhost:3000

# Java Runtime
MAVEN_OPTS=-Xmx512m
JAVA_OPTS=-Xmx512m
```

### Variáveis Opcionais (Integrações Futuras)

```bash
# WhatsApp
WHATSAPP_ENABLED=false
WHATSAPP_API_URL=
WHATSAPP_API_TOKEN=

# PagBank
PAGBANK_API_URL=https://sandbox.api.pagseguro.com
PAGBANK_TOKEN=
PAGBANK_WEBHOOK_TOKEN=
```

## Passo 4: Configurar Build

O Railway detectará automaticamente o projeto Spring Boot através do arquivo `railway.toml`.

Se necessário, você pode ajustar as configurações em **"Settings"**:
- **Build Command**: `mvn clean install -DskipTests`
- **Start Command**: `java -Dserver.port=$PORT $JAVA_OPTS -jar target/*.jar`

## Passo 5: Deploy

1. O Railway iniciará o build automaticamente após configurar as variáveis
2. Aguarde o build completar (pode levar alguns minutos na primeira vez)
3. Após o deploy, você receberá uma URL pública: `https://seu-app.up.railway.app`

## Passo 6: Verificar Deploy

Teste os endpoints da API:

```bash
# Health Check
curl https://seu-app.up.railway.app/actuator/health

# API Docs (Swagger)
https://seu-app.up.railway.app/swagger-ui.html
```

## Configuração do Domínio Customizado (Opcional)

1. No painel do serviço, vá em **"Settings"** → **"Domains"**
2. Clique em **"Generate Domain"** ou **"Custom Domain"**
3. Configure seu DNS se usar domínio customizado

## Monitoramento

Railway fornece:
- **Logs**: Veja logs em tempo real na aba "Logs"
- **Metrics**: CPU, memória e network na aba "Metrics"
- **Deploys**: Histórico de deploys na aba "Deployments"

## Troubleshooting

### Build Falha

**Erro: Out of Memory durante build**
```bash
# Adicione nas variáveis de ambiente:
MAVEN_OPTS=-Xmx1024m
```

**Erro: Testes falhando**
```bash
# O build já está configurado para pular testes
# Mas você pode garantir no railway.toml:
buildCommand = "mvn clean install -DskipTests"
```

### Aplicação não inicia

**Erro: Port already in use**
- O Railway injeta automaticamente a variável `$PORT`
- Certifique-se que o `application.yml` está configurado para usar `${PORT:8080}`

**Erro: Connection to database failed**
- Verifique se a variável `DATABASE_URL` está configurada
- O Railway gera automaticamente quando você adiciona PostgreSQL
- Formato: `postgresql://user:password@host:port/database`

### Aplicação muito lenta

```bash
# Aumente a memória JVM:
JAVA_OPTS=-Xmx768m -Xms512m
```

## Configuração de Produção

### 1. Gerar JWT Secret Seguro

```bash
# Use um gerador de secrets seguro:
openssl rand -base64 64
```

### 2. Configurar SSL/HTTPS

O Railway fornece HTTPS automaticamente para todos os domínios.

### 3. Configurar CORS corretamente

```bash
# Adicione apenas os domínios do seu frontend:
CORS_ALLOWED_ORIGINS=https://seu-frontend.vercel.app
```

### 4. Habilitar Health Check

O endpoint `/actuator/health` já está disponível via Spring Boot Actuator.
Railway pode monitorar automaticamente.

## Custos

- **PostgreSQL**: ~$5/mês (500MB) ou plano gratuito limitado
- **Backend**: ~$5/mês baseado no uso (500 horas/mês grátis no plano Hobby)

## CI/CD Automático

Railway faz deploy automático quando você:
1. Faz push para o branch `main` (ou branch configurado)
2. Merge de Pull Request no GitHub

Para desabilitar auto-deploy:
1. Vá em **"Settings"** → **"Service"**
2. Desative **"Auto Deploy"**

## Links Úteis

- [Railway Documentation](https://docs.railway.app/)
- [Railway Discord](https://discord.gg/railway)
- [Spring Boot on Railway](https://docs.railway.app/guides/spring-boot)

## Próximos Passos

1. ✅ Deploy do backend no Railway
2. 🔄 Deploy do frontend no Vercel
3. 🔄 Conectar frontend com backend
4. 🔄 Configurar banco de dados de produção
5. 🔄 Implementar monitoramento e logging
6. 🔄 Configurar backups do banco de dados
