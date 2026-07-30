package com.jorel.template_api.util;

import com.jorel.template_api.enums.Environment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ScopeUtils {

    @Value("${spring.profiles.active:local}")
    private String activeProfile;

    public boolean isLocal() {
        return Environment.LOCAL.getProfile().equals(activeProfile);
    }

    public boolean isBeta() {
        return Environment.BETA.getProfile().equals(activeProfile);
    }

    public boolean isProd() {
        return Environment.PROD.getProfile().equals(activeProfile);
    }

    public String getActiveProfile() {
        return activeProfile;
    }

    public void logProfileInfo() {
        log.info("========================================");
        log.info("  Perfil activo: {}", activeProfile.toUpperCase());
        log.info("  Entorno: {}", getEnvironmentDescription());
        log.info("========================================");
    }

    private String getEnvironmentDescription() {
        return switch (activeProfile) {
            case "local" -> Environment.LOCAL.getDescription();
            case "beta" -> Environment.BETA.getDescription();
            case "prod" -> Environment.PROD.getDescription();
            default -> "Desconocido";
        };
    }
}
