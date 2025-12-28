# Configuração NeonDB - VeniFretes Backend

## 🎯 O que é NeonDB?

NeonDB é um **PostgreSQL serverless** totalmente gerenciado, perfeito para desenvolvimento e produção. Oferece:
- ✅ Tier gratuito generoso
- ✅ Escalabilidade automática
- ✅ Backups automáticos
- ✅ SSL/TLS por padrão
- ✅ Branching de banco de dados

---

## 🚀 Passo a Passo

### 1. Criar Conta no NeonDB

1. Acesse: https://neon.tech
2. Clique em **"Sign Up"**
3. Faça login com GitHub ou Google

### 2. Criar Novo Projeto

1. No dashboard, clique em **"Create a project"**
2. Preencha:
   - **Project name**: `venifretes-backend`
   - **PostgreSQL version**: 16 (recomendado)
   - **Region**: escolha a mais próxima (ex: `US East`)
3. Clique em **"Create project"**

### 3. Obter String de Conexão

Após criar o projeto, você verá a **Connection String**. Exemplo:

```
postgresql://username:password@ep-cool-darkness-123456.us-east-2.aws.neon.tech/neondb?sslmode=require
```

**Copie essa URL!** Você vai precisar dela.

---

## ⚙️ Configurar o Backend

### Opção 1: Via Variável de Ambiente (Recomendado)

Crie um arquivo `.env` na raiz do projeto:

```bash
# .env
DATABASE_URL=postgresql://username:password@ep-cool-darkness-123456.us-east-2.aws.neon.tech/neondb?sslmode=require
```

### Opção 2: Direto no application-dev.yml

Edite o arquivo `src/main/resources/application-dev.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://ep-cool-darkness-123456.us-east-2.aws.neon.tech:5432/neondb?sslmode=require
    username: seu-usuario-aqui
    password: sua-senha-aqui
    driver-class-name: org.postgresql.Driver

  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: true
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect

  flyway:
    enabled: true
    baseline-on-migrate: true
    locations: classpath:db/migration
```

---

## 🔒 Segurança

### Não commitar credenciais!

1. Adicione o `.env` ao `.gitignore`:

```bash
echo ".env" >> .gitignore
```

2. Crie um `.env.example` sem as credenciais:

```bash
# .env.example
DATABASE_URL=postgresql://username:password@your-neon-host.neon.tech/neondb?sslmode=require
```

---

## 🧪 Testar Conexão

### 1. Compilar o projeto

```bash
mvn clean install
```

### 2. Executar a aplicação

```bash
mvn spring-boot:run
```

### 3. Verificar logs

Se tudo estiver correto, você verá nos logs:

```
Flyway migration running...
Successfully applied 6 migrations
```

---

## 📊 Acessar Banco de Dados via NeonDB Console

1. Acesse o dashboard do NeonDB: https://console.neon.tech
2. Selecione seu projeto **venifretes-backend**
3. Clique na aba **"SQL Editor"**
4. Execute queries direto no navegador:

```sql
-- Ver todas as tabelas
SELECT tablename FROM pg_tables WHERE schemaname = 'public';

-- Ver freteiros cadastrados
SELECT id, nome, email, cidade FROM freteiros;

-- Ver planos disponíveis
SELECT * FROM planos;
```

---

## 🔧 Troubleshooting

### Erro: "SSL connection required"

Certifique-se de ter `?sslmode=require` na URL:

```
jdbc:postgresql://host:5432/neondb?sslmode=require
```

### Erro: "Connection timeout"

Verifique se:
1. Seu IP não está bloqueado (NeonDB aceita qualquer IP por padrão)
2. A URL está correta
3. Usuário e senha estão corretos

### Erro: "Database does not exist"

O NeonDB cria automaticamente um banco chamado `neondb`. Use esse nome ou crie um novo:

```sql
CREATE DATABASE venifretes;
```

E atualize a URL:
```
jdbc:postgresql://host:5432/venifretes?sslmode=require
```

---

## 💰 Limites do Tier Gratuito

- **Storage**: 0.5 GB
- **Compute**: 0.25 vCPU
- **Branches**: 10
- **Connections**: Ilimitadas

Para desenvolvimento, é mais que suficiente!

---

## 🚀 Deploy em Produção com NeonDB

### Conectar NeonDB ao Render (Recomendado)

**Vantagem**: O NeonDB tem plano gratuito mais generoso (0.5 GB) que o PostgreSQL do Render.

#### Passo 1: Obter Connection String do NeonDB

1. Acesse o dashboard do NeonDB: https://console.neon.tech
2. Selecione seu projeto `venifretes-backend`
3. Na página inicial do projeto, localize a seção **Connection Details**
4. Copie a **Connection string**:
   ```
   postgresql://username:password@ep-xxx.region.aws.neon.tech/neondb?sslmode=require
   ```

#### Passo 2: Configurar no Render

1. Acesse seu projeto no Render: https://render.com
2. Selecione o web service do backend
3. Vá em **"Environment"**
4. Adicione as variáveis:
   - **Key**: `DATABASE_URL`
   - **Value**: Cole a connection string do NeonDB
   - **Key**: `SPRING_PROFILES_ACTIVE`
   - **Value**: `prod`
5. Salve as alterações

#### Passo 3: Deploy Automático

O Render fará deploy automaticamente ao salvar as variáveis.

**Importante**:
- ❌ **NÃO** crie PostgreSQL no Render se estiver usando NeonDB
- ✅ Use apenas a variável `DATABASE_URL` apontando para o NeonDB
- ✅ Certifique-se que a URL tem `?sslmode=require` no final

### Deploy em Outras Plataformas

Ao fazer deploy (Heroku, Railway, etc.), configure a variável de ambiente:

```bash
DATABASE_URL=postgresql://user:pass@neon-host.neon.tech/neondb?sslmode=require
SPRING_PROFILES_ACTIVE=prod
```

O Spring Boot detectará automaticamente e usará essa configuração.

---

## 📝 Diferenças vs Docker Local

| Feature | Docker Local | NeonDB |
|---------|-------------|--------|
| Setup | Requer Docker instalado | Apenas navegador |
| Persistência | Volume local | Cloud (sempre disponível) |
| Acesso externo | Apenas localhost | Qualquer lugar |
| Backups | Manual | Automáticos |
| SSL | Opcional | Obrigatório |
| Custo | Grátis (local) | Tier gratuito disponível |

---

## ✅ Checklist de Configuração

- [ ] Criar conta no NeonDB
- [ ] Criar projeto `venifretes-backend`
- [ ] Copiar connection string
- [ ] Criar arquivo `.env` com credenciais
- [ ] Adicionar `.env` ao `.gitignore`
- [ ] Atualizar `application-dev.yml` (se necessário)
- [ ] Compilar: `mvn clean install`
- [ ] Executar: `mvn spring-boot:run`
- [ ] Verificar migrações Flyway nos logs
- [ ] Testar endpoint: `curl http://localhost:8080/actuator/health`

---

## 🎉 Pronto!

Agora seu backend está usando **NeonDB** ao invés do PostgreSQL local via Docker!

**Vantagens**:
- ✅ Não precisa do Docker rodando
- ✅ Banco sempre disponível online
- ✅ Backups automáticos
- ✅ Pode acessar de qualquer máquina
- ✅ Fácil integração com deploy

---

## 🔗 Links Úteis

- Dashboard NeonDB: https://console.neon.tech
- Documentação: https://neon.tech/docs
- Status Page: https://neonstatus.com
