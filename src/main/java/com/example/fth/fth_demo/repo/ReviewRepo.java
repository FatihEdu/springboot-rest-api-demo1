package com.example.fth.fth_demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fth.fth_demo.models.Review;

public interface ReviewRepo extends JpaRepository<Review, Long> {}