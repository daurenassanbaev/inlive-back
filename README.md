# Inlive Back

Backend-сервис платформы Inlive на Java 21 и Spring Boot.

## Что внутри

- REST API на Spring Boot
- PostgreSQL через Spring Data JPA / Hibernate
- Авторизация через Keycloak и JWT
- Swagger / OpenAPI документация
- Spring Boot Actuator для health-check и метрик
- Интеграция с отдельным сервисом файлов `inlive-file-manager`
- Gradle wrapper для сборки без локальной установки Gradle

## Требования

Для локального запуска понадобятся:

- Java 21
- Docker и Docker Compose
- Git

Gradle устанавливать отдельно не нужно: в проекте есть `./gradlew`.

## Быстрый запуск через Docker Compose

1. Клонируйте проект и перейдите в папку:

```bash
git clone https://github.com/daurenassanbaev/inlive-back.git
cd inlive-back
```

2. Создайте файл `.env` в корне проекта:

```env
API_URL=http://localhost:8888/api

KEYCLOAK_URL=http://keycloak:8080
KEYCLOAK_REALM=inlive
KEYCLOAK_CLIENT_ID=inlive
KEYCLOAK_CLIENT_SECRET=change-me
KEYCLOAK_ADMIN=admin
KEYCLOAK_ADMIN_PASSWORD=admin

POSTGRES_ADDRESS=postgres
POSTGRES_ADDRESS_PORT=5432
POSTGRES_DB_NAME=inlive
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=postgres

FILE_API_URL=http://inlive-file-manager:8888

KC_DB=postgres
KC_DB_URL_HOST=postgres-keycloak
KC_DB_URL_PORT=5432
KC_DB_URL_DATABASE=keycloak
KC_DB_USERNAME=postgres
KC_DB_PASSWORD=postgres
KC_HOSTNAME_STRICT=false
KC_HOSTNAME_STRICT_HTTPS=false
KC_PROXY=edge

AWS_ACCESS_KEY_ID=test
AWS_SECRET_ACCESS_KEY=test
AWS_BUCKET=test
```

3. Соберите Docker-образы backend и file-manager:

```bash
docker build -t app/backend:latest .
docker build -t app/file-manager:latest ./inlive-file-manager
```

Если фронтенд не нужен, можно закомментировать сервис `front` в `docker-compose.yml` или запускать только backend-зависимости.

4. Запустите сервисы:

```bash
docker compose up -d
```

5. Проверьте, что контейнеры поднялись:

```bash
docker compose ps
```

Полезные адреса после запуска:

- Backend API: http://localhost:8888/api
- Swagger UI: http://localhost:8888/api/swagger-ui.html
- Health-check: http://localhost:8888/api/actuator/health
- Keycloak Admin Console: http://localhost:8182
- File manager health-check: http://localhost:8889/api/actuator/health

Для входа в Keycloak Admin Console используйте значения `KEYCLOAK_ADMIN` и `KEYCLOAK_ADMIN_PASSWORD` из `.env`.

## Локальный запуск backend без Docker-образа

Этот вариант удобен для разработки: PostgreSQL и Keycloak запускаются в Docker, а backend запускается через Gradle на машине разработчика.

1. Поднимите инфраструктуру:

```bash
docker compose up -d postgres postgres-keycloak keycloak inlive-file-manager
```

2. Экспортируйте переменные окружения для backend:

```bash
export KEYCLOAK_URL=http://localhost:8182
export KEYCLOAK_REALM=inlive
export KEYCLOAK_CLIENT_SECRET=change-me
export KEYCLOAK_ADMIN=admin
export KEYCLOAK_ADMIN_PASSWORD=admin

export POSTGRES_ADDRESS=localhost
export POSTGRES_ADDRESS_PORT=5434
export POSTGRES_DB_NAME=inlive
export DATABASE_USERNAME=postgres
export DATABASE_PASSWORD=postgres

export FILE_API_URL=http://localhost:8889
```

3. Запустите приложение:

```bash
./gradlew bootRun
```

По умолчанию backend стартует на порту `8080`.

Адреса при локальном запуске через Gradle:

- Backend API: http://localhost:8080/api
- Swagger UI: http://localhost:8080/api/swagger-ui.html
- Health-check: http://localhost:8080/api/actuator/health

## Настройка Keycloak

После первого запуска Keycloak нужно создать realm и client, которые используются backend-сервисом.

Минимальная настройка:

1. Откройте http://localhost:8182.
2. Войдите под админом из `.env`.
3. Создайте realm `inlive`.
4. Создайте client `inlive`.
5. Включите client authentication, если используете `KEYCLOAK_CLIENT_SECRET`.
6. Скопируйте client secret и укажите его в `KEYCLOAK_CLIENT_SECRET`.
7. Создайте нужные роли приложения в realm/client в соответствии с бизнес-логикой проекта.

Если `KEYCLOAK_REALM`, `client-id` или secret отличаются, обновите соответствующие переменные окружения.

## Сборка и тесты

Собрать проект:

```bash
./gradlew build
```

Запустить тесты:

```bash
./gradlew test
```

Собрать исполняемый jar:

```bash
./gradlew bootJar
```

Готовый jar появится в папке `build/libs`.

## Основные переменные окружения

| Переменная | Назначение |
|------------|------------|
| `KEYCLOAK_URL` | URL Keycloak |
| `KEYCLOAK_REALM` | Realm приложения |
| `KEYCLOAK_CLIENT_SECRET` | Secret клиента `inlive` |
| `KEYCLOAK_ADMIN` | Логин администратора Keycloak |
| `KEYCLOAK_ADMIN_PASSWORD` | Пароль администратора Keycloak |
| `POSTGRES_ADDRESS` | Хост PostgreSQL для backend |
| `POSTGRES_ADDRESS_PORT` | Порт PostgreSQL для backend |
| `POSTGRES_DB_NAME` | Название базы данных backend |
| `DATABASE_USERNAME` | Пользователь PostgreSQL |
| `DATABASE_PASSWORD` | Пароль PostgreSQL |
| `FILE_API_URL` | URL сервиса `inlive-file-manager` |
| `PORT` | Порт backend внутри процесса, по умолчанию `8080` |

## Остановка проекта

Остановить контейнеры:

```bash
docker compose down
```

Остановить контейнеры и удалить volumes с данными PostgreSQL:

```bash
docker compose down -v
```

Команда с `-v` удалит локальные данные баз данных, поэтому используйте ее только если хотите полностью очистить окружение.
