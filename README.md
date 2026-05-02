# Students

- 230103164
- 230103154
- 230103016
- 230103345


# Inlive Back


Backend service for the Inlive platform built with Java 21 and Spring Boot.

## What's Inside

- REST API built with Spring Boot
- PostgreSQL through Spring Data JPA / Hibernate
- Authentication with Keycloak and JWT
- Swagger / OpenAPI documentation
- Spring Boot Actuator for health checks and metrics
- Integration with the separate `inlive-file-manager` service
- Gradle wrapper, so local Gradle installation is not required

## Requirements

You need:

- Java 21
- Docker and Docker Compose
- Git

Gradle does not need to be installed separately: the project already includes `./gradlew`.

## Env Files

Ready-to-use env files are stored in Google Drive:

[Inlive env files - google form submit any files, not here]()

The folder contains:

- `back_env1.txt` - backend variables: Keycloak, `FILE_API_URL`, PostgreSQL, and CORS.
- `file_manager_env2.txt` - `inlive-file-manager` variables: Keycloak and object storage access.

Do not commit real secrets to README, `.env.example`, `.env.local`, `.env`, or git. Download the required file from Google Drive and use it locally as a base for your `.env.local` or `.env`.

## Important Note About the New Railway Credentials

The current Keycloak and file manager URLs can be used locally:

```env
KEYCLOAK_URL=https://keycloak-production-4101.up.railway.app
FILE_API_URL=https://adorable-trust-production-169a.up.railway.app
```

However, the current PostgreSQL host:

```env
POSTGRES_ADDRESS=postgres.railway.internal
```

is usually available only inside the Railway private network. On a local machine, this hostname usually does not resolve.

So, with the new credentials, there are two working scenarios:

1. The backend runs inside Railway or in an environment that has access to the Railway private network. In this case, the PostgreSQL parameters can be used as they are.
2. The backend runs locally. In this case, Keycloak and `FILE_API_URL` can stay on Railway, but PostgreSQL must be replaced with a local or publicly accessible database.

## Recommended Scenario: Local Backend + Current Railway Keycloak/File API

This is the most convenient setup for development: the backend runs locally, while authentication and the file manager stay on the current Railway services.

### 1. Clone the Project

```bash
git clone https://github.com/daurenassanbaev/inlive-back.git
cd inlive-back
```

### 2. Start Local PostgreSQL

You can use the `postgres` service from the current `docker-compose.yml`:

```bash
docker compose up -d postgres
```

This container starts a database with:

- host: `localhost`
- port: `5434`
- db: `inlive`
- user: `postgres`
- password: `postgres`

### 3. Create `.env.local` in the Project Root

You can use `back_env1.txt` from Google Drive as a base, but for local development you must replace the PostgreSQL block with local values.

Example `.env.local`:

```env
KEYCLOAK_URL=https://keycloak-production-4101.up.railway.app
KEYCLOAK_REALM=inlive
KEYCLOAK_CLIENT_ID=inlive
KEYCLOAK_ADMIN=<current admin email>
KEYCLOAK_ADMIN_PASSWORD=<current admin password>
KEYCLOAK_CLIENT_SECRET=<current client secret>

FILE_API_URL=https://adorable-trust-production-169a.up.railway.app

POSTGRES_ADDRESS=localhost
POSTGRES_ADDRESS_PORT=5434
POSTGRES_DB_NAME=inlive
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=postgres

APP_ALLOWED_ORIGINS=https://inlive-frontend.vercel.app,http://localhost:3000,http://localhost:3001
```

### 4. Run the Backend

```bash
set -a
source .env.local
set +a
./gradlew bootRun
```

By default, the backend starts on port `8080`.

Useful URLs:

- Backend API: http://localhost:8080/api
- Swagger UI: http://localhost:8080/api/swagger-ui.html
- Health check: http://localhost:8080/api/actuator/health

## Railway Scenario: Backend Inside the Private Network

If the backend runs inside Railway, the current backend credentials can be used almost as they are.

Download `back_env1.txt` from Google Drive and use it as the base for the backend environment variables.

Example `.env.local` shape:

```env
KEYCLOAK_URL=https://keycloak-production-4101.up.railway.app
KEYCLOAK_REALM=inlive
KEYCLOAK_CLIENT_ID=inlive
KEYCLOAK_ADMIN=<current admin email>
KEYCLOAK_ADMIN_PASSWORD=<current admin password>
KEYCLOAK_CLIENT_SECRET=<current client secret>

FILE_API_URL=https://adorable-trust-production-169a.up.railway.app

POSTGRES_ADDRESS=postgres.railway.internal
POSTGRES_ADDRESS_PORT=5432
POSTGRES_DB_NAME=railway
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=<current postgres password>

APP_ALLOWED_ORIGINS=https://inlive-frontend.vercel.app,http://localhost:3000,http://localhost:3001
```

Run:

```bash
set -a
source .env.local
set +a
./gradlew bootRun
```

## Fully Local Stack with Docker Compose

This option does not use the current Railway Keycloak/PostgreSQL credentials. Use it when you want a fully standalone local environment: backend + local Keycloak + local PostgreSQL + local file manager.

If the file manager needs to work with real object storage, take `AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY`, and `AWS_BUCKET` from `file_manager_env2.txt` in Google Drive.

### 1. Create `.env`

You can start from `.env.example`:

```bash
cp .env.example .env
```

### 2. Build Images

```bash
docker build -t app/backend:latest .
docker build -t app/file-manager:latest ./inlive-file-manager
```

If the frontend is not needed, you can skip starting the `front` service.

### 3. Start Services

```bash
docker compose up -d
```

### 4. Check Containers

```bash
docker compose ps
```

Useful URLs:

- Backend API: http://localhost:8888/api
- Swagger UI: http://localhost:8888/api/swagger-ui.html
- Backend health check: http://localhost:8888/api/actuator/health
- Keycloak Admin Console: http://localhost:8182
- File manager health check: http://localhost:8889/actuator/health

Important: this scenario uses local `postgres`, `postgres-keycloak`, `keycloak`, and `inlive-file-manager` services from `docker-compose.yml`, not Railway private PostgreSQL.

## Local Backend Run Without a Docker Image

Use this option if you want to run only the backend through Gradle while keeping the infrastructure in Docker.

### 1. Start Infrastructure

For the local backend + Railway Keycloak/File API setup, only PostgreSQL is enough:

```bash
docker compose up -d postgres
```

For a fully local stack, you can start:

```bash
docker compose up -d postgres postgres-keycloak keycloak inlive-file-manager
```

### 2. Export Environment Variables

Example for local backend + Railway Keycloak/File API:

```bash
export KEYCLOAK_URL=https://keycloak-production-4101.up.railway.app
export KEYCLOAK_REALM=inlive
export KEYCLOAK_CLIENT_ID=inlive
export KEYCLOAK_ADMIN="<current admin email>"
export KEYCLOAK_ADMIN_PASSWORD="<current admin password>"
export KEYCLOAK_CLIENT_SECRET="<current client secret>"

export FILE_API_URL=https://adorable-trust-production-169a.up.railway.app

export POSTGRES_ADDRESS=localhost
export POSTGRES_ADDRESS_PORT=5434
export POSTGRES_DB_NAME=inlive
export DATABASE_USERNAME=postgres
export DATABASE_PASSWORD=postgres

export APP_ALLOWED_ORIGINS="https://inlive-frontend.vercel.app,http://localhost:3000,http://localhost:3001"
```

### 3. Run the Application

```bash
./gradlew bootRun
```

URLs for the local Gradle run:

- Backend API: http://localhost:8080/api
- Swagger UI: http://localhost:8080/api/swagger-ui.html
- Health check: http://localhost:8080/api/actuator/health

## If You Run the File Manager Locally

For local `inlive-file-manager`, use separate credentials from `file_manager_env2.txt` in Google Drive:

```env
KEYCLOAK_URL=https://keycloak-production-4101.up.railway.app
KEYCLOAK_REALM=inlive
KEYCLOAK_CLIENT_ID=inlive
AWS_ACCESS_KEY_ID=<current access key>
AWS_SECRET_ACCESS_KEY=<current secret key>
AWS_BUCKET=ui-tap-bucket
```

Detailed instructions are in [inlive-file-manager/README.md](./inlive-file-manager/README.md).

## Build and Tests

Build the project:

```bash
./gradlew build
```

Run tests:

```bash
./gradlew test
```

Build the executable jar:

```bash
./gradlew bootJar
```

The resulting jar will be created in `build/libs`.

## Main Environment Variables

| Variable | Purpose |
|----------|---------|
| `KEYCLOAK_URL` | Keycloak URL |
| `KEYCLOAK_REALM` | Application realm |
| `KEYCLOAK_CLIENT_ID` | Application client ID |
| `KEYCLOAK_CLIENT_SECRET` | Secret for the `inlive` client |
| `KEYCLOAK_ADMIN` | Keycloak admin login |
| `KEYCLOAK_ADMIN_PASSWORD` | Keycloak admin password |
| `POSTGRES_ADDRESS` | Backend PostgreSQL host |
| `POSTGRES_ADDRESS_PORT` | Backend PostgreSQL port |
| `POSTGRES_DB_NAME` | Backend database name |
| `DATABASE_USERNAME` | PostgreSQL user |
| `DATABASE_PASSWORD` | PostgreSQL password |
| `FILE_API_URL` | `inlive-file-manager` service URL |
| `APP_ALLOWED_ORIGINS` | Allowed frontend origins |
| `PORT` | Backend process port, defaults to `8080` |
| `AWS_ACCESS_KEY_ID` | Object storage access key for file manager |
| `AWS_SECRET_ACCESS_KEY` | Object storage secret key for file manager |
| `AWS_BUCKET` | File manager bucket |

## Stopping the Project

Stop containers:

```bash
docker compose down
```

Stop containers and remove PostgreSQL data volumes:

```bash
docker compose down -v
```

The `-v` command removes local database data, so use it only when you want to fully clean the environment.
