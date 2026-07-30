### Task 5: Utility Classes (ScopeUtils and AppConfig)

**Files:**
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\util\ScopeUtils.java`
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\config\AppConfig.java`

**Interfaces:**
- Consumes: Environment enum
- Produces: ScopeUtils, AppConfig

- [ ] **Step 1: Create ScopeUtils.java**

```java
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
```

- [ ] **Step 2: Create AppConfig.java**

```java
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
```

- [ ] **Step 3: Commit**

```bash
git add src/main/java/com/jorel/template_api/util/
git add src/main/java/com/jorel/template_api/config/
git commit -m "feat: add ScopeUtils and AppConfig for environment management"
```
