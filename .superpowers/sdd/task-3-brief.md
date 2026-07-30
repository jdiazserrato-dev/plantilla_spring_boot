### Task 3: Domain Layer (HealthCheck Entity)

**Files:**
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\domain\HealthCheck.java`
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\dtos\ErrorResponse.java`
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\enums\Environment.java`

**Interfaces:**
- Consumes: None
- Produces: HealthCheck entity, ErrorResponse DTO, Environment enum

- [ ] **Step 1: Create HealthCheck.java**

```java
package com.jorel.template_api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HealthCheck {
    private Long id;
    private String status;
    private LocalDateTime createdAt;
}
```

- [ ] **Step 2: Create ErrorResponse.java**

```java
package com.jorel.template_api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;
}
```

- [ ] **Step 3: Create Environment.java**

```java
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
```

- [ ] **Step 4: Commit**

```bash
git add src/main/java/com/jorel/template_api/domain/
git add src/main/java/com/jorel/template_api/dtos/
git add src/main/java/com/jorel/template_api/enums/
git commit -m "feat: add domain layer with HealthCheck entity, ErrorResponse DTO, and Environment enum"
```
