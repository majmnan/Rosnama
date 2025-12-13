package com.example.rosnama.DTO;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExternalEventDTOIn {

    @NotEmpty(message = " event title can not be empty ! ")
    private String title ;

    @NotEmpty(message = " event organizer name can not be empty ! ")
    private String organizationName ;

    @NotEmpty(message = " event description can not be empty ! ")
    private String description ;

    @NotNull(message = " event city can not be empty ! ")
    private String city;

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

    @NotEmpty
    @URL
    private String url;

    @NotEmpty
    @Pattern(regexp =  "^(Conference|MeetAndGreets|Hackathon|Opening Ceremony|Others)$",
            message = "Type must be one of the predefined categories :" +
                    " (Conference , MeetAndGreets , Hackathon , Opening Ceremony, Others)")
    private String type;


    @NotNull(message = "ownerId can not be null !")
    private Integer ownerId;

    @NotNull(message = "CategoryId cannot be null")
    private Integer categoryId;
}
