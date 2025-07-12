package com.example.fth.fth_demo.service;

import com.example.fth.fth_demo.dto.ReviewDto;
import com.example.fth.fth_demo.entity.Review;
import com.example.fth.fth_demo.exception.ReviewNoChangesDetectedException;
import com.example.fth.fth_demo.exception.ReviewNotFoundException;
import com.example.fth.fth_demo.mapper.ReviewServiceMapper;
import com.example.fth.fth_demo.repo.ReviewRepo;
import com.example.fth.fth_demo.service.impl.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.eq; // Removed unused import
import static org.mockito.Mockito.*;

class ReviewServiceTest {
    @Mock
    ReviewRepo reviewRepo;
    @Mock
    ReviewServiceMapper mapper;
    @InjectMocks
    ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_returnsDtos() {
        Review review = new Review();
        when(reviewRepo.findAll()).thenReturn(List.of(review));
        when(mapper.toDtoList(anyList())).thenReturn(List.of(new ReviewDto()));
        List<ReviewDto> result = reviewService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void findAllWithFilters_filtersCorrectly() {
        Review review = new Review();
        review.setContent("abc");
        review.setRating(5);
        when(reviewRepo.findAll()).thenReturn(List.of(review));
        when(mapper.toDtoList(anyList())).thenReturn(List.of(new ReviewDto()));
        List<ReviewDto> result = reviewService.findAllWithFilters(true, 5);
        assertEquals(1, result.size());
    }

    @Test
    void findById_found() {
        Review review = new Review();
        when(reviewRepo.findById(1L)).thenReturn(Optional.of(review));
        // Use ArgumentMatchers.<Optional<Review>>any() for type safety and avoid ambiguity
        when(mapper.toDto(org.mockito.ArgumentMatchers.<Optional<Review>>any())).thenReturn(Optional.of(new ReviewDto()));
        Optional<ReviewDto> result = reviewService.findById(1L);
        assertTrue(result.isPresent());
    }

    @Test
    void findById_notFound() {
        when(reviewRepo.findById(1L)).thenReturn(Optional.empty());
        Optional<ReviewDto> result = reviewService.findById(1L);
        assertTrue(result.isEmpty());
    }

    @Test
    void save_savesAndReturnsDto() {
        ReviewDto dto = new ReviewDto();
        Review review = new Review();
        when(mapper.toEntity(dto)).thenReturn(review);
        when(reviewRepo.save(review)).thenReturn(review);
        when(mapper.toDto(review)).thenReturn(dto);
        ReviewDto result = reviewService.save(dto);
        assertEquals(dto, result);
    }

    @Test
    void update_success() {
        ReviewDto dto = new ReviewDto();
        dto.setContent("new");
        dto.setRating(5);
        Review review = new Review();
        review.setContent("old");
        review.setRating(4);
        when(reviewRepo.findById(1L)).thenReturn(Optional.of(review));
        when(reviewRepo.save(any())).thenReturn(review);
        when(mapper.toDto(any(Review.class))).thenReturn(dto);
        ReviewDto result = reviewService.update(1L, dto);
        assertEquals(dto, result);
    }

    @Test
    void update_noChangesDetected() {
        ReviewDto dto = new ReviewDto();
        dto.setContent("same");
        dto.setRating(5);
        Review review = new Review();
        review.setContent("same");
        review.setRating(5);
        when(reviewRepo.findById(1L)).thenReturn(Optional.of(review));
        assertThrows(ReviewNoChangesDetectedException.class, () -> reviewService.update(1L, dto));
    }

    @Test
    void update_notFound() {
        ReviewDto dto = new ReviewDto();
        when(reviewRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ReviewNotFoundException.class, () -> reviewService.update(1L, dto));
    }

    @Test
    void delete_success() {
        Review review = new Review();
        when(reviewRepo.findById(1L)).thenReturn(Optional.of(review));
        doNothing().when(reviewRepo).delete(review);
        assertDoesNotThrow(() -> reviewService.delete(1L));
    }

    @Test
    void delete_notFound() {
        when(reviewRepo.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ReviewNotFoundException.class, () -> reviewService.delete(1L));
    }
}
