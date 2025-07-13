package com.example.fth.fth_demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fth.fth_demo.dto.ReviewDto;
import com.example.fth.fth_demo.exception.ReviewNotFoundException;
import com.example.fth.fth_demo.service.IReviewService;
import com.example.fth.fth_demo.validation.ValidationGroups;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    
    private final IReviewService reviewService;

    // Reviews
    @GetMapping({"", "/"})
    public ResponseEntity<List<ReviewDto>> getReviews(@RequestParam(required = false) Boolean requireContent,
                                                      @RequestParam(required = false) Integer minRating) {

        List<ReviewDto> reviews = reviewService.findAllWithFilters(requireContent, minRating);
        
        return ResponseEntity.ok(reviews); // 200 OK
    }

    @GetMapping({"/{id}", "{id}/"})
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable Long id) {
        Optional<ReviewDto> reviewDto = reviewService.findById(id);

        if (reviewDto.isEmpty()) {
            throw new ReviewNotFoundException(id); // 404 Not Found
        }
        ReviewDto review = reviewDto.get();
        ResponseEntity<ReviewDto> response = new ResponseEntity<>(review, HttpStatus.OK); // 200 OK
        

        return response;
    }

    @PutMapping({"/{id}", "{id}/"})
    public ResponseEntity<ReviewDto> updateReview(@PathVariable Long id,
                                    @Validated(ValidationGroups.Update.class)
                                    @RequestBody ReviewDto updateData) {

        ReviewDto review = reviewService.update(id, updateData);                                            
        return ResponseEntity.ok().body(review);

    }

    @PostMapping({"/",""})
    public ResponseEntity<ReviewDto> createReview(
                                @Validated(ValidationGroups.Create.class)
                                @RequestBody ReviewDto review) {

        ReviewDto savedReview = reviewService.save(review);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview); // 201 Created

        }

    @DeleteMapping({"/{id}", "{id}/"})
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }   
    
}
