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
//import com.example.fth.fth_demo.entity.Review;
import com.example.fth.fth_demo.exception.ReviewNotFoundException;
import com.example.fth.fth_demo.service.IReviewService;
import com.example.fth.fth_demo.validation.ValidationGroups;

import jakarta.validation.Valid;
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
        //HttpStatus status = reviews.isEmpty() ? HttpStatus.NOT_FOUND : HttpStatus.OK;
        /*HttpStatus status = HttpStatus.OK; // Always return 200 OK
        ResponseEntity<List<ReviewDto>> response = new ResponseEntity<>(reviews, status);*/
        
        return ResponseEntity.ok(reviews); // 200 OK
    }

    @GetMapping({"/{id}", "{id}/"})
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable Long id) {
        Optional<ReviewDto> reviewDto = reviewService.findById(id);

        //HttpStatus status = reviewDto.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND;
        //ResponseEntity<ReviewDto> response = new ResponseEntity<>(reviewDto.orElse(null), status);

        // Raises: java.util.NoSuchElementException: No value present
        //ResponseEntity<ReviewDto> response = new ResponseEntity<>(reviewDto.get(), status);

        if (reviewDto.isEmpty()) {
            throw new ReviewNotFoundException(id); // 404 Not Found
        }
        ReviewDto review = reviewDto.get();
        ResponseEntity<ReviewDto> response = new ResponseEntity<>(review, HttpStatus.OK); // 200 OK
        

        return response;
    }

    @PutMapping({"/{id}", "{id}/"})
    @Validated(ValidationGroups.Update.class)
    public ResponseEntity<ReviewDto> updateReview(@PathVariable Long id,
                                @Valid @RequestBody ReviewDto updateData) {

        ReviewDto review = reviewService.update(id, updateData);                                            
        return ResponseEntity.ok().body(review);

        /*if (updateData == null) {
            return ResponseEntity.badRequest().body("Invalid request");
        }              
        
        int rating = updateData.getRating();
        String content = updateData.getContent();

        if (rating == 0 && (content == null || content.isEmpty())) {
            //return ResponseEntity.status(418).build();
            return ResponseEntity.badRequest().body("At least one field must be provided for update");
        }
        else if (rating != 0 && (rating < 1 || rating > 5)) {
            return ResponseEntity.badRequest().body("Rating must be between 1 and 5");
        }
        else if (content != null && content.length() > 500) {
            return ResponseEntity.badRequest().body("Content must be less than 500 characters");
        }

        Optional<Review> optionalReview = reviewRepo.findById(id);
         //.orElseThrow(() -> new IllegalArgumentException("Review not found with id: " + id));
        if (optionalReview.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Review review = optionalReview.get();

        // See if anything actually changed.
        boolean ratingChanged = rating != 0 && review.getRating() != rating;
        boolean contentChanged = content != null && !content.isEmpty() && !content.equals(review.getContent());

        if (!ratingChanged && !contentChanged) {
            return ResponseEntity.badRequest().body("Nothing changed"); // No changes made
        }
        

        if (rating != 0) {
            review.setRating(rating);
        }
        if (content != null && !content.isEmpty()) {
            review.setContent(content);
        }

        

        reviewRepo.save(review);
            
        return ResponseEntity.noContent().build();*/
    }

    @PostMapping({"/",""})
    @Validated(ValidationGroups.Create.class)
    public ResponseEntity<ReviewDto> createReview(@RequestBody @Valid ReviewDto review) {

        ReviewDto savedReview = reviewService.save(review);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview); // 201 Created

        /*if (review == null) {
            return ResponseEntity.badRequest().body("Invalid request");
        }

        // Validate rating
        if (review.getRating() == 0) {
            return ResponseEntity.badRequest().body("Rating is required");
        }   
        else if (review.getRating() < 1 || review.getRating() > 5) {
            return ResponseEntity.badRequest().body("Rating must be between 1 and 5");
        }
        
        // Validate content length
        if (review.getContent() != null && review.getContent().length() > 500) {
            return ResponseEntity.badRequest().body("Content must be less than 500 characters");
        }

        Review savedReview = reviewRepo.save(review);

        return ResponseEntity.status(201).body(savedReview); // HTTP 201 Created*/
    }

    @DeleteMapping({"/{id}", "{id}/"})
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }   
    
}
