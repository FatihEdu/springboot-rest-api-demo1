package com.example.fth.fth_demo.controller;

import com.example.fth.fth_demo.dto.ReviewDto;
// import com.example.fth.fth_demo.exception.ReviewNoChangesDetectedException; // Removed unused import
import com.example.fth.fth_demo.exception.ReviewNotFoundException;
import com.example.fth.fth_demo.service.IReviewService;
// import org.junit.jupiter.api.DisplayName; // Removed unused import
// import org.junit.jupiter.api.Nested; // Removed unused import
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.validation.annotation.Validated; // Removed unused import

// import java.util.Arrays; // Removed unused import
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ReviewControllerTest {
    @Mock
    IReviewService reviewService;

    @InjectMocks
    ReviewController reviewController;

    public ReviewControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getReviews_returnsList() {
        ReviewDto dto = new ReviewDto();
        when(reviewService.findAllWithFilters(null, null)).thenReturn(List.of(dto));
        ResponseEntity<List<ReviewDto>> response = reviewController.getReviews(null, null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<ReviewDto> body = response.getBody();
        assertNotNull(body);
        assertEquals(1, body.size());
    }

    @Test
    void getReviews_emptyList() {
        when(reviewService.findAllWithFilters(null, null)).thenReturn(List.of());
        ResponseEntity<List<ReviewDto>> response = reviewController.getReviews(null, null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<ReviewDto> body = response.getBody();
        assertNotNull(body);
        assertTrue(body.isEmpty());
    }

    @Test
    void getReviewById_found() {
        ReviewDto dto = new ReviewDto();
        when(reviewService.findById(1L)).thenReturn(Optional.of(dto));
        ResponseEntity<ReviewDto> response = reviewController.getReviewById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(dto, response.getBody());
    }

    @Test
    void getReviewById_notFound() {
        when(reviewService.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ReviewNotFoundException.class, () -> reviewController.getReviewById(1L));
    }

    @Test
    void updateReview_success() {
        ReviewDto input = new ReviewDto();
        ReviewDto output = new ReviewDto();
        when(reviewService.update(eq(1L), any())).thenReturn(output);
        ResponseEntity<ReviewDto> response = reviewController.updateReview(1L, input);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(output, response.getBody());
    }
}
