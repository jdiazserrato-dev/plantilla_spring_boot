package com.jorel.template_api.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BadRequestExceptionTest {

    @Test
    void constructor_setsMessage() {
        BadRequestException exception = new BadRequestException("Solicitud inválida");
        assertEquals("Solicitud inválida", exception.getMessage());
    }

    @Test
    void constructor_isRuntimeException() {
        BadRequestException exception = new BadRequestException("error");
        assertInstanceOf(RuntimeException.class, exception);
    }
}
