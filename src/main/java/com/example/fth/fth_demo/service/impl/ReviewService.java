package com.example.fth.fth_demo.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.fth.fth_demo.dto.ReviewDto;
import com.example.fth.fth_demo.entity.Review;
import com.example.fth.fth_demo.exception.ReviewNoChangesDetectedException;
import com.example.fth.fth_demo.exception.ReviewNotFoundException;
import com.example.fth.fth_demo.mapper.ReviewServiceMapper;
import com.example.fth.fth_demo.repo.ReviewRepo;
import com.example.fth.fth_demo.service.IReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService implements IReviewService {
        private final ReviewRepo reviewRepo;

        // Helper so private.
        private Review findEntityByIdOrThrowException(Long id) {
            return reviewRepo.findById(id)
                    .orElseThrow(() -> new ReviewNotFoundException(id));
        }

        @Override
        public List<ReviewDto> findAll() {
            List<Review> reviews = reviewRepo.findAll();
            return ReviewServiceMapper.INSTANCE.toDtoList(reviews);    
        }

        @Override
        public List<ReviewDto> findAllWithFilters(Boolean requireContent, Integer minRating) {
            List<Review> reviews = reviewRepo.findAll();
        
            // Chain filters using streams
            if (Boolean.TRUE.equals(requireContent)) {
                reviews = reviews.stream()
                    .filter(review -> review.getContent() != null && !review.getContent().isEmpty())
                    .collect(Collectors.toList());
            }
            
            if (minRating != null) {
                reviews = reviews.stream()
                    .filter(review -> review.getRating() >= minRating)
                    .collect(Collectors.toList());
            }

            return ReviewServiceMapper.INSTANCE.toDtoList(reviews);    
        }

        @Override
        public Optional<ReviewDto> findById(Long id) {
            Optional<Review> review = reviewRepo.findById(id);
            return ReviewServiceMapper.INSTANCE.toDto(review);
        }

        @Override
        public ReviewDto save(ReviewDto reviewDto) {
            Review review = ReviewServiceMapper.INSTANCE.toEntity(reviewDto);
            Review savedReview = reviewRepo.save(review);
            return ReviewServiceMapper.INSTANCE.toDto(savedReview);    
        }

        @Override
        public ReviewDto update(Long id, ReviewDto reviewDto) {
            /*Review existingReview = reviewRepo.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException(id));*/

            Review existingReview = findEntityByIdOrThrowException(id);
            
            if ( 
                ( (reviewDto.getContent() == null || reviewDto.getContent().isEmpty() ) 
                    && reviewDto.getRating() == null)
                || (existingReview.getContent().equals(reviewDto.getContent())
                    && existingReview.getRating().equals(reviewDto.getRating()))
                ) {
                throw new ReviewNoChangesDetectedException(id);
            }

            existingReview.setContent(
                Optional.ofNullable(reviewDto.getContent()).orElse(existingReview.getContent()));

            existingReview.setRating(
                Optional.ofNullable(reviewDto.getRating()).orElse(existingReview.getRating()));

            Review updatedReview = reviewRepo.save(existingReview);
            return ReviewServiceMapper.INSTANCE.toDto(updatedReview);
        }

        @Override
        public void delete(Long id) {
            Review existingReview = findEntityByIdOrThrowException(id);
            reviewRepo.delete(existingReview);
        }



    
}
