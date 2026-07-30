### Task 11: Unit Tests

**Files:**
- Create: `C:\Users\jorel\Desktop\template-api\src\test\java\com\jorel\template_api\TemplateApiApplicationTests.java`
- Create: `C:\Users\jorel\Desktop\template-api\src\test\java\com\jorel\template_api\service\PingServiceTest.java`
- Create: `C:\Users\jorel\Desktop\template-api\src\test\java\com\jorel\template_api\service\HealthCheckServiceTest.java`
- Create: `C:\Users\jorel\Desktop\template-api\src\test\java\com\jorel\template_api\controller\PingControllerTest.java`

**Interfaces:**
- Consumes: PingService, HealthCheckService, PingController
- Produces: Unit tests for all components

- [ ] **Step 1: Create TemplateApiApplicationTests.java**

```java
package com.jorel.template_api;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TemplateApiApplicationTests {

    @Test
    void contextLoads() {
    }
}
```

- [ ] **Step 2: Create PingServiceTest.java**

```java
package com.jorel.template_api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PingServiceTest {

    private PingService pingService;

    @BeforeEach
    void setUp() {
        pingService = new PingService();
    }

    @Test
    void ping_shouldReturnPong() {
        assertEquals("pong", pingService.ping());
    }
}
```

- [ ] **Step 3: Run unit tests**

Run: `mvn test`
Expected: All tests pass

- [ ] **Step 4: Commit**

```bash
git add src/test/java/com/jorel/template_api/
git commit -m "feat: add unit tests for PingService and application context"
```
