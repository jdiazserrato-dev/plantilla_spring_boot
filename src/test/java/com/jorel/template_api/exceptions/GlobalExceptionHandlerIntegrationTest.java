package com.jorel.template_api.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Import(GlobalExceptionHandlerIntegrationTest.TestController.class)
class GlobalExceptionHandlerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @RestController
    static class TestController {
        @GetMapping("/test/not-found")
        void throwNotFound() {
            throw new ResourceNotFoundException("Recurso no encontrado");
        }

        @GetMapping("/test/bad-request")
        void throwBadRequest() {
            throw new BadRequestException("Solicitud inválida");
        }

        @GetMapping("/test/conflict")
        void throwConflict() {
            throw new ConflictException("Conflicto detectado");
        }

        @GetMapping("/test/db-unavailable")
        void throwDBUnavailable() {
            throw new DataBaseNotAvailableException("Base de datos no disponible");
        }

        @GetMapping("/test/generic-error")
        void throwGeneric() {
            throw new RuntimeException("Error inesperado");
        }
    }

    @Test
    void notFound_returns404() throws Exception {
        mockMvc.perform(get("/test/not-found").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(404))
            .andExpect(jsonPath("$.message").value("Recurso no encontrado"));
    }

    @Test
    void badRequest_returns400() throws Exception {
        mockMvc.perform(get("/test/bad-request").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status").value(400))
            .andExpect(jsonPath("$.message").value("Solicitud inválida"));
    }

    @Test
    void conflict_returns409() throws Exception {
        mockMvc.perform(get("/test/conflict").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isConflict())
            .andExpect(jsonPath("$.status").value(409))
            .andExpect(jsonPath("$.message").value("Conflicto detectado"));
    }

    @Test
    void dbUnavailable_returns503() throws Exception {
        mockMvc.perform(get("/test/db-unavailable").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isServiceUnavailable())
            .andExpect(jsonPath("$.status").value(503))
            .andExpect(jsonPath("$.message").value("Base de datos no disponible"));
    }

    @Test
    void genericError_returns500() throws Exception {
        mockMvc.perform(get("/test/generic-error").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError())
            .andExpect(jsonPath("$.status").value(500))
            .andExpect(jsonPath("$.message").value("Error interno del servidor"));
    }
}
