package com.jorel.template_api.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataBaseNotAvailableExceptionTest {

    @Test
    void constructor_setsMessage() {
        DataBaseNotAvailableException exception = new DataBaseNotAvailableException("Base de datos no disponible");
        assertEquals("Base de datos no disponible", exception.getMessage());
    }

    @Test
    void constructor_isRuntimeException() {
        DataBaseNotAvailableException exception = new DataBaseNotAvailableException("error");
        assertInstanceOf(RuntimeException.class, exception);
    }
}
