package com.example.rosnama.DTO;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecommendationDTO {

    @NotNull
    private Integer eventId;

    @NotEmpty
    private String title;

    @NotEmpty
    private String aiReason;

}
