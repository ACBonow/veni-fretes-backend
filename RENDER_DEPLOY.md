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
```
JWT_SECRET=<gere-uma-chave-secreta-forte-minimo-64-caracteres>
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
- Verifique se o JWT_SECRET tem tamanho suficiente

### Port binding error
- O Render define a porta via variável PORT
- Não é necessário configurar, o application.yml já usa ${PORT:8080}
