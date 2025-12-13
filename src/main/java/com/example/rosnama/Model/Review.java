package com.example.rosnama.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    @Column(columnDefinition = "integer not null")
    private Integer rating;

    @Size(min = 10, message = "Review must be at least 10 characters")
    @Column(columnDefinition = "text not null")
    private String description;

    @NotNull
    @OneToOne
    @JsonIgnore
    @MapsId
    private Registration registration;

}
