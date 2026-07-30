package com.jorel.template_api.exceptions;

import com.jorel.template_api.dtos.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void handleNotFound_returns404WithMessage() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Usuario no encontrado");

        ResponseEntity<ErrorResponse> response = handler.handleNotFound(ex);

        assertEquals(404, response.getStatusCode().value());
        assertEquals(404, response.getBody().getStatus());
        assertEquals("Usuario no encontrado", response.getBody().getMessage());
    }

    @Test
    void handleBadRequest_returns400WithMessage() {
        BadRequestException ex = new BadRequestException("Datos inválidos");

        ResponseEntity<ErrorResponse> response = handler.handleBadRequest(ex);

        assertEquals(400, response.getStatusCode().value());
        assertEquals(400, response.getBody().getStatus());
        assertEquals("Datos inválidos", response.getBody().getMessage());
    }

    @Test
    void handleConflict_returns409WithMessage() {
        ConflictException ex = new ConflictException("El recurso ya existe");

        ResponseEntity<ErrorResponse> response = handler.handleConflict(ex);

        assertEquals(409, response.getStatusCode().value());
        assertEquals(409, response.getBody().getStatus());
        assertEquals("El recurso ya existe", response.getBody().getMessage());
    }

    @Test
    void handleDBUnavailable_returns503WithMessage() {
        DataBaseNotAvailableException ex = new DataBaseNotAvailableException("BD caída");

        ResponseEntity<ErrorResponse> response = handler.handleDBUnavailable(ex);

        assertEquals(503, response.getStatusCode().value());
        assertEquals(503, response.getBody().getStatus());
        assertEquals("BD caída", response.getBody().getMessage());
    }

    @Test
    void handleGeneric_returns500WithDefaultMessage() {
        Exception ex = new RuntimeException("Error inesperado");

        ResponseEntity<ErrorResponse> response = handler.handleGeneric(ex);

        assertEquals(500, response.getStatusCode().value());
        assertEquals(500, response.getBody().getStatus());
        assertEquals("Error interno del servidor", response.getBody().getMessage());
    }
}
