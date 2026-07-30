### Task 9: AOP Logging Aspect

**Files:**
- Create: `C:\Users\jorel\Desktop\template-api\src\main\java\com\jorel\template_api\aspect\LoggingAspect.java`

**Interfaces:**
- Consumes: All controller, service, and persistence classes
- Produces: LoggingAspect with AOP logging

- [ ] **Step 1: Create LoggingAspect.java**

```java
package com.jorel.template_api.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* com.jorel.template_api.controller..*(..))")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        log.info("=== Entrada: {} ===", methodName);
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long elapsed = System.currentTimeMillis() - start;
            log.info("=== Salida: {} | Tiempo: {}ms | Status: OK ===", methodName, elapsed);
            return result;
        } catch (Exception e) {
            long elapsed = System.currentTimeMillis() - start;
            log.error("=== Error: {} | Tiempo: {}ms | Exception: {} ===",
                methodName, elapsed, e.getClass().getSimpleName());
            throw e;
        }
    }

    @Around("execution(* com.jorel.template_api.service..*(..))")
    public Object logService(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        log.debug("Service: {} - Inicio", methodName);
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long elapsed = System.currentTimeMillis() - start;
            log.debug("Service: {} - Fin ({}ms)", methodName, elapsed);
            return result;
        } catch (Exception e) {
            long elapsed = System.currentTimeMillis() - start;
            log.error("Service: {} - Error ({}ms): {}", methodName, elapsed, e.getMessage());
            throw e;
        }
    }

    @Around("execution(* com.jorel.template_api.persistence..*(..))")
    public Object logPersistence(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        log.debug("DAO: {} - Inicio", methodName);
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long elapsed = System.currentTimeMillis() - start;
            log.debug("DAO: {} - Fin ({}ms)", methodName, elapsed);
            return result;
        } catch (Exception e) {
            long elapsed = System.currentTimeMillis() - start;
            log.error("DAO: {} - Error ({}ms): {}", methodName, elapsed, e.getMessage());
            throw e;
        }
    }
}
```

- [ ] **Step 2: Test logging**

Run: `mvn spring-boot:run`
Test: `curl http://localhost:8080/api/v1/ping`
Expected: Logs show entry/exit for controller and service layers

- [ ] **Step 3: Commit**

```bash
git add src/main/java/com/jorel/template_api/aspect/
git commit -m "feat: add LoggingAspect for AOP logging across all layers"
```
