package com.jorel.template_api.service;

import com.jorel.template_api.domain.HealthCheck;
import com.jorel.template_api.exceptions.DataBaseNotAvailableException;
import com.jorel.template_api.persistence.datasource.HikariDataSourceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HealthCheckServiceTest {

    @Mock
    private HikariDataSourceProvider dataSourceProvider;

    @InjectMocks
    private HealthCheckService healthCheckService;

    @Test
    void performHealthCheck_whenDbAvailable_returnsOkStatus() {
        when(dataSourceProvider.isDbAvailable()).thenReturn(true);

        HealthCheck result = healthCheckService.performHealthCheck();

        assertNotNull(result);
        assertEquals("OK", result.getStatus());
        assertNotNull(result.getCreatedAt());
    }

    @Test
    void performHealthCheck_whenDbUnavailable_throwsException() {
        when(dataSourceProvider.isDbAvailable()).thenReturn(false);

        assertThrows(DataBaseNotAvailableException.class, () -> healthCheckService.performHealthCheck());
    }
}
