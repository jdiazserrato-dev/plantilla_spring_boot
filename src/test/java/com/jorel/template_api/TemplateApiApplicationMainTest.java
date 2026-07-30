package com.jorel.template_api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TemplateApiApplicationMainTest {

    @Test
    void main_startsApplicationWithoutErrors() {
        assertDoesNotThrow(() -> TemplateApiApplication.main(
            new String[]{"--spring.main.web-application-type=none"}));
    }
}
