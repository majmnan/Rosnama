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

@AllArgsConstructor
@Data
public class ExternalEventDTO {

    @NotEmpty(message = " event title can not be empty ! ")
    @Column(columnDefinition = "varchar(30) not null ")
    private String title ;

    @NotEmpty(message = " event organizer name can not be empty ! ")
    @Column(columnDefinition = "varchar(50) not null ")
    private String organizationName ;

    @NotEmpty(message = " event city can not be empty ! ")
    @Column(columnDefinition = "varchar(20) not null ")
    private String description ;

    @NotNull(message = " event end date can not be empty ! ")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date not null")
    private String city;

    @NotNull(message = " event start date can not be empty ! ")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date not null")
    private LocalDate startDate;

    @NotNull(message = " event end date can not be empty ! ")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date not null")
    private LocalDate endDate;

    @NotNull(message = " event start time can not be empty ! ")
    @JsonFormat(pattern = "HH:mm:ss")
    @Column(columnDefinition = "timestamp not null")
    private LocalTime startTime;

    @NotNull(message = " event end time can not be empty ! ")
    @JsonFormat(pattern = "HH:mm:ss")
    @Column(columnDefinition = "timestamp not null")
    private LocalTime endTime;

    @NotEmpty
    @URL
    @Column(columnDefinition = "text not null")
    private String url;

    @NotNull(message = "ownerId can not be null !")
    private Integer ownerId;


}
