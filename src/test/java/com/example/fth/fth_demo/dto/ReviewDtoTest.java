package com.example.fth.fth_demo.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import java.util.Set;
import com.example.fth.fth_demo.validation.ValidationGroups;

class ReviewDtoTest {
    @Test
    void validationFailsForInvalidFields() {
        ReviewDto dto = new ReviewDto();
        // id is null, content is null, rating is null
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        // Validate for Create group (id must be null, rating must not be null)
        Set<ConstraintViolation<ReviewDto>> violations = validator.validate(dto, ValidationGroups.Create.class);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("rating")));
        // Validate for Update group (id must not be null)
        dto.setRating(3);
        violations = validator.validate(dto, ValidationGroups.Update.class);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("id")));
    }
}
