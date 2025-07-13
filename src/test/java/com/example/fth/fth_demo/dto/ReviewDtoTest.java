package com.example.fth.fth_demo.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import java.util.Set;
import com.example.fth.fth_demo.validation.ValidationGroups;

class ReviewDtoTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void createGroup_invalidRating_null() {
        ReviewDto dto = ReviewDto.builder().build();
        Set<ConstraintViolation<ReviewDto>> violations = validator.validate(dto, ValidationGroups.Create.class);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("rating")), "Null rating should fail for Create group");
    }

    @Test
    void createGroup_invalidRating_tooLow() {
        ReviewDto dto = ReviewDto.builder().rating(0).build();
        Set<ConstraintViolation<ReviewDto>> violations = validator.validate(dto, ValidationGroups.Create.class);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("rating")), "Rating < 1 should fail for Create group");
    }

    @Test
    void createGroup_invalidRating_tooHigh() {
        ReviewDto dto = ReviewDto.builder().rating(6).build();
        Set<ConstraintViolation<ReviewDto>> violations = validator.validate(dto, ValidationGroups.Create.class);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("rating")), "Rating > 5 should fail for Create group");
    }

    @Test
    void createGroup_validRating() {
        ReviewDto dto = ReviewDto.builder().rating(3).build();
        Set<ConstraintViolation<ReviewDto>> violations = validator.validate(dto, ValidationGroups.Create.class);
        assertTrue(violations.isEmpty(), "Valid rating should pass for Create group");
    }

    @Test
    void updateGroup_invalidId() {
        ReviewDto dto = ReviewDto.builder().rating(3).build();
        Set<ConstraintViolation<ReviewDto>> violations = validator.validate(dto, ValidationGroups.Update.class);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("id")), "Null id should fail for Update group");
    }

    @Test
    void updateGroup_invalidRating_tooLow() {
        ReviewDto dto = ReviewDto.builder().id(1L).rating(0).build();
        Set<ConstraintViolation<ReviewDto>> violations = validator.validate(dto, ValidationGroups.Update.class);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("rating")), "Rating < 1 should fail for Update group");
    }

    @Test
    void updateGroup_invalidRating_tooHigh() {
        ReviewDto dto = ReviewDto.builder().id(1L).rating(6).build();
        Set<ConstraintViolation<ReviewDto>> violations = validator.validate(dto, ValidationGroups.Update.class);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("rating")), "Rating > 5 should fail for Update group");
    }

    @Test
    void updateGroup_valid() {
        ReviewDto dto = ReviewDto.builder().id(1L).rating(4).build();
        Set<ConstraintViolation<ReviewDto>> violations = validator.validate(dto, ValidationGroups.Update.class);
        assertTrue(violations.isEmpty(), "Valid id and rating should pass for Update group");
    }
}
