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
