package com.jhonatapers.aop.context;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class SimplePreInvocationContextTest {

    @Mock
    ProceedingJoinPoint proceedingJoinPoint;

    @BeforeEach
    void setUp() {
        openMocks(this);
        reset(proceedingJoinPoint);
    }

    @Test
    void givenAnProceedingJoinPoint_whenOfIsCalled_shouldInstantiateNewSimplePreInvocationContext() {

        final var expectedJoinPoint = proceedingJoinPoint;

        final var actualPreInvocationContext = assertDoesNotThrow(
                () -> SimplePreInvocationContext.of(expectedJoinPoint));

        assertNotNull(actualPreInvocationContext);
        assertInstanceOf(SimplePreInvocationContext.class, actualPreInvocationContext);

    }

    @Test
    void givenUnproceededJointPoint_whenProceedIsCalled_shouldProceedTheJoinPoint() throws Throwable {

        final var expectedJoinPoint = proceedingJoinPoint;
        final var expectedResult = "result";

        when(expectedJoinPoint.proceed())
                .thenReturn(expectedResult);

        final var actualPreInvocationContext = assertDoesNotThrow(
                () -> SimplePreInvocationContext.of(expectedJoinPoint));

        assertNotNull(actualPreInvocationContext);
        assertInstanceOf(SimplePreInvocationContext.class, actualPreInvocationContext);
        assertFalse(actualPreInvocationContext.proceeded());

        final var actualResult = assertDoesNotThrow(() -> actualPreInvocationContext.proceed());

        assertTrue(actualPreInvocationContext.proceeded());
        assertNotNull(actualResult);
        assertInstanceOf(String.class, actualResult);
        assertEquals(expectedResult, actualResult);

        verify(expectedJoinPoint, only()).proceed();
        verify(expectedJoinPoint, never()).proceed(any(Object[].class));

    }

    @Test
    void givenUnproceededJointPoint_whenProceedWithArgsIsCalled_shouldProceedTheJoinPoint() throws Throwable {

        final var expectedJoinPoint = proceedingJoinPoint;
        final var expectedResult = "result";
        final var expectedArgs = new Object[] { "arg1", 2, 3.0 };

        when(expectedJoinPoint.proceed(expectedArgs))
                .thenReturn(expectedResult);

        when(expectedJoinPoint.proceed())
                .thenReturn(expectedResult);

        final var actualPreInvocationContext = assertDoesNotThrow(
                () -> SimplePreInvocationContext.of(expectedJoinPoint));

        assertNotNull(actualPreInvocationContext);
        assertInstanceOf(SimplePreInvocationContext.class, actualPreInvocationContext);
        assertFalse(actualPreInvocationContext.proceeded());

        final var actualResult = assertDoesNotThrow(() -> actualPreInvocationContext.proceed(expectedArgs));

        assertTrue(actualPreInvocationContext.proceeded());
        assertNotNull(actualResult);
        assertInstanceOf(String.class, actualResult);
        assertEquals(expectedResult, actualResult);

        verify(expectedJoinPoint, only()).proceed(expectedArgs);
        verify(expectedJoinPoint, never()).proceed();

    }

}
