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

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExternalEventRequest {

    @Id
    @GeneratedValue
    private Integer id;

    @NotEmpty
    @Pattern(regexp = "^(Requested|Offered)$")
    private String status;

    @Positive
    @Column(columnDefinition = "double")
    private Double price;

    @NotNull
    @OneToOne
    @MapsId
    private ExternalEvent externalEvent;

}
