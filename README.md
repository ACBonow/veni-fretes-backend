# VeniFretes Backend

Backend da plataforma VeniFretes - Sistema de conexão entre freteiros e contratantes.

## Tecnologias

- **Java 21 LTS**
- **Spring Boot 3.2.1**
- **PostgreSQL 16**
- **Maven**
- **Docker & Docker Compose**
- **JWT** para autenticação
- **Flyway** para migrações de banco
- **Swagger/OpenAPI** para documentação

## Pré-requisitos

- Java 21 JDK
- Maven 3.9+
- Docker e Docker Compose
- Git

## Como Executar

### 1. Subir o banco de dados PostgreSQL

```bash
docker-compose up -d
```

Isso iniciará:
- PostgreSQL na porta 5432
- pgAdmin na porta 5050 (http://localhost:5050)
  - Email: admin@venifretes.com
  - Senha: admin123

### 2. Compilar o projeto

```bash
mvn clean install
```

### 3. Executar a aplicação

```bash
mvn spring-boot:run
```

A aplicação estará disponível em:
- **API**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs

## Estrutura do Projeto

```
src/main/java/com/venifretes/
├── config/          # Configurações (CORS, Security, OpenAPI)
├── controller/      # REST Controllers
├── dto/             # DTOs de Request/Response
├── exception/       # Exception Handlers
├── model/
│   ├── entity/      # Entidades JPA
│   └── enums/       # Enums
├── repository/      # JPA Repositories
├── security/        # JWT e Spring Security
├── service/         # Lógica de negócio
└── util/            # Utilitários
```

## Modelo de Dados

Hierarquia de entidades usando **Joined Table Strategy**:

```
Pessoa (base)
  ├─> Usuario
  │     ├─> Freteiro
  │     └─> Admin
  └─> Contratante
```

## Endpoints Principais

### Autenticação
- `POST /api/auth/register` - Registrar novo freteiro
- `POST /api/auth/login` - Login
- `GET /api/auth/me` - Usuário logado

### Freteiros (Público)
- `GET /api/freteiros` - Listar com filtros
- `GET /api/freteiros/{id}` - Buscar por ID
- `GET /api/freteiros/slug/{slug}` - Buscar por slug

### Tracking
- `POST /api/tracking` - Registrar evento

### Avaliações
- `GET /api/freteiros/{id}/avaliacoes` - Listar
- `POST /api/freteiros/{id}/avaliacoes` - Criar (autenticado)

## Variáveis de Ambiente

Criar arquivo `.env` na raiz (opcional):

```bash
# Database
DATABASE_URL=jdbc:postgresql://localhost:5432/venifretes
DATABASE_USERNAME=venifretes
DATABASE_PASSWORD=venifretes123

# JWT
JWT_SECRET=venifretes-secret-key-change-in-production-min-512-bits
JWT_EXPIRATION=86400000

# CORS
CORS_ALLOWED_ORIGINS=http://localhost:3000

# WhatsApp (futuro)
WHATSAPP_ENABLED=false

# PagBank (futuro)
PAGBANK_TOKEN=
```

## Desenvolvimento

### Rodar testes

```bash
mvn test
```

### Gerar build

```bash
mvn clean package
```

### Acessar banco de dados

Via pgAdmin: http://localhost:5050

Ou via linha de comando:
```bash
docker exec -it venifretes-postgres psql -U venifretes -d venifretes
```

## Licença

Proprietário - VeniFretes © 2024
