package com.example.fth.fth_demo.mapper;

import com.example.fth.fth_demo.dto.ReviewDto;
import com.example.fth.fth_demo.entity.Review;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ReviewServiceMapperTest {
    private final ReviewServiceMapper mapper = ReviewServiceMapper.INSTANCE;

    @Test
    void toDto_and_toEntity() {
        Review review = new Review();
        review.setId(1L);
        review.setContent("test");
        review.setRating(5);
        ReviewDto dto = mapper.toDto(review);
        assertEquals(review.getId(), dto.getId());
        assertEquals(review.getContent(), dto.getContent());
        assertEquals(review.getRating(), dto.getRating());

        Review entity = mapper.toEntity(dto);
        assertEquals(dto.getId(), entity.getId());
        assertEquals(dto.getContent(), entity.getContent());
        assertEquals(dto.getRating(), entity.getRating());
    }

    @Test
    void toDtoList_and_toEntityList() {
        Review review = new Review();
        review.setId(2L);
        review.setContent("abc");
        review.setRating(3);
        List<ReviewDto> dtos = mapper.toDtoList(List.of(review));
        assertEquals(1, dtos.size());
        assertEquals(review.getId(), dtos.get(0).getId());

        List<Review> entities = mapper.toEntityList(List.of(dtos.get(0)));
        assertEquals(1, entities.size());
        assertEquals(dtos.get(0).getId(), entities.get(0).getId());
    }

    @Test
    void toDto_optional() {
        Review review = new Review();
        review.setId(3L);
        Optional<ReviewDto> dtoOpt = mapper.toDto(Optional.of(review));
        assertTrue(dtoOpt.isPresent());
        assertEquals(3L, dtoOpt.get().getId());
        assertTrue(mapper.toDto(Optional.empty()).isEmpty());
    }

    @Test
    void toEntity_optional() {
        ReviewDto dto = new ReviewDto();
        dto.setId(4L);
        Optional<Review> entityOpt = mapper.toEntity(Optional.of(dto));
        assertTrue(entityOpt.isPresent());
        assertEquals(4L, entityOpt.get().getId());
        assertTrue(mapper.toEntity(Optional.empty()).isEmpty());
    }

    @Test
    void toDtoList_optional() {
        Review review = new Review();
        review.setId(5L);
        List<ReviewDto> dtos = mapper.toDtoList(Optional.of(List.of(review)));
        assertEquals(1, dtos.size());
        assertEquals(5L, dtos.get(0).getId());
        assertTrue(mapper.toDtoList(Optional.empty()).isEmpty());
    }
}
