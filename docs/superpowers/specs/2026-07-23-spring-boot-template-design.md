# Spring Boot Template API - Design Spec

## Overview

Plantilla base para proyectos Spring Boot con arquitectura en capas tradicional, logging AOP, manejo de excepciones, y conexión a MySQL usando JDBI con HikariCP. Compatible con Java 25.

## Tech Stack

| Component | Version |
|-----------|---------|
| Java | 25 (Microsoft OpenJDK 25.0.3) |
| Spring Boot | 4.1.0 |
| Spring Framework | 7.0.8 |
| Maven | 3.x |
| Lombok | Latest |
| JDBI | 3.x |
| HikariCP | Latest |
| MySQL Connector | 8.x |
| springdoc-openapi | 2.x |
| JUnit 5 | Latest |
| Mockito | Latest |
| Testcontainers | Latest |
| Logback | Latest |

## Project Structure

```
C:\Users\jorel\Desktop\template-api\
├── pom.xml
├── docker-compose.yml
├── src/
│   ├── main/java/com/jorel/template_api/
│   │   ├── TemplateApiApplication.java
│   │   ├── aspect/
│   │   │   └── LoggingAspect.java
│   │   ├── config/
│   │   │   └── AppConfig.java
│   │   ├── controller/
│   │   │   └── PingController.java
│   │   ├── domain/
│   │   │   └── HealthCheck.java
│   │   ├── dtos/
│   │   │   └── ErrorResponse.java
│   │   ├── enums/
│   │   │   └── Environment.java
│   │   ├── exceptions/
│   │   │   ├── ResourceNotFoundException.java
│   │   │   ├── BadRequestException.java
│   │   │   ├── ConflictException.java
│   │   │   ├── DataBaseNotAvailableException.java
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── persistence/
│   │   │   ├── dao/
│   │   │   │   └── HealthCheckDao.java
│   │   │   ├── vos/
│   │   │   │   └── HealthCheckVO.java
│   │   │   └── datasource/
│   │   │       └── HikariDataSourceProvider.java
│   │   ├── service/
│   │   │   ├── PingService.java
│   │   │   └── HealthCheckService.java
│   │   └── util/
│   │       └── ScopeUtils.java
│   ├── main/resources/
│   │   ├── application.yml
│   │   ├── application-local.yml
│   │   ├── application-beta.yml
│   │   ├── application-prod.yml
│   │   ├── schema.sql
│   │   └── data.sql
│   └── test/java/com/jorel/template_api/
│       ├── TemplateApiApplicationTests.java
│       ├── controller/
│       │   └── PingControllerTest.java
│       ├── service/
│       │   ├── PingServiceTest.java
│       │   └── HealthCheckServiceTest.java
│       └── persistence/
│           └── HealthCheckDaoTest.java
└── docs/
    └── superpowers/
        └── specs/
            └── 2026-07-23-spring-boot-template-design.md
```

## Configuration

### application.yml (Principal)

```yaml
spring:
  profiles:
    active: local
  application:
    name: template-api

server:
  port: 8080

management:
  endpoints:
    web:
      exposure:
        include: health,info
```

### application-local.yml

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/test
    username: root
    password: root
    driver-class-name: com.mysql.cj.jdbc.Driver
  hikari:
    maximum-pool-size: 5
    minimum-idle: 2
    connection-timeout: 30000

logging:
  level:
    com.jorel.template_api: DEBUG
    root: INFO
```

### application-beta.yml

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/test_beta
    username: ${DB_USERNAME:root}
    password: ${DB_PASSWORD:root}
    driver-class-name: com.mysql.cj.jdbc.Driver
  hikari:
    maximum-pool-size: 10
    minimum-idle: 5

logging:
  level:
    com.jorel.template_api: INFO
    root: WARN
```

### application-prod.yml

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/test_prod
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
    driver-class-name: com.mysql.cj.jdbc.Driver
  hikari:
    maximum-pool-size: 20
    minimum-idle: 10
    connection-timeout: 5000

logging:
  level:
    com.jorel.template_api: WARN
    root: ERROR
```

## Components

### 1. PingController + PingService

**Endpoint:** `GET /api/v1/ping`

**Response:** HTTP 200 with body `"pong"`

```java
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PingController {
    private final PingService pingService;

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok(pingService.ping());
    }
}
```

```java
@Service
@RequiredArgsConstructor
public class PingService {
    public String ping() {
        return "pong";
    }
}
```

### 2. Exception Handling

**Custom Exceptions:**
- `ResourceNotFoundException` → 404
- `BadRequestException` → 400
- `ConflictException` → 409
- `DataBaseNotAvailableException` → 503

**GlobalExceptionHandler:**
- `@RestControllerAdvice` with `@Slf4j`
- Maps each exception to appropriate HTTP status
- Returns `ErrorResponse` DTO with status code and message
- Logs warnings for 4xx, errors for 5xx

**ErrorResponse DTO:**
```java
@Data
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
}
```

### 3. Logging AOP (LoggingAspect)

**Pointcuts:**
- `execution(* com.jorel.template_api.controller..*(..))` → INFO level
- `execution(* com.jorel.template_api.service..*(..))` → DEBUG level
- `execution(* com.jorel.template_api.persistence..*(..))` → DEBUG level

**Behavior:**
- Logs method entry with method name
- Logs method exit with execution time in ms
- Logs errors with exception class name
- Uses SLF4J + Logback

### 4. HikariDataSourceProvider

**Behavior:**
1. Reads `spring.datasource.url` from active profile
2. If URL not configured → log warning, project starts without DB
3. If connection fails → log warning, project starts without DB
4. If connection succeeds → log success, HikariCP pool active
5. `isDbAvailable()` method for other components to check DB status

### 5. ScopeUtils

**Methods:**
- `isLocal()`, `isBeta()`, `isProd()` → environment checks
- `getActiveProfile()` → returns current profile name
- `logProfileInfo()` → logs environment info at startup

### 6. AppConfig

- `@Configuration` class
- `@PostConstruct` calls `scopeUtils.logProfileInfo()`
- Bean configurations for the application

### 7. HealthCheck (Domain)

Simple entity for testing database connectivity:
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthCheck {
    private Long id;
    private String status;
    private LocalDateTime createdAt;
}
```

### 8. HealthCheckService

Service for database health check operations, using `HikariDataSourceProvider.isDbAvailable()` to gracefully handle missing DB.

## Database

### schema.sql

```sql
CREATE TABLE IF NOT EXISTS health_check (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### data.sql

```sql
INSERT INTO health_check (status) VALUES ('OK') ON DUPLICATE KEY UPDATE status = 'OK';
```

### Docker Compose

```yaml
version: '3.8'
services:
  mysql:
    image: mysql:8.0
    container_name: template-api-mysql
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: test
    ports:
      - "3306:3306"
    volumes:
      - mysql-data:/var/lib/mysql

volumes:
  mysql-data:
```

## Testing

### Unit Tests (JUnit 5 + Mockito)

- `PingServiceTest` → unit test for ping method
- `PingControllerTest` → `@WebMvcTest` with MockMvc
- `HealthCheckServiceTest` → unit test with mocked datasource

### Integration Tests (Testcontainers)

- `HealthCheckDaoTest` → tests DAO with real MySQL via Testcontainers
- Tests verify database connectivity and CRUD operations

### Test Structure

```
src/test/java/com/jorel/template_api/
├── TemplateApiApplicationTests.java
├── controller/
│   └── PingControllerTest.java
├── service/
│   ├── PingServiceTest.java
│   └── HealthCheckServiceTest.java
└── persistence/
    └── HealthCheckDaoTest.java
```

## API Documentation

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Configured via `springdoc-openapi-starter-webmvc-ui`

## Profiles

| Profile | Description | DB Pool | Logging |
|---------|-------------|---------|---------|
| local | Development | 5 connections | DEBUG |
| beta | Staging/Testing | 10 connections | INFO |
| prod | Production | 20 connections | WARN |

## Non-Functional Requirements

- Project starts even if database is unavailable
- Graceful degradation with warning logs
- AOP logging for observability
- Structured exception handling
- Environment-aware configuration
- Docker support for local development

## README.md

Crear `README.md` en la raíz del proyecto con:
- Nombre del proyecto y descripción (plantilla Spring Boot)
- Stack tecnológico completo (Java 25, Spring Boot 4.1.0, Maven, Lombok, JDBI, HikariCP, MySQL, Testcontainers)
- Estructura del proyecto
- Cómo levantar el proyecto (Maven + perfiles)
- Cómo correr tests
- Endpoints disponibles
- Configuración de BD (Docker Compose)
- Perfiles de ambiente (local, beta, prod)

## Decisions

1. **No hexagonal architecture** → simplified layered architecture for template usability
2. **YAML only** → no .properties files
3. **Lombok enabled** → reduces boilerplate
4. **Testcontainers for integration tests** → real MySQL testing
5. **HikariCP** → connection pooling with configurable pool sizes per environment
6. **springdoc-openapi** → automatic Swagger documentation
7. **Maven** → build tool (no Gradle)
8. **README.md** → technical documentation at project root
