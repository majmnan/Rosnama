package com.example.rosnama.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestBody;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventRequest {
    @Id
    @GeneratedValue
    private Integer id;

    @NotNull
    @OneToOne
    @MapsId
    private Event event; //parent class of External and internal Events

    @NotEmpty
    @Pattern(regexp = "^(Requested|Offered)$")
    private String status;

    @Positive
    @Column(columnDefinition = "double")
    private Double price;
}
