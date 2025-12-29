# Deploy no Render

## Passo a Passo

### 1. Escolha do Runtime
Na tela de criação do serviço no Render:
- **Runtime Environment**: Selecione **Docker**
- O Render irá detectar e usar o `Dockerfile` automaticamente

### 2. Configuração Básica
- **Name**: veni-fretes-backend (ou o nome que preferir)
- **Region**: Escolha a região mais próxima (ex: Oregon para melhor latência com Brasil)
- **Branch**: main
- **Root Directory**: deixe vazio (projeto está na raiz)

### 3. Variáveis de Ambiente Obrigatórias

Configure as seguintes variáveis de ambiente no Render:

#### Database (NeonDB)
```
DATABASE_URL=jdbc:postgresql://<usuario>:<senha>@<neon-host>/<database>?sslmode=require
```
Exemplo:
```
DATABASE_URL=jdbc:postgresql://user:pass@ep-cool-name-123456.us-east-1.aws.neon.tech/venifretes?sslmode=require
```

#### Segurança
**CRÍTICO**: O JWT_SECRET deve ter **no mínimo 64 caracteres** (512 bits). Caso contrário, a aplicação falhará ao gerar tokens.

Gere um secret seguro com:
```bash
openssl rand -base64 64
```

Depois adicione no Render:
```
JWT_SECRET=<cole-aqui-o-resultado-do-comando-acima>
```

#### CORS
Para **desenvolvimento/testes** (Postman, qualquer origem):
```
CORS_ALLOWED_ORIGINS=*
```

Para **produção** (origens específicas):
```
CORS_ALLOWED_ORIGINS=https://seu-frontend.com,https://outro-dominio.com
```

#### PagBank (Opcional - para produção)
```
PAGBANK_API_URL=https://api.pagseguro.com
PAGBANK_TOKEN=<seu-token-pagbank>
PAGBANK_WEBHOOK_TOKEN=<seu-webhook-token>
```

#### Spring Profile
```
SPRING_PROFILES_ACTIVE=prod
```

### 4. Deploy

1. Faça commit e push do Dockerfile e .dockerignore
2. No Render, clique em "Create Web Service"
3. Conecte seu repositório GitHub
4. Configure conforme os passos acima
5. Adicione as variáveis de ambiente
6. Clique em "Create Web Service"

O Render irá:
- Detectar o Dockerfile
- Fazer o build da imagem Docker
- Fazer o deploy automático
- Gerar uma URL pública (ex: https://veni-fretes-backend.onrender.com)

### 5. Health Check

Após o deploy, verifique se a aplicação está rodando:
```
https://seu-app.onrender.com/actuator/health
```

### 6. Documentação da API

Acesse o Swagger UI:
```
https://seu-app.onrender.com/swagger-ui.html
```

## Notas Importantes

1. **Free Tier**: O Render pode colocar o serviço em "sleep" após 15 minutos de inatividade. O primeiro acesso pode demorar ~30 segundos.

2. **Database**: Configure o NeonDB conforme `NEONDB_RAILWAY.md`

3. **Build Time**: O primeiro build pode demorar 5-10 minutos (Maven precisa baixar dependências)

4. **Logs**: Acesse os logs em tempo real no dashboard do Render

5. **Auto Deploy**: Cada push na branch main fará um novo deploy automaticamente

## Troubleshooting

### Erro de conexão com database
- Verifique se o DATABASE_URL está correto
- Certifique-se que `sslmode=require` está presente na URL
- Verifique se o IP do Render está autorizado no NeonDB

### Aplicação não inicia
- Verifique os logs no dashboard do Render
- Certifique-se que todas as variáveis de ambiente obrigatórias estão configuradas
- **IMPORTANTE**: Verifique se o JWT_SECRET tem no mínimo 64 caracteres (use `openssl rand -base64 64`)

### Erro 500 ao fazer login
- **Causa provável**: JWT_SECRET muito curto ou inválido
- **Solução**: Gere um novo JWT_SECRET com `openssl rand -base64 64` e atualize no Render
- Verifique os logs para confirmar o erro: `WeakKeyException` indica chave JWT muito curta

### Port binding error
- O Render define a porta via variável PORT
- Não é necessário configurar, o application.yml já usa ${PORT:8080}
