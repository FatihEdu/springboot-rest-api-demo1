package com.example.fth.fth_demo.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReviewNotFoundExceptionTest {
    @Test
    void messageConstructor() {
        ReviewNotFoundException ex = new ReviewNotFoundException("msg");
        assertEquals("msg", ex.getMessage());
    }
    @Test
    void idConstructor() {
        ReviewNotFoundException ex = new ReviewNotFoundException(42L);
        assertTrue(ex.getMessage().contains("42"));
    }
}
