# Task 4: Exception Handling — Report

## What I Implemented

Created 4 custom exception classes and 1 GlobalExceptionHandler, all in `com.jorel.template_api.exceptions`:

1. **ResourceNotFoundException** — 404 (extends `RuntimeException`)
2. **BadRequestException** — 400 (extends `RuntimeException`)
3. **ConflictException** — 409 (extends `RuntimeException`)
4. **DataBaseNotAvailableException** — 503 (extends `RuntimeException`)
5. **GlobalExceptionHandler** — `@RestControllerAdvice` with handlers for each custom exception plus a generic `Exception` handler returning 500

All exceptions use `ErrorResponse` DTO from the existing `dtos` package.

## What I Tested

- Compilation verified: `mvn compile "-Djava.version=22"` — **SUCCESS** (environment has Java 22, not 25)
- No unit tests required by task brief
- Note: full test suite cannot run in current environment (requires Java 25 + Docker for Testcontainers)

## Files Changed

| File | Action |
|------|--------|
| `src/main/java/com/jorel/template_api/exceptions/ResourceNotFoundException.java` | Created |
| `src/main/java/com/jorel/template_api/exceptions/BadRequestException.java` | Created |
| `src/main/java/com/jorel/template_api/exceptions/ConflictException.java` | Created |
| `src/main/java/com/jorel/template_api/exceptions/DataBaseNotAvailableException.java` | Created |
| `src/main/java/com/jorel/template_api/exceptions/GlobalExceptionHandler.java` | Created |

## Self-Review

**Completeness:** All 5 files created exactly as specified in the brief. All steps checked off.

**Quality:** Code is clean, follows the brief exactly, uses Lombok `@Slf4j` for logging, and `ErrorResponse` DTO for consistent error responses.

**Discipline:** No overbuilding — implemented exactly what was specified, nothing more.

**Testing:** Compilation passes. No unit tests were required by the task brief.

## Commit

`18733ba` — feat: add custom exceptions and GlobalExceptionHandler
