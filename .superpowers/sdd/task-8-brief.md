### Task 8: Controller Layer (PingController)

**Files:**
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\controller\PingController.java`

**Interfaces:**
- Consumes: PingService
- Produces: PingController with /ping endpoint

- [ ] **Step 1: Create PingController.java**

```java
package com.jorel.template_api.controller;

import com.jorel.template_api.service.PingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PingController {

    private final PingService pingService;

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok(pingService.ping());
    }
}
```

- [ ] **Step 2: Test endpoint manually**

Run: `mvn spring-boot:run`
Test: `curl http://localhost:8080/api/v1/ping`
Expected: Response "pong" with HTTP 200

- [ ] **Step 3: Commit**

```bash
git add src/main/java/com/jorel/template_api/controller/
git commit -m "feat: add PingController with /ping endpoint"
```
