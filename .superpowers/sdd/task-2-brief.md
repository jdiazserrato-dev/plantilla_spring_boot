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
