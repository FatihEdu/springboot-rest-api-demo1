package com.example.fth.fth_demo.exception;

import com.example.fth.fth_demo.dto.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {
    Environment environment;
    GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        environment = mock(Environment.class);
        handler = new GlobalExceptionHandler(environment);
    }

    @Test
    void handleReviewNotFound() {
        ReviewNotFoundException ex = new ReviewNotFoundException(1L);
        ResponseEntity<ErrorResponse> response = handler.handleReviewNotFound(ex);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("Review not found", body.getError());
    }

    @Test
    void handleNoChangesDetected() {
        ReviewNoChangesDetectedException ex = new ReviewNoChangesDetectedException(1L);
        ResponseEntity<ErrorResponse> response = handler.handleNoChangesDetected(ex);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("No changes were made", body.getError());
    }

    @Test
    void handleGenericException_devProfile() {
        when(environment.matchesProfiles("dev")).thenReturn(true);
        Exception ex = new Exception("dev error");
        ResponseEntity<ErrorResponse> response = handler.handleGenericException(ex);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("Internal Server Error", body.getError());
        assertEquals("dev error", body.getMessage());
    }

    @Test
    void handleGenericException_prodProfile() {
        when(environment.matchesProfiles("dev")).thenReturn(false);
        Exception ex = new Exception("prod error");
        ResponseEntity<ErrorResponse> response = handler.handleGenericException(ex);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ErrorResponse body = response.getBody();
        assertNotNull(body);
        assertEquals("Internal Server Error", body.getError());
        assertNull(body.getMessage());
    }
}
