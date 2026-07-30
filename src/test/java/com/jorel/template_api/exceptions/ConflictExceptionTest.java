package com.jorel.template_api.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConflictExceptionTest {

    @Test
    void constructor_setsMessage() {
        ConflictException exception = new ConflictException("Conflicto detectado");
        assertEquals("Conflicto detectado", exception.getMessage());
    }

    @Test
    void constructor_isRuntimeException() {
        ConflictException exception = new ConflictException("error");
        assertInstanceOf(RuntimeException.class, exception);
    }
}
