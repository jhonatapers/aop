package com.jhonatapers.aop.context;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class SimplePostInvocationContextTest {

    @Mock
    PreInvocationContext preInvocationContext;

    @BeforeEach
    void setUp() {
        openMocks(this);
        reset(preInvocationContext);
    }

    @Test
    void givenSuccessfulPreInvocationContext_whenCreateIsCalled_shouldInstantiate() throws Throwable {

        final var expectedResult = "result";

        when(preInvocationContext.proceed())
                .thenReturn(expectedResult);

        final var postInvocationContext = SimplePostInvocationContext.from(preInvocationContext);

        assertNotNull(postInvocationContext.getProceededAt());
        assertNull(postInvocationContext.getThrowable());
        assertTrue(postInvocationContext.wasSuccessful());
        assertEquals(expectedResult, postInvocationContext.getResult());

        verify(preInvocationContext, times(1)).proceed();

    }

    @Test
    void givenAnUnsuccessfulPreInvocationContext_whenCreateIsCalled_shouldInstantiate() throws Throwable {

        final var expectedThrowable = new RuntimeException("error");

        when(preInvocationContext.proceed())
                .thenThrow(expectedThrowable);

        final var postInvocationContext = SimplePostInvocationContext.from(preInvocationContext);

        assertNotNull(postInvocationContext.getProceededAt());
        assertFalse(postInvocationContext.wasSuccessful());
        assertEquals(expectedThrowable, postInvocationContext.getThrowable());
        assertNull(postInvocationContext.getResult());

        verify(preInvocationContext, times(1)).proceed();

    }

    @Test
    void givenSuccessfulProceededPreInvocationContext_whenProceedIsCalled_shouldGivenAnResult() throws Throwable {

        final PreInvocationContext expectedPreInvocationContext = this.preInvocationContext;
        final Object expectedResult = "result";
        final Throwable expectedThrowable = null;
        final boolean expectedSuccessful = Boolean.TRUE;

        when(expectedPreInvocationContext.proceeded())
                .thenReturn(Boolean.TRUE);

        when(expectedPreInvocationContext.proceed())
                .thenReturn(expectedResult);

        final var postInvocationContext = SimplePostInvocationContext.from(expectedPreInvocationContext);

        final var actualResult = assertDoesNotThrow(() -> postInvocationContext.proceed());

        assertEquals(expectedResult, actualResult);
        assertEquals(expectedResult, postInvocationContext.getResult());
        assertEquals(expectedThrowable, postInvocationContext.getThrowable());
        assertEquals(expectedSuccessful, postInvocationContext.wasSuccessful());
        assertNotNull(postInvocationContext.getProceededAt());

        verify(expectedPreInvocationContext, times(1)).proceed();

    }

    @Test
    void givenAnUnsuccessfulProceededPreInvocationContext_whenProceedIsCalled_shouldThrow() throws Throwable {

        final PreInvocationContext expectedPreInvocationContext = this.preInvocationContext;
        final Object expectedResult = null;
        final String expectedThrowableMessage = "error";
        final Throwable expectedThrowable = new RuntimeException(expectedThrowableMessage);
        final boolean expectedSuccessful = Boolean.FALSE;

        when(expectedPreInvocationContext.proceeded())
                .thenReturn(Boolean.TRUE);

        when(expectedPreInvocationContext.proceed())
                .thenThrow(expectedThrowable);

        final var postInvocationContext = SimplePostInvocationContext.from(expectedPreInvocationContext);

        final var actualThrowable = assertThrows(RuntimeException.class, () -> postInvocationContext.proceed());

        assertEquals(expectedThrowableMessage, actualThrowable.getMessage());
        assertEquals(expectedResult, postInvocationContext.getResult());
        assertEquals(expectedThrowable, postInvocationContext.getThrowable());
        assertEquals(expectedSuccessful, postInvocationContext.wasSuccessful());
        assertNotNull(postInvocationContext.getProceededAt());

        verify(expectedPreInvocationContext, times(1)).proceed();

    }

    @Test
    void givenAnUnsuccessfulProceededPreInvocationContext_whenProceedWithArgsIsCalled_shouldThrow() throws Throwable {

        final PreInvocationContext expectedPreInvocationContext = this.preInvocationContext;
        final Object expectedResult = null;
        final String expectedThrowableMessage = "error";
        final Throwable expectedThrowable = new RuntimeException(expectedThrowableMessage);
        final boolean expectedSuccessful = Boolean.FALSE;

        when(expectedPreInvocationContext.proceeded())
                .thenReturn(Boolean.TRUE);

        when(expectedPreInvocationContext.proceed())
                .thenThrow(expectedThrowable);

        final var postInvocationContext = SimplePostInvocationContext.from(expectedPreInvocationContext);

        final var args = new Object[] { "arg1", 2, 3.0 };

        final var actualThrowable = assertThrows(RuntimeException.class, () -> postInvocationContext.proceed(args));

        assertEquals(expectedThrowableMessage, actualThrowable.getMessage());
        assertEquals(expectedResult, postInvocationContext.getResult());
        assertEquals(expectedThrowable, postInvocationContext.getThrowable());
        assertEquals(expectedSuccessful, postInvocationContext.wasSuccessful());
        assertNotNull(postInvocationContext.getProceededAt());

        verify(expectedPreInvocationContext, times(1)).proceed();

    }

    @Test
    void givenAnUnprocessedPreInvocationContext_whenProceedIsCalled_shouldProceedNormally() throws Throwable {

        final PreInvocationContext expectedPreInvocationContext = this.preInvocationContext;
        final Object expectedResult = "result";
        final Throwable expectedThrowable = null;
        final boolean expectedSuccessful = Boolean.TRUE;
        final var expectedProceeded = Boolean.FALSE;

        when(expectedPreInvocationContext.proceeded())
                .thenReturn(expectedProceeded);

        when(expectedPreInvocationContext.proceed())
                .thenReturn(expectedResult);

        final var postInvocationContext = SimplePostInvocationContext.from(expectedPreInvocationContext);

        final var actualResult = assertDoesNotThrow(() -> postInvocationContext.proceed());

        assertEquals(expectedResult, actualResult);
        assertEquals(expectedResult, postInvocationContext.getResult());
        assertEquals(expectedThrowable, postInvocationContext.getThrowable());
        assertEquals(expectedSuccessful, postInvocationContext.wasSuccessful());
        assertNotNull(postInvocationContext.getProceededAt());

        verify(expectedPreInvocationContext, times(2)).proceed();

    }

    @Test
    void givenAnUnprocessedPreInvocationContext_whenProceedWithArgsIsCalled_shouldProceedNormally() throws Throwable {

        final PreInvocationContext expectedPreInvocationContext = this.preInvocationContext;
        final Object expectedResult = "result";
        final Throwable expectedThrowable = null;
        final boolean expectedSuccessful = Boolean.TRUE;
        final var expectedProceeded = Boolean.FALSE;
        final var expectedArgs = new Object[] { "arg1", 2, 3.0 };

        when(expectedPreInvocationContext.proceeded())
                .thenReturn(expectedProceeded);

        when(expectedPreInvocationContext.proceed(expectedArgs))
                .thenReturn(expectedResult);

        final var postInvocationContext = SimplePostInvocationContext.from(expectedPreInvocationContext);

        final var actualResult = assertDoesNotThrow(() -> postInvocationContext.proceed(expectedArgs));

        assertEquals(expectedResult, actualResult);
        assertEquals(expectedThrowable, postInvocationContext.getThrowable());
        assertEquals(expectedSuccessful, postInvocationContext.wasSuccessful());
        assertNotNull(postInvocationContext.getProceededAt());

        verify(expectedPreInvocationContext, times(1)).proceed();

    }

    @Test
    void givenSuccessfulProceededPreInvocationContext_whenProceedWithArgsIsCalled_shouldGivenAnResult()
            throws Throwable {

        final PreInvocationContext expectedPreInvocationContext = this.preInvocationContext;
        final Object expectedResult = "result";
        final Throwable expectedThrowable = null;
        final boolean expectedSuccessful = Boolean.TRUE;
        final var expectedArgs = new Object[] { "arg1", 2, 3.0 };

        when(expectedPreInvocationContext.proceeded())
                .thenReturn(Boolean.TRUE);

        when(expectedPreInvocationContext.proceed())
                .thenReturn(expectedResult);

        final var postInvocationContext = SimplePostInvocationContext.from(expectedPreInvocationContext);

        final var actualResult = assertDoesNotThrow(() -> postInvocationContext.proceed(expectedArgs));

        assertEquals(expectedResult, actualResult);
        assertEquals(expectedResult, postInvocationContext.getResult());
        assertEquals(expectedThrowable, postInvocationContext.getThrowable());
        assertEquals(expectedSuccessful, postInvocationContext.wasSuccessful());
        assertNotNull(postInvocationContext.getProceededAt());

        verify(expectedPreInvocationContext, times(1)).proceed();

    }

}
