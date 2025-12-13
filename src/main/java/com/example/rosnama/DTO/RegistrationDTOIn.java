package com.example.rosnama.DTO;

import com.example.rosnama.Model.InternalEvent;
import com.example.rosnama.Model.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.optional.qual.Present;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
public class RegistrationDTOIn {
    @NotNull
    private Integer userId;

    @NotNull
    private Integer eventId;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
