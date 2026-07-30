# Spring Boot Template API Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Create a Spring Boot 4.1.0 template project with Java 25, layered architecture, AOP logging, exception handling, JDBI+HikariCP MySQL connection, and comprehensive testing.

**Architecture:** Layered architecture with controller → service → persistence flow. AOP logging across all layers. Graceful degradation when database is unavailable.

**Tech Stack:** Java 25, Spring Boot 4.1.0, Maven, Lombok, JDBI 3.x, HikariCP, MySQL 8.x, springdoc-openapi 2.x, JUnit 5, Mockito, Testcontainers, Logback

## Global Constraints

- Java 25 (Microsoft OpenJDK 25.0.3)
- Spring Boot 4.1.0
- Maven build tool
- YAML configuration only (no .properties)
- Package base: com.jorel.template_api
- Lombok enabled
- Project location: C:\Users\jorel\Desktop\template-api

---

## File Structure

```
C:\Users\jorel\Desktop\template-api\
├── pom.xml
├── docker-compose.yml
├── README.md
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
        ├── specs/
        │   └── 2026-07-23-spring-boot-template-design.md
        └── plans/
            └── 2026-07-23-spring-boot-template-plan.md
```

---

### Task 1: Project Scaffolding and Maven Configuration

**Files:**
- Create: `C:\Users\jorel\Desktop\template-api\pom.xml`
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\TemplateApiApplication.java`

**Interfaces:**
- Consumes: None (first task)
- Produces: Maven project structure, main application class

- [ ] **Step 1: Create project directory structure**

```powershell
mkdir "C:\Users\jorel\Desktop\template-api"
mkdir "C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\aspect"
mkdir "C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\config"
mkdir "C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\controller"
mkdir "C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\domain"
mkdir "C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\dtos"
mkdir "C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\enums"
mkdir "C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\exceptions"
mkdir "C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\persistence\dao"
mkdir "C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\persistence\vos"
mkdir "C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\persistence\datasource"
mkdir "C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\service"
mkdir "C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\util"
mkdir "C:\Users\jorel\Desktop\template-api\src\main\resources"
mkdir "C:\Users\jorel\Desktop\template-api\src\test\java\com\jorel\template_api\controller"
mkdir "C:\Users\jorel\Desktop\template-api\src\test\java\com\jorel\template_api\service"
mkdir "C:\Users\jorel\Desktop\template-api\src\test\java\com\jorel\template_api\persistence"
```

- [ ] **Step 2: Create pom.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>4.1.0</version>
        <relativePath/>
    </parent>
    <groupId>com.jorel</groupId>
    <artifactId>template-api</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>template-api</name>
    <description>Spring Boot Template API Project</description>
    <properties>
        <java.version>25</java.version>
        <jdbi.version>3.47.0</jdbi.version>
        <springdoc.version>2.8.6</springdoc.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springdoc</groupId>
            <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
            <version>${springdoc.version}</version>
        </dependency>
        <dependency>
            <groupId>org.jdbi</groupId>
            <artifactId>jdbi3-core</artifactId>
            <version>${jdbi.version}</version>
        </dependency>
        <dependency>
            <groupId>org.jdbi</groupId>
            <artifactId>jdbi3-mysql</artifactId>
            <version>${jdbi.version}</version>
        </dependency>
        <dependency>
            <groupId>org.jdbi</groupId>
            <artifactId>jdbi3-sqlobject</artifactId>
            <version>${jdbi.version}</version>
        </dependency>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>com.zaxxer</groupId>
            <artifactId>HikariCP</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>mysql</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.testcontainers</groupId>
            <artifactId>junit-jupiter</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

- [ ] **Step 3: Create TemplateApiApplication.java**

```java
package com.jorel.template_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TemplateApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplateApiApplication.class, args);
    }
}
```

- [ ] **Step 4: Verify project compiles**

Run: `mvn compile`
Expected: BUILD SUCCESS

- [ ] **Step 5: Commit**

```bash
git init
git add .
git commit -m "feat: initial project scaffolding with Maven and Java 25"
```

---

### Task 2: Configuration Files (YAML)

**Files:**
- Create: `C:\Users\jorel\Desktop\template-api\src\main\resources\application.yml`
- Create: `C:\Users\jorel\Desktop\template-api\src\main\resources\application-local.yml`
- Create: `C:\Users\jorel\Desktop\template-api\src\main\resources\application-beta.yml`
- Create: `C:\Users\jorel\Desktop\template-api\src\main\resources\application-prod.yml`

**Interfaces:**
- Consumes: None
- Produces: YAML configuration files for all environments

- [ ] **Step 1: Create application.yml**

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

- [ ] **Step 2: Create application-local.yml**

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

- [ ] **Step 3: Create application-beta.yml**

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

- [ ] **Step 4: Create application-prod.yml**

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

- [ ] **Step 5: Verify configuration loads**

Run: `mvn spring-boot:run`
Expected: Application starts on port 8080 with local profile

- [ ] **Step 6: Commit**

```bash
git add src/main/resources/
git commit -m "feat: add YAML configuration files for all environments"
```

---

### Task 3: Domain Layer (HealthCheck Entity)

**Files:**
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\domain\HealthCheck.java`
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\dtos\ErrorResponse.java`
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\enums\Environment.java`

**Interfaces:**
- Consumes: None
- Produces: HealthCheck entity, ErrorResponse DTO, Environment enum

- [ ] **Step 1: Create HealthCheck.java**

```java
package com.jorel.template_api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthCheck {
    private Long id;
    private String status;
    private LocalDateTime createdAt;
}
```

- [ ] **Step 2: Create ErrorResponse.java**

```java
package com.jorel.template_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
}
```

- [ ] **Step 3: Create Environment.java**

```java
package com.jorel.template_api.enums;

public enum Environment {
    LOCAL("local", "Desarrollo Local"),
    BETA("beta", "Staging/Testing"),
    PROD("prod", "Producción");

    private final String profile;
    private final String description;

    Environment(String profile, String description) {
        this.profile = profile;
        this.description = description;
    }

    public String getProfile() {
        return profile;
    }

    public String getDescription() {
        return description;
    }
}
```

- [ ] **Step 4: Commit**

```bash
git add src/main/java/com/jorel/template_api/domain/
git add src/main/java/com/jorel/template_api/dtos/
git add src/main/java/com/jorel/template_api/enums/
git commit -m "feat: add domain layer with HealthCheck entity, ErrorResponse DTO, and Environment enum"
```

---

### Task 4: Exception Handling

**Files:**
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\exceptions\ResourceNotFoundException.java`
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\exceptions\BadRequestException.java`
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\exceptions\ConflictException.java`
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\exceptions\DataBaseNotAvailableException.java`
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\exceptions\GlobalExceptionHandler.java`

**Interfaces:**
- Consumes: ErrorResponse DTO
- Produces: Custom exceptions, GlobalExceptionHandler

- [ ] **Step 1: Create ResourceNotFoundException.java**

```java
package com.jorel.template_api.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
```

- [ ] **Step 2: Create BadRequestException.java**

```java
package com.jorel.template_api.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
```

- [ ] **Step 3: Create ConflictException.java**

```java
package com.jorel.template_api.exceptions;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
```

- [ ] **Step 4: Create DataBaseNotAvailableException.java**

```java
package com.jorel.template_api.exceptions;

public class DataBaseNotAvailableException extends RuntimeException {
    public DataBaseNotAvailableException(String message) {
        super(message);
    }
}
```

- [ ] **Step 5: Create GlobalExceptionHandler.java**

```java
package com.jorel.template_api.exceptions;

import com.jorel.template_api.dtos.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        log.warn("Recurso no encontrado: {}", ex.getMessage());
        return ResponseEntity.status(404).body(new ErrorResponse(404, ex.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex) {
        log.warn("Solicitud inválida: {}", ex.getMessage());
        return ResponseEntity.status(400).body(new ErrorResponse(400, ex.getMessage()));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ConflictException ex) {
        log.warn("Conflicto: {}", ex.getMessage());
        return ResponseEntity.status(409).body(new ErrorResponse(409, ex.getMessage()));
    }

    @ExceptionHandler(DataBaseNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleDBUnavailable(DataBaseNotAvailableException ex) {
        log.error("Base de datos no disponible: {}", ex.getMessage());
        return ResponseEntity.status(503).body(new ErrorResponse(503, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        log.error("Error inesperado: {}", ex.getMessage(), ex);
        return ResponseEntity.status(500).body(new ErrorResponse(500, "Error interno del servidor"));
    }
}
```

- [ ] **Step 6: Commit**

```bash
git add src/main/java/com/jorel/template_api/exceptions/
git commit -m "feat: add custom exceptions and GlobalExceptionHandler"
```

---

### Task 5: Utility Classes (ScopeUtils and AppConfig)

**Files:**
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\util\ScopeUtils.java`
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\config\AppConfig.java`

**Interfaces:**
- Consumes: Environment enum
- Produces: ScopeUtils, AppConfig

- [ ] **Step 1: Create ScopeUtils.java**

```java
package com.jorel.template_api.util;

import com.jorel.template_api.enums.Environment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScopeUtils {

    @Value("${spring.profiles.active:local}")
    private String activeProfile;

    public boolean isLocal() {
        return Environment.LOCAL.getProfile().equals(activeProfile);
    }

    public boolean isBeta() {
        return Environment.BETA.getProfile().equals(activeProfile);
    }

    public boolean isProd() {
        return Environment.PROD.getProfile().equals(activeProfile);
    }

    public String getActiveProfile() {
        return activeProfile;
    }

    public void logProfileInfo() {
        log.info("========================================");
        log.info("  Perfil activo: {}", activeProfile.toUpperCase());
        log.info("  Entorno: {}", getEnvironmentDescription());
        log.info("========================================");
    }

    private String getEnvironmentDescription() {
        return switch (activeProfile) {
            case "local" -> Environment.LOCAL.getDescription();
            case "beta" -> Environment.BETA.getDescription();
            case "prod" -> Environment.PROD.getDescription();
            default -> "Desconocido";
        };
    }
}
```

- [ ] **Step 2: Create AppConfig.java**

```java
package com.jorel.template_api.config;

import com.jorel.template_api.util.ScopeUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AppConfig {

    private final ScopeUtils scopeUtils;

    @PostConstruct
    public void init() {
        scopeUtils.logProfileInfo();
    }
}
```

- [ ] **Step 3: Commit**

```bash
git add src/main/java/com/jorel/template_api/util/
git add src/main/java/com/jorel/template_api/config/
git commit -m "feat: add ScopeUtils and AppConfig for environment management"
```

---

### Task 6: Service Layer (PingService and HealthCheckService)

**Files:**
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\service\PingService.java`
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\service\HealthCheckService.java`

**Interfaces:**
- Consumes: HealthCheck entity, DataBaseNotAvailableException
- Produces: PingService, HealthCheckService

- [ ] **Step 1: Create PingService.java**

```java
package com.jorel.template_api.service;

import org.springframework.stereotype.Service;

@Service
public class PingService {

    public String ping() {
        return "pong";
    }
}
```

- [ ] **Step 2: Create HealthCheckService.java**

```java
package com.jorel.template_api.service;

import com.jorel.template_api.domain.HealthCheck;
import com.jorel.template_api.exceptions.DataBaseNotAvailableException;
import com.jorel.template_api.persistence.datasource.HikariDataSourceProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class HealthCheckService {

    private final HikariDataSourceProvider dataSourceProvider;

    public HealthCheck performHealthCheck() {
        if (!dataSourceProvider.isDbAvailable()) {
            log.warn("Base de datos no disponible, health check omitido");
            throw new DataBaseNotAvailableException("Base de datos no disponible");
        }

        HealthCheck healthCheck = new HealthCheck();
        healthCheck.setStatus("OK");
        healthCheck.setCreatedAt(LocalDateTime.now());

        log.info("Health check realizado exitosamente");
        return healthCheck;
    }
}
```

- [ ] **Step 3: Commit**

```bash
git add src/main/java/com/jorel/template_api/service/
git commit -m "feat: add PingService and HealthCheckService"
```

---

### Task 7: Persistence Layer (HikariDataSourceProvider and DAO)

**Files:**
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\persistence\datasource\HikariDataSourceProvider.java`
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\persistence\dao\HealthCheckDao.java`
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\persistence\vos\HealthCheckVO.java`

**Interfaces:**
- Consumes: HealthCheck entity, YAML configuration
- Produces: HikariDataSourceProvider, HealthCheckDao, HealthCheckVO

- [ ] **Step 1: Create HikariDataSourceProvider.java**

```java
package com.jorel.template_api.persistence.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@Slf4j
public class HikariDataSourceProvider {

    private HikariDataSource dataSource;
    private boolean dbAvailable;

    @Value("${spring.datasource.url:}")
    private String dbUrl;

    @Value("${spring.datasource.username:}")
    private String dbUsername;

    @Value("${spring.datasource.password:}")
    private String dbPassword;

    @PostConstruct
    public void init() {
        if (dbUrl == null || dbUrl.isEmpty()) {
            log.warn("⚠️ No se encontró configuración de base de datos. El proyecto levantará sin BD.");
            this.dataSource = null;
            this.dbAvailable = false;
            return;
        }

        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dbUrl);
            config.setUsername(dbUsername);
            config.setPassword(dbPassword);
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");

            this.dataSource = new HikariDataSource(config);
            try (Connection connection = dataSource.getConnection()) {
                log.info("✅ Conexión a base de datos establecida: {}", dbUrl);
            }
            this.dbAvailable = true;
        } catch (SQLException e) {
            log.warn("⚠️ No se pudo conectar a la base de datos: {}. El proyecto levantará sin BD.", e.getMessage());
            this.dataSource = null;
            this.dbAvailable = false;
        }
    }

    public HikariDataSource getDataSource() {
        return dataSource;
    }

    public boolean isDbAvailable() {
        return dbAvailable;
    }
}
```

- [ ] **Step 2: Create HealthCheckVO.java**

```java
package com.jorel.template_api.persistence.vos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthCheckVO {
    private Long id;
    private String status;
    private LocalDateTime createdAt;
}
```

- [ ] **Step 3: Create HealthCheckDao.java**

```java
package com.jorel.template_api.persistence.dao;

import com.jorel.template_api.persistence.vos.HealthCheckVO;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import java.util.List;
import java.util.Optional;

public interface HealthCheckDao {

    @SqlQuery("SELECT * FROM health_check")
    @RegisterBeanMapper(HealthCheckVO.class)
    List<HealthCheckVO> findAll();

    @SqlQuery("SELECT * FROM health_check WHERE id = :id")
    @RegisterBeanMapper(HealthCheckVO.class)
    Optional<HealthCheckVO> findById(@Bind("id") Long id);

    @SqlUpdate("INSERT INTO health_check (status) VALUES (:status)")
    void insert(@Bind("status") String status);
}
```

- [ ] **Step 4: Commit**

```bash
git add src/main/java/com/jorel/template_api/persistence/
git commit -m "feat: add persistence layer with HikariDataSourceProvider, HealthCheckDao, and HealthCheckVO"
```

---

### Task 8: Controller Layer (PingController)

**Files:**
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\controller\PingController.java`

**Interfaces:**
- Consumes: PingService
- Produces: PingController with /ping endpoint

- [ ] **Step 1: Create PingController.java**

```java
package com.jorel.template_api.controller;

import com.jorel.template_api.service.PingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

- [ ] **Step 2: Test endpoint manually**

Run: `mvn spring-boot:run`
Test: `curl http://localhost:8080/api/v1/ping`
Expected: Response "pong" with HTTP 200

- [ ] **Step 3: Commit**

```bash
git add src/main/java/com/jorel/template_api/controller/
git commit -m "feat: add PingController with /ping endpoint"
```

---

### Task 9: AOP Logging Aspect

**Files:**
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\aspect\LoggingAspect.java`

**Interfaces:**
- Consumes: All controller, service, and persistence classes
- Produces: LoggingAspect with AOP logging

- [ ] **Step 1: Create LoggingAspect.java**

```java
package com.jorel.template_api.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.jorel.template_api.controller..*(..))")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        log.info("=== Entrada: {} ===", methodName);
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long elapsed = System.currentTimeMillis() - start;
            log.info("=== Salida: {} | Tiempo: {}ms | Status: OK ===", methodName, elapsed);
            return result;
        } catch (Exception e) {
            long elapsed = System.currentTimeMillis() - start;
            log.error("=== Error: {} | Tiempo: {}ms | Exception: {} ===",
                methodName, elapsed, e.getClass().getSimpleName());
            throw e;
        }
    }

    @Around("execution(* com.jorel.template_api.service..*(..))")
    public Object logService(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        log.debug("Service: {} - Inicio", methodName);
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long elapsed = System.currentTimeMillis() - start;
            log.debug("Service: {} - Fin ({}ms)", methodName, elapsed);
            return result;
        } catch (Exception e) {
            long elapsed = System.currentTimeMillis() - start;
            log.error("Service: {} - Error ({}ms): {}", methodName, elapsed, e.getMessage());
            throw e;
        }
    }

    @Around("execution(* com.jorel.template_api.persistence..*(..))")
    public Object logPersistence(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        log.debug("DAO: {} - Inicio", methodName);
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long elapsed = System.currentTimeMillis() - start;
            log.debug("DAO: {} - Fin ({}ms)", methodName, elapsed);
            return result;
        } catch (Exception e) {
            long elapsed = System.currentTimeMillis() - start;
            log.error("DAO: {} - Error ({}ms): {}", methodName, elapsed, e.getMessage());
            throw e;
        }
    }
}
```

- [ ] **Step 2: Test logging**

Run: `mvn spring-boot:run`
Test: `curl http://localhost:8080/api/v1/ping`
Expected: Logs show entry/exit for controller and service layers

- [ ] **Step 3: Commit**

```bash
git add src/main/java/com/jorel/template_api/aspect/
git commit -m "feat: add LoggingAspect for AOP logging across all layers"
```

---

### Task 10: Database Scripts and Docker Compose

**Files:**
- Create: `C:\Users\jorel\Desktop\template-api\src\main\resources\schema.sql`
- Create: `C:\Users\jorel\Desktop\template-api\src\main\resources\data.sql`
- Create: `C:\Users\jorel\Desktop\template-api\docker-compose.yml`

**Interfaces:**
- Consumes: MySQL database requirements
- Produces: SQL scripts, Docker Compose configuration

- [ ] **Step 1: Create schema.sql**

```sql
CREATE TABLE IF NOT EXISTS health_check (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

- [ ] **Step 2: Create data.sql**

```sql
INSERT INTO health_check (status) VALUES ('OK') ON DUPLICATE KEY UPDATE status = 'OK';
```

- [ ] **Step 3: Create docker-compose.yml**

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

- [ ] **Step 4: Commit**

```bash
git add src/main/resources/schema.sql
git add src/main/resources/data.sql
git add docker-compose.yml
git commit -m "feat: add database scripts and Docker Compose for MySQL"
```

---

### Task 11: Unit Tests

**Files:**
- Create: `C:\Users\jorel\Desktop\template-api\src\test\java\com\jorel\template_api\TemplateApiApplicationTests.java`
- Create: `C:\Users\jorel\Desktop\template-api\src\test\java\com\jorel\template_api\service\PingServiceTest.java`
- Create: `C:\Users\jorel\Desktop\template-api\src\test\java\com\jorel\template_api\service\HealthCheckServiceTest.java`
- Create: `C:\Users\jorel\Desktop\template-api\src\test\java\com\jorel\template_api\controller\PingControllerTest.java`

**Interfaces:**
- Consumes: PingService, HealthCheckService, PingController
- Produces: Unit tests for all components

- [ ] **Step 1: Create TemplateApiApplicationTests.java**

```java
package com.jorel.template_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TemplateApiApplicationTests {

    @Test
    void contextLoads() {
    }
}
```

- [ ] **Step 2: Create PingServiceTest.java**

```java
package com.jorel.template_api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PingServiceTest {

    private PingService pingService;

    @BeforeEach
    void setUp() {
        pingService = new PingService();
    }

    @Test
    void ping_shouldReturnPong() {
        assertEquals("pong", pingService.ping());
    }
}
```

- [ ] **Step 3: Run unit tests**

Run: `mvn test`
Expected: All tests pass

- [ ] **Step 4: Commit**

```bash
git add src/test/java/com/jorel/template_api/
git commit -m "feat: add unit tests for PingService and application context"
```

---

### Task 12: Integration Tests (Controller)

**Files:**
- Create: `C:\Users\jorel\Desktop\template-api\src\test\java\com\jorel\template_api\controller\PingControllerTest.java`

**Interfaces:**
- Consumes: PingController, PingService
- Produces: Integration test for /ping endpoint

- [ ] **Step 1: Create PingControllerTest.java**

```java
package com.jorel.template_api.controller;

import com.jorel.template_api.service.PingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.bean.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PingController.class)
class PingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PingService pingService;

    @Test
    void ping_shouldReturnPong() throws Exception {
        when(pingService.ping()).thenReturn("pong");

        mockMvc.perform(get("/api/v1/ping"))
            .andExpect(status().isOk())
            .andExpect(content().string("pong"));
    }
}
```

- [ ] **Step 2: Run integration tests**

Run: `mvn test`
Expected: All tests pass

- [ ] **Step 3: Commit**

```bash
git add src/test/java/com/jorel/template_api/controller/
git commit -m "feat: add integration test for PingController with MockMvc"
```

---

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

---

### Task 14: Final Verification and Cleanup

**Files:**
- Modify: `C:\Users\jorel\Desktop\template-api\pom.xml` (verify dependencies)
- Modify: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\TemplateApiApplication.java` (verify imports)

**Interfaces:**
- Consumes: All previous tasks
- Produces: Verified, working project

- [ ] **Step 1: Clean and compile**

```bash
mvn clean compile
```
Expected: BUILD SUCCESS

- [ ] **Step 2: Run all tests**

```bash
mvn test
```
Expected: All tests pass

- [ ] **Step 3: Package the application**

```bash
mvn package
```
Expected: JAR file created in target/

- [ ] **Step 4: Verify application starts**

```bash
java -jar target/template-api-0.0.1-SNAPSHOT.jar
```
Expected: Application starts on port 8080

- [ ] **Step 5: Test endpoint**

```bash
curl http://localhost:8080/api/v1/ping
```
Expected: Response "pong"

- [ ] **Step 6: Final commit**

```bash
git add .
git commit -m "chore: final verification and project cleanup"
```

---

## Execution Handoff

Plan complete and saved to `docs/superpowers/plans/2026-07-23-spring-boot-template-plan.md`. Two execution options:

**1. Subagent-Driven (recommended)** - I dispatch a fresh subagent per task, review between tasks, fast iteration

**2. Inline Execution** - Execute tasks in this session using executing-plans, batch execution with checkpoints

Which approach?
