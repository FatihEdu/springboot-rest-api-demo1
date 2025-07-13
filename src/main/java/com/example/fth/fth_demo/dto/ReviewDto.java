package com.example.fth.fth_demo.dto;

import java.time.LocalDateTime;

import com.example.fth.fth_demo.validation.ValidationGroups;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReviewDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Null(groups = ValidationGroups.Create.class)
    @NotNull(groups = ValidationGroups.Update.class)
    private Long id;
    
    @Size(max = 500, message = "Content exceeds 500 characters")
    private String content;
    
    @NotNull(groups = ValidationGroups.Create.class)
    @Min(value = 1, message = "Rating must be at least 1", groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    @Max(value = 5, message = "Rating must be at most 5", groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private Integer rating; // Can be null when updating the content only.
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Null(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private LocalDateTime createdAt;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Null(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class})
    private LocalDateTime modifiedAt;
}
