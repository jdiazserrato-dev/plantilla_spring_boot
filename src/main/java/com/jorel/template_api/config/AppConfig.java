package com.jorel.template_api.config;

import com.jorel.template_api.util.ScopeUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AppConfig {

    private final ScopeUtils scopeUtils;

    @PostConstruct
    public void init() {
        scopeUtils.logProfileInfo();
    }
}
