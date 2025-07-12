package com.example.fth.fth_demo.exception;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.http.converter.HttpMessageNotReadableException;

//import com.example.fth.fth_demo.config.AppProperties;

//import lombok.RequiredArgsConstructor;

import com.example.fth.fth_demo.dto.ErrorResponse;

@ControllerAdvice
//@RequiredArgsConstructor
public class GlobalExceptionHandler {

    //private final AppProperties appProperties;
    private final Environment environment;

    // @RequiredArgsConstructor doesn't want to inject Environment,
    // so we do it manually.
    public GlobalExceptionHandler(Environment environment) {
        this.environment = environment;
    }

    // Handle ReviewNotFoundException
    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleReviewNotFound(ReviewNotFoundException ex) {
        //return ResponseEntity.status(HttpStatus.NOT_FOUND)
        //        .body(ex.getMessage());
        ErrorResponse errorResponse = new ErrorResponse("Review not found", ex.getMessage(),
        HttpStatus.NOT_FOUND.value(), null); 
        
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(errorResponse);

    }

    // Handle NoChangesDetectedException
    @ExceptionHandler(ReviewNoChangesDetectedException.class)
    public ResponseEntity<ErrorResponse> handleNoChangesDetected(ReviewNoChangesDetectedException ex) {
        ErrorResponse errorResponse = new ErrorResponse("No changes were made", ex.getMessage(),
        HttpStatus.BAD_REQUEST.value(), null); 

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(errorResponse);
        
        //return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        //        .body(ex.getMessage());
    }

    // Handle NoResourceFoundException (for unmapped static resources)
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFound(NoResourceFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            "Resource not found",
            null, //"No static resource found.",
            HttpStatus.NOT_FOUND.value(),
            null
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    // Handle HttpRequestMethodNotSupportedException (405)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            "Method not allowed",
            ex.getMessage(),
            HttpStatus.METHOD_NOT_ALLOWED.value(),
            null
        );
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }

    // Handle HttpMessageNotReadableException (400)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(HttpMessageNotReadableException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
            "Bad request",
            ex.getMessage(),
            HttpStatus.BAD_REQUEST.value(),
            null
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // Handle generic exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {

        BodyBuilder status = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
        //if (appProperties.debug()) {
        if (environment.matchesProfiles("dev")) {
            //return status.body(ex.getMessage() + "\n" + ex.getStackTrace());
            //return status.body(ex.getMessage());
            return status.body(new ErrorResponse("Internal Server Error", ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(), null));

        } else {
            // In production mode, do not expose stack trace / message
            return status.body(new ErrorResponse("Internal Server Error", null,
            HttpStatus.INTERNAL_SERVER_ERROR.value(), null));}        
    }
}
