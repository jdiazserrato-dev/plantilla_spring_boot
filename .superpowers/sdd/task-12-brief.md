### Task 12: Integration Tests (Controller)

**Files:**
- Create: `C:\Users\jorel\Desktop\template-api\src\test\java\com\jorel\template_api\controller\PingControllerTest.java`

**Interfaces:**
- Consumes: PingController, PingService
- Produces: Integration test for /ping endpoint

- [ ] **Step 1: Create PingControllerTest.java**

```java
package com.jorel.template_api.controller;

import com.jorel.template_api.service.PingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.bean.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PingController.class)
class PingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PingService pingService;

    @Test
    void ping_shouldReturnPong() throws Exception {
        when(pingService.ping()).thenReturn("pong");

        mockMvc.perform(get("/api/v1/ping"))
            .andExpect(status().isOk())
            .andExpect(content().string("pong"));
    }
}
```

- [ ] **Step 2: Run integration tests**

Run: `mvn test`
Expected: All tests pass

- [ ] **Step 3: Commit**

```bash
git add src/test/java/com/jorel/template_api/controller/
git commit -m "feat: add integration test for PingController with MockMvc"
```
