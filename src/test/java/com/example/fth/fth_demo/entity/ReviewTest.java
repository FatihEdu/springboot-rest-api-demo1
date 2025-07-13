package com.example.fth.fth_demo.entity;

import org.junit.jupiter.api.Test;
//import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReviewTest {
    @Test
    void gettersAndSetters() {
        Review review = new Review();
        review.setId(1L);
        review.setContent("test");
        review.setRating(4);
        // LocalDateTime now = LocalDateTime.now(); // Removed unused variable
        // set via reflection or constructor if needed, here just test getters
        assertEquals(1L, review.getId());
        assertEquals("test", review.getContent());
        assertEquals(4, review.getRating());
        // createdAt and modifiedAt are null by default
        assertNull(review.getCreatedAt());
        assertNull(review.getModifiedAt());
    }
}
