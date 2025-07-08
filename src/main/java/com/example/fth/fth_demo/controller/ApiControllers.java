package com.example.fth.fth_demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fth.fth_demo.repo.ReviewRepo;

import com.example.fth.fth_demo.models.Review;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
public class ApiControllers {
    
    @Autowired
    private ReviewRepo reviewRepo;

    // Home
    @GetMapping("/")
    public String getHomePage() {
        return "Hello World?";
    }


    // Reviews
    @GetMapping("/reviews")
    public List<Review> getReviews(@RequestParam(defaultValue = "false") boolean requireContent,
                                   @RequestParam(defaultValue = "0") int minRating) {

        List<Review> reviews = reviewRepo.findAll();

        /*if (!requireContent && minRating == 0) {
            // If no filters are applied, return all reviews
            return reviews;
        }*/
        
        // Chain filters using streams
        if (requireContent) {
            reviews = reviews.stream()
                .filter(review -> review.getContent() != null && !review.getContent().isEmpty())
                .collect(Collectors.toList());
        }
        
        if (minRating != 0) {
            reviews = reviews.stream()
                .filter(review -> review.getRating() >= minRating)
                .collect(Collectors.toList());
        }

        return reviews;
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        return reviewRepo.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("reviews/{id}")
    public ResponseEntity<String> updateReview(@PathVariable Long id,
                                            @RequestBody Review updateData) {
        
        int rating = updateData.getRating();
        String content = updateData.getContent();

        if (rating == 0 && (content == null || content.isEmpty())) {
            return ResponseEntity.badRequest().build();
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
        

        if (rating != 0) {
            review.setRating(rating);
        }
        if (content != null && !content.isEmpty()) {
            review.setContent(content);
        }

        reviewRepo.save(review);
            
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/reviews")
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        // Validate rating
        if (review.getRating() == 0) {
            return ResponseEntity.badRequest().build();
        }   
        else if (review.getRating() < 1 || review.getRating() > 5) {
            return ResponseEntity.badRequest().build();
        }
        
        // Validate content length
        if (review.getContent() != null && review.getContent().length() > 500) {
            return ResponseEntity.badRequest().build();
        }

        Review savedReview = reviewRepo.save(review);

        return ResponseEntity.status(201).body(savedReview); // HTTP 201 Created
    }
    
   

   
    
}
