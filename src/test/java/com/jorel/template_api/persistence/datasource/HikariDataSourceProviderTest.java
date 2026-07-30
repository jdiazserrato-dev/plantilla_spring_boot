package com.jorel.template_api.persistence.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HikariDataSourceProviderTest {

    @Nested
    @SpringBootTest(properties = {"spring.datasource.url="})
    class WithEmptyUrl {

        @Autowired
        private HikariDataSourceProvider provider;

        @Test
        void dataSourceIsNullWhenUrlEmpty() {
            assertNull(provider.getDataSource());
        }

        @Test
        void dbNotAvailableWhenUrlEmpty() {
            assertFalse(provider.isDbAvailable());
        }
    }

    @Nested
    @SpringBootTest(properties = {
        "spring.datasource.url=jdbc:mysql://invalid-host:3306/test",
        "spring.datasource.username=root",
        "spring.datasource.password=root"
    })
    class WithInvalidUrl {

        @Autowired
        private HikariDataSourceProvider provider;

        @Test
        void dataSourceIsNullWhenConnectionFails() {
            assertNull(provider.getDataSource());
        }

        @Test
        void dbNotAvailableWhenConnectionFails() {
            assertFalse(provider.isDbAvailable());
        }
    }

    @Test
    void init_whenNullUrl_skipsDatabaseConnection() {
        HikariDataSourceProvider provider = new HikariDataSourceProvider(null, "root", "root");
        provider.init();

        assertNull(provider.getDataSource());
        assertFalse(provider.isDbAvailable());
    }

    @Test
    void init_whenValidUrl_createsDataSource() {
        try (var mocked = mockConstruction(HikariDataSource.class)) {
            HikariDataSourceProvider provider = new HikariDataSourceProvider(
                "jdbc:mysql://localhost:3306/test", "root", "root");
            provider.init();

            HikariDataSource constructed = mocked.constructed().get(0);
            assertSame(constructed, provider.getDataSource());
            assertTrue(provider.isDbAvailable());
        }
    }
}
