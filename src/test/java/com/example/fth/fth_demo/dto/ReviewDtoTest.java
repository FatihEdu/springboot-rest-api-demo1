package com.example.fth.fth_demo.dto;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ReviewDtoTest {
    @Test
    void dataAndSetters() {
        ReviewDto dto = new ReviewDto();
        dto.setId(1L);
        dto.setContent("abc");
        dto.setRating(5);
        LocalDateTime now = LocalDateTime.now();
        dto.setCreatedAt(now);
        dto.setModifiedAt(now);
        assertEquals(1L, dto.getId());
        assertEquals("abc", dto.getContent());
        assertEquals(5, dto.getRating());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getModifiedAt());
    }
}
