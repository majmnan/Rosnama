package com.example.rosnama.DTO;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewDTO {


    @NotNull(message = "Rating cannot be null")
    @Min(value = 1, message = "Rating must be at least 0 ")
    @Max(value = 5, message = "Rating cannot  be greater than 5")
    private Integer rating;

    @NotEmpty(message = "Review description cannot be empty")
    @Size(min = 10, message = "Review must be at least 10 characters")
    private String description;

    @NotNull(message = "Registration ID cannot be null")
    private Integer registrationId;


}
