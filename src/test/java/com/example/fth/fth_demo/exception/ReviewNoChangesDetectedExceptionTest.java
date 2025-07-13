package com.example.fth.fth_demo.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReviewNoChangesDetectedExceptionTest {
    @Test
    void idConstructor() {
        ReviewNoChangesDetectedException ex = new ReviewNoChangesDetectedException(99L);
        assertTrue(ex.getMessage().contains("99"));
    }
    @Test
    void messageConstructor() {
        ReviewNoChangesDetectedException ex = new ReviewNoChangesDetectedException("custom");
        assertEquals("custom", ex.getMessage());
    }
}
