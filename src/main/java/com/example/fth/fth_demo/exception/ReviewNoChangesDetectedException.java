package com.example.fth.fth_demo.exception;

public class ReviewNoChangesDetectedException extends RuntimeException {
    public ReviewNoChangesDetectedException(Long id) {
        super("No changes detected in the update request for review with id: " + id);
    }

    public ReviewNoChangesDetectedException(String message) {
        super(message);
    }
}
