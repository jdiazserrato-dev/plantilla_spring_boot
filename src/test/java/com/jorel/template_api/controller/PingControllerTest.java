package com.jorel.template_api.controller;

import com.jorel.template_api.service.PingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PingControllerTest {

    @Mock
    private PingService pingService;

    @InjectMocks
    private PingController pingController;

    @Test
    void ping_shouldReturnPongResponse() {
        when(pingService.ping()).thenReturn("pong");

        ResponseEntity<String> response = pingController.ping();

        assertEquals(200, response.getStatusCode().value());
        assertEquals("pong", response.getBody());
    }
}
