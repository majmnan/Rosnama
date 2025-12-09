package com.example.rosnama.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class InternalEventDTOIn {



    @NotEmpty(message = " event title can not be empty ! ")
    @Column(columnDefinition = "varchar(30) not null ")
    private String title ;



    @NotEmpty(message = " event city can not be empty ! ")
    @Column(columnDefinition = "varchar(20) not null ")
    private String description ;

    @NotNull(message = " event end date can not be empty ! ")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date not null")
    private String city;

    @NotNull(message = " event end date can not be empty ! ")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date not null")
    private LocalDate start_date;

    @NotNull(message = " event start time can not be empty ! ")
    @JsonFormat(pattern = "HH:mm:ss")
    @Column(columnDefinition = "timestamp not null")
    private LocalTime start_time;

    @NotNull(message = " event end time can not be empty ! ")
    @JsonFormat(pattern = "HH:mm:ss")
    @Column(columnDefinition = "timestamp not null")
    private LocalTime end_time;

    @NotNull(message = "ownerId can not be null !")
    private Integer ownerId;





}
