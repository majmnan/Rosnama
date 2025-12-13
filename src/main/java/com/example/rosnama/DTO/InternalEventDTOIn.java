package com.example.rosnama.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class InternalEventDTOIn {

    @NotEmpty(message = " event title can not be empty ! ")
    private String title ;

    @NotEmpty(message = " event description can not be empty ! ")
    private String description ;

    @NotEmpty(message = " event city can not be empty ! ")
    private String city;

    @NotNull
    private String location;

    @NotNull(message = " event start date can not be empty ! ")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = " event end date can not be empty ! ")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @NotNull(message = " event start time can not be empty ! ")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;

    @NotNull(message = " event end time can not be empty ! ")
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime endTime;

    @NotNull(message = "price should not be empty")
    @Column(columnDefinition = "double not null")
    private Double price;

    @NotNull(message = "ownerId can not be null !")
    private Integer ownerId;

    @NotEmpty
    @Pattern(regexp =  "^(Conference|MeetAndGreets|Hackathon|Opening Ceremony|Others)$",
            message = "Type must be one of the predefined categories :" +
                    " (Conference , MeetAndGreets , Hackathon , Opening Ceremony, Others)")
    private String type;

    @NotNull(message = "dailyCapacity should be not null")
    @Column(columnDefinition = " int not null ")
    private Integer dailyCapacity;

    @NotNull(message = "categoryId cannot be null")
    private Integer categoryId;

}
