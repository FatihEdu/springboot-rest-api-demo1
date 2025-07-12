package com.example.fth.fth_demo.mapper;

import java.util.List;
import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.fth.fth_demo.dto.ReviewDto;
import com.example.fth.fth_demo.entity.Review;

@Mapper
public interface ReviewServiceMapper {
    ReviewServiceMapper INSTANCE = Mappers.getMapper(ReviewServiceMapper.class);

    ReviewDto toDto(Review review);
    Review toEntity(ReviewDto dto);
    List<ReviewDto> toDtoList(List<Review> reviews);

    default Optional<ReviewDto> toDto(Optional<Review> review) {
        return review.map(this::toDto);
    };

    default Optional<Review> toEntity(Optional<ReviewDto> dto) {
        return dto.map(this::toEntity);
    };

    default List<Review> toEntityList(List<ReviewDto> dtos) {
        return dtos.stream().map(this::toEntity).toList();
    }

    default List<ReviewDto> toDtoList(Optional<List<Review>> reviews) {
        return reviews.map(this::toDtoList).orElse(List.of());
    }

        
}
