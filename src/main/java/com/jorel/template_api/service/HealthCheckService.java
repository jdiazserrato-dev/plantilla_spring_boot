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
