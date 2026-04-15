# Inlive Back

Backend service for the Inlive platform built with Java 21 and Spring Boot.

## Problem Statement
This project provides the backend infrastructure for the Inlive platform. It is responsible for handling business logic, security, API endpoints, database access, documentation, and integration with internal modules.

## Features
- REST API built with Spring Boot
- Authentication and authorization with Spring Security and OAuth2
- PostgreSQL database integration with Spring Data JPA
- OpenFeign support for external/internal service communication
- Keycloak admin client integration
- Swagger / OpenAPI documentation
- Actuator for monitoring
- Modular project structure with internal client library support

## Project Structure
```text
.
├── src/                    # Main backend source code
├── client-libs/            # Internal reusable modules
├── docs/                   # Documentation files
├── tests/                  # Test-related materials
├── assets/                 # Images and screenshots
├── gradle/                 # Gradle wrapper files
├── build.gradle.kts
├── settings.gradle.kts
├── README.md
├── AUDIT.md
└── .gitignore
```

## 🛠 Tech Stack
- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Spring Cloud OpenFeign
- PostgreSQL
- Keycloak
- Swagger / OpenAPI
- Gradle (Kotlin DSL)

## ⚙️ Installation

1. Clone repository:
```bash
git clone https://github.com/daurenassanbaev/inlive-back.git
cd inlive-back

2. Configure application properties:
Set up PostgreSQL connection

3. Configure environment variables if needed
Build the project:
./gradlew build

▶️ Running the Application
./gradlew bootRun
```
Application will start on the configured port (default: 8080).

📖 API Documentation

Swagger UI is available after running the application (if enabled):

http://localhost:8080/swagger-ui/index.html

🧪 Testing
(If no tests yet, оставь так — это нормально)

Unit and integration tests can be placed in the tests/ directory


Docker and ci/cd tests will be added later
