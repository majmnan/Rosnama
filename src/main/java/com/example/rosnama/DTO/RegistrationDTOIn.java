package com.example.rosnama.DTO;

import com.example.rosnama.Model.InternalEvent;
import com.example.rosnama.Model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public class RegistrationDTOIn {
    @NotNull
    private Integer userId;

    @NotNull
    private Integer eventId;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @Pattern(regexp = "^(Regestered|Active|Used|Ended)$")
    private String status;
}
