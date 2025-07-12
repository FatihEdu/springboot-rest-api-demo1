package com.example.fth.fth_demo.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {
    @Test
    void allArgsConstructorAndGetters() {
        ErrorResponse error = new ErrorResponse("err", "msg", 400, 10);
        assertEquals("err", error.getError());
        assertEquals("msg", error.getMessage());
        assertEquals(400, error.getStatus());
        assertEquals(10, error.getRetryAfter());
    }

    @Test
    void noArgsConstructorAndSetters() {
        ErrorResponse error = new ErrorResponse();
        error.setError("err");
        error.setMessage("msg");
        error.setStatus(404);
        error.setRetryAfter(5);
        assertEquals("err", error.getError());
        assertEquals("msg", error.getMessage());
        assertEquals(404, error.getStatus());
        assertEquals(5, error.getRetryAfter());
    }
}
