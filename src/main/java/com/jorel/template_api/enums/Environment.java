package com.jorel.template_api.enums;

public enum Environment {
    LOCAL("local", "Desarrollo Local"),
    BETA("beta", "Staging/Testing"),
    PROD("prod", "Producción");

    private final String profile;
    private final String description;

    Environment(String profile, String description) {
        this.profile = profile;
        this.description = description;
    }

    public String getProfile() {
        return profile;
    }

    public String getDescription() {
        return description;
    }
}
