# Railway Deploy

Этот проект проще всего выкатывать в Railway как набор отдельных сервисов:

1. `inlive-back`
2. `inlive-file-manager`
3. `postgres` для backend
4. `keycloak`
5. `postgres-keycloak` для Keycloak

## Почему Railway

- Railway умеет собирать сервисы из `Dockerfile`.
- У Railway есть готовый PostgreSQL сервис.
- Railway поддерживает несколько сервисов в одном проекте и удобно связывает их переменными окружения.

Официальные доки:
- Dockerfile deployments: https://docs.railway.com/deploy/dockerfiles
- PostgreSQL: https://docs.railway.com/guides/postgresql
- Monorepo / root directory: https://docs.railway.com/guides/monorepo
- Variables: https://docs.railway.com/reference/variables

## Что деплоить

### 1. Backend

- Source: этот репозиторий
- Root Directory: `/`
- Dockerfile: `Dockerfile`
- Public networking: включить
- Healthcheck path: `/api/actuator/health`

Переменные:

```env
KEYCLOAK_URL=https://<your-keycloak-domain>
KEYCLOAK_REALM=inlive
KEYCLOAK_CLIENT_ID=inlive
KEYCLOAK_CLIENT_SECRET=<secret>
KEYCLOAK_ADMIN=<admin-login>
KEYCLOAK_ADMIN_PASSWORD=<admin-password>

POSTGRES_ADDRESS=<backend-postgres-host>
POSTGRES_ADDRESS_PORT=<backend-postgres-port>
POSTGRES_DB_NAME=<backend-postgres-db>
DATABASE_USERNAME=<backend-postgres-user>
DATABASE_PASSWORD=<backend-postgres-password>

FILE_API_URL=https://<your-file-manager-domain>
APP_ALLOWED_ORIGINS=https://<your-frontend-domain>
```

### 2. File manager

- Source: этот репозиторий
- Root Directory: `/inlive-file-manager`
- Dockerfile: `Dockerfile`
- Public networking: включить
- Healthcheck path: `/actuator/health`

Переменные:

```env
KEYCLOAK_URL=https://<your-keycloak-domain>
KEYCLOAK_REALM=inlive
KEYCLOAK_CLIENT_ID=inlive

AWS_ACCESS_KEY_ID=<access-key>
AWS_SECRET_ACCESS_KEY=<secret-key>
AWS_BUCKET=<bucket-name>
```

### 3. Backend Postgres

Создать Railway PostgreSQL service.

Из него взять:

```env
PGHOST
PGPORT
PGDATABASE
PGUSER
PGPASSWORD
```

И пробросить их в backend как:

```env
POSTGRES_ADDRESS=${{Postgres.PGHOST}}
POSTGRES_ADDRESS_PORT=${{Postgres.PGPORT}}
POSTGRES_DB_NAME=${{Postgres.PGDATABASE}}
DATABASE_USERNAME=${{Postgres.PGUSER}}
DATABASE_PASSWORD=${{Postgres.PGPASSWORD}}
```

### 4. Keycloak

Самый быстрый путь: взять готовый Railway template для Keycloak и отдельный Postgres под него.

Template:
- https://railway.com/template/keycloak

После деплоя:

1. Открыть Keycloak admin console.
2. Создать realm `inlive`.
3. Создать client `inlive`.
4. Включить `Client authentication`, если нужен secret.
5. Скопировать `client secret` в backend переменную `KEYCLOAK_CLIENT_SECRET`.
6. Создать роли, которые ожидает backend.
7. Создать тестовых пользователей для защиты.

## Что отдать фронтендеру

Фронтендеру нужен backend URL:

```text
https://<backend-domain>/api
```

Если фронт логинится напрямую через Keycloak, еще понадобится:

```text
https://<keycloak-domain>/realms/inlive
```

## Важный момент

В репозитории сейчас нет экспортированного realm-конфига Keycloak. Значит realm, client, роли и тестовых пользователей нужно будет один раз завести вручную в внешнем Keycloak или импортировать из вашего текущего локального Keycloak, если он у вас уже настроен.
