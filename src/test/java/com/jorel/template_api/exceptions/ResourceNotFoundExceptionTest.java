package com.jorel.template_api.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceNotFoundExceptionTest {

    @Test
    void constructor_setsMessage() {
        ResourceNotFoundException exception = new ResourceNotFoundException("Recurso no encontrado");
        assertEquals("Recurso no encontrado", exception.getMessage());
    }

    @Test
    void constructor_isRuntimeException() {
        ResourceNotFoundException exception = new ResourceNotFoundException("error");
        assertInstanceOf(RuntimeException.class, exception);
    }
}
