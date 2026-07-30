### Task 13: README Documentation

**Files:**
- Create: `C:\Users\jorel\Desktop\template-api\README.md`

**Interfaces:**
- Consumes: All project components
- Produces: Complete project documentation

- [ ] **Step 1: Create README.md**

```markdown
# Template API

Plantilla base para proyectos Spring Boot con arquitectura en capas, logging AOP, manejo de excepciones, y conexión a MySQL usando JDBI con HikariCP.

## Stack Tecnológico

- **Java:** 25 (Microsoft OpenJDK 25.0.3)
- **Spring Boot:** 4.1.0
- **Spring Framework:** 7.0.8
- **Maven:** 3.x
- **Lombok:** Latest
- **JDBI:** 3.x (para MySQL)
- **HikariCP:** Connection pooling
- **MySQL Connector:** 8.x
- **springdoc-openapi:** 2.x (Swagger UI)
- **JUnit 5:** Unit testing
- **Mockito:** Mocking framework
- **Testcontainers:** Integration testing
- **Logback:** Logging

## Estructura del Proyecto

```
src/main/java/com/jorel/template_api/
├── aspect/          → LoggingAspect (AOP)
├── config/          → AppConfig
├── controller/      → PingController
├── domain/          → HealthCheck entity
├── dtos/            → ErrorResponse
├── enums/           → Environment
├── exceptions/      → Custom exceptions + GlobalExceptionHandler
├── persistence/
│   ├── dao/         → HealthCheckDao (JDBI)
│   ├── vos/         → HealthCheckVO
│   └── datasource/  → HikariDataSourceProvider
├── service/         → PingService, HealthCheckService
└── util/            → ScopeUtils
```

## Cómo Levantar el Proyecto

### Requisitos Previos
- Java 25 instalado
- Maven 3.x
- Docker (opcional, para MySQL)

### Pasos

1. **Clonar el proyecto:**
   ```bash
   git clone <repository-url>
   cd template-api
   ```

2. **Iniciar MySQL con Docker (opcional):**
   ```bash
   docker-compose up -d
   ```

3. **Compilar el proyecto:**
   ```bash
   mvn clean compile
   ```

4. **Ejecutar la aplicación:**
   ```bash
   mvn spring-boot:run
   ```

5. **O ejecutar con perfil específico:**
   ```bash
   mvn spring-boot:run -Dspring-boot.run.profiles=beta
   ```

## Endpoints Disponibles

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/v1/ping` | Health check básico, retorna "pong" |
| GET | `/actuator/health` | Spring Boot Actuator health check |

## Documentación API

- **Swagger UI:** http://localhost:8080/swagger-ui.html
- **OpenAPI JSON:** http://localhost:8080/v3/api-docs

## Configuración de BD

### Perfiles de Ambiente

| Perfil | Descripción | Pool Size | Logging |
|--------|-------------|-----------|---------|
| local | Desarrollo local | 5 conexiones | DEBUG |
| beta | Staging/Testing | 10 conexiones | INFO |
| prod | Producción | 20 conexiones | WARN |

### Variables de Entorno (beta/prod)

```bash
export DB_USERNAME=your_username
export DB_PASSWORD=your_password
```

## Cómo Correr Tests

### Unit Tests
```bash
mvn test
```

### Integration Tests (con Testcontainers)
```bash
mvn verify -Pintegration-tests
```

## Docker Compose

El archivo `docker-compose.yml` incluye MySQL 8.0 para desarrollo local:

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

## Comportamiento sin BD

El proyecto levanta incluso si la base de datos no está disponible:
- Se muestra un warning en el log
- Los endpoints que dependen de BD retornan error controlado (503)
- El endpoint `/ping` funciona sin BD
```

- [ ] **Step 2: Commit**

```bash
git add README.md
git commit -m "docs: add README with technical specifications and usage instructions"
```
