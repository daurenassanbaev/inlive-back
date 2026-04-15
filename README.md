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
