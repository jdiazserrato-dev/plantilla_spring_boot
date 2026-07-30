package com.jorel.template_api.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoggingAspectTest {

    private final LoggingAspect loggingAspect = new LoggingAspect();

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private Signature signature;

    private void stubSignature(String methodName) {
        lenient().when(joinPoint.getSignature()).thenReturn(signature);
        lenient().when(signature.toShortString()).thenReturn(methodName);
    }

    @Test
    void logController_whenSuccess_returnsResult() throws Throwable {
        stubSignature("PingController.ping()");
        when(joinPoint.proceed()).thenReturn("controller-result");

        Object result = loggingAspect.logController(joinPoint);

        assertEquals("controller-result", result);
        verify(joinPoint).proceed();
    }

    @Test
    void logController_whenException_throwsException() throws Throwable {
        stubSignature("PingController.ping()");
        when(joinPoint.proceed()).thenThrow(new RuntimeException("controller-error"));

        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> loggingAspect.logController(joinPoint));

        assertEquals("controller-error", exception.getMessage());
    }

    @Test
    void logService_whenSuccess_returnsResult() throws Throwable {
        stubSignature("HealthCheckService.performHealthCheck()");
        when(joinPoint.proceed()).thenReturn("service-result");

        Object result = loggingAspect.logService(joinPoint);

        assertEquals("service-result", result);
        verify(joinPoint).proceed();
    }

    @Test
    void logService_whenException_throwsException() throws Throwable {
        stubSignature("HealthCheckService.performHealthCheck()");
        when(joinPoint.proceed()).thenThrow(new RuntimeException("service-error"));

        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> loggingAspect.logService(joinPoint));

        assertEquals("service-error", exception.getMessage());
    }

    @Test
    void logPersistence_whenSuccess_returnsResult() throws Throwable {
        stubSignature("HealthCheckDao.findAll()");
        when(joinPoint.proceed()).thenReturn("persistence-result");

        Object result = loggingAspect.logPersistence(joinPoint);

        assertEquals("persistence-result", result);
        verify(joinPoint).proceed();
    }

    @Test
    void logPersistence_whenException_throwsException() throws Throwable {
        stubSignature("HealthCheckDao.findAll()");
        when(joinPoint.proceed()).thenThrow(new RuntimeException("persistence-error"));

        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> loggingAspect.logPersistence(joinPoint));

        assertEquals("persistence-error", exception.getMessage());
    }
}
