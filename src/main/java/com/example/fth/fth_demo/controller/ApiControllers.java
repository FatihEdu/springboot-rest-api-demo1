package com.example.fth.fth_demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fth.fth_demo.repo.ReviewRepo;
import com.example.fth.fth_demo.models.Review;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



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
    public List<Review> getReviews(@RequestParam(required = false) boolean requireContent,
                                   @RequestParam(required = false) int minRating) {

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

    @PostMapping("/reviews")
    public String postMethodName(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }
    

   
    
}
