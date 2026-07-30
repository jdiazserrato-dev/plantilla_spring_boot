package com.jorel.template_api.persistence.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
            config.setInitializationFailTimeout(5000);

            this.dataSource = new HikariDataSource(config);
            log.info("✅ Conexión a base de datos establecida: {}", dbUrl);
            this.dbAvailable = true;
        } catch (Exception e) {
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
