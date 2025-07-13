package com.example.fth.fth_demo.exception;

public class ReviewNotFoundException extends RuntimeException {
    public ReviewNotFoundException(String message) {
        super(message);
    }
    
    public ReviewNotFoundException(Long id) {
        super("Review not found with id: " + id);
    }
    
}
