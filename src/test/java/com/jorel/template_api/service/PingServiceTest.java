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
