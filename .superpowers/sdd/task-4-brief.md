### Task 4: Exception Handling

**Files:**
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\exceptions\ResourceNotFoundException.java`
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\exceptions\BadRequestException.java`
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\exceptions\ConflictException.java`
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\exceptions\DataBaseNotAvailableException.java`
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\exceptions\GlobalExceptionHandler.java`

**Interfaces:**
- Consumes: ErrorResponse DTO
- Produces: Custom exceptions, GlobalExceptionHandler

- [ ] **Step 1: Create ResourceNotFoundException.java**

```java
package com.jorel.template_api.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
```

- [ ] **Step 2: Create BadRequestException.java**

```java
package com.jorel.template_api.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
```

- [ ] **Step 3: Create ConflictException.java**

```java
package com.jorel.template_api.exceptions;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
```

- [ ] **Step 4: Create DataBaseNotAvailableException.java**

```java
package com.jorel.template_api.exceptions;

public class DataBaseNotAvailableException extends RuntimeException {
    public DataBaseNotAvailableException(String message) {
        super(message);
    }
}
```

- [ ] **Step 5: Create GlobalExceptionHandler.java**

```java
package com.jorel.template_api.exceptions;

import com.jorel.template_api.dtos.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex) {
        log.warn("Recurso no encontrado: {}", ex.getMessage());
        return ResponseEntity.status(404).body(new ErrorResponse(404, ex.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex) {
        log.warn("Solicitud inválida: {}", ex.getMessage());
        return ResponseEntity.status(400).body(new ErrorResponse(400, ex.getMessage()));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ConflictException ex) {
        log.warn("Conflicto: {}", ex.getMessage());
        return ResponseEntity.status(409).body(new ErrorResponse(409, ex.getMessage()));
    }

    @ExceptionHandler(DataBaseNotAvailableException.class)
    public ResponseEntity<ErrorResponse> handleDBUnavailable(DataBaseNotAvailableException ex) {
        log.error("Base de datos no disponible: {}", ex.getMessage());
        return ResponseEntity.status(503).body(new ErrorResponse(503, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        log.error("Error inesperado: {}", ex.getMessage(), ex);
        return ResponseEntity.status(500).body(new ErrorResponse(500, "Error interno del servidor"));
    }
}
```

- [ ] **Step 6: Commit**

```bash
git add src/main/java/com/jorel/template_api/exceptions/
git commit -m "feat: add custom exceptions and GlobalExceptionHandler"
```
