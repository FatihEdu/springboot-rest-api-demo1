package com.example.fth.fth_demo.service;

import com.example.fth.fth_demo.dto.ReviewDto;
import java.util.List;
import java.util.Optional;

public interface IReviewService {
    List<ReviewDto> findAll();
    List<ReviewDto> findAllWithFilters(Boolean requireContent, Integer minRating);
    Optional<ReviewDto> findById(Long id);
    ReviewDto save(ReviewDto reviewDto);
    ReviewDto update(Long id, ReviewDto reviewDto);
    void delete(Long id);
}
