package com.example.fth.fth_demo.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class ReviewControllerErrorCasesTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /api/reviews/{id} with non-existent id returns 404")
    void getReviewById_notFound() throws Exception {
        mockMvc.perform(get("/api/reviews/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("/api/reviews/ with invalid method returns 405")
    void postToGetEndpoint_returns405() throws Exception {
        mockMvc.perform(patch("/api/reviews/"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("GET /reviews/{id} (no /api) returns 404 for static resource")
    void getNonApiReviews_returns404() throws Exception {
        mockMvc.perform(get("/reviews/123456"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Resource not found"));
    }

    @Test
    @DisplayName("POST /api/reviews with invalid payload returns 400")
    void postInvalidReview_returns400() throws Exception {
        String invalidJson = "{"; // Malformed JSON
        mockMvc.perform(post("/api/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidJson))
                .andExpect(status().isBadRequest());
    }
}
