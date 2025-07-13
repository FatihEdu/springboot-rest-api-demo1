package com.example.fth.fth_demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String error;
    private String message;
    private Integer status;
    private Integer retryAfter;
}
