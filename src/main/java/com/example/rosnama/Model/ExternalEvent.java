package com.example.rosnama.Model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Entity
@Setter
@Getter
@NoArgsConstructor
public class ExternalEvent {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;  //-

    @NotEmpty(message = " event title can not be empty ! ")
    @Column(columnDefinition = "varchar(30) not null ")
    private String title ;

    @NotEmpty(message = " event organizer name can not be empty ! ")
    @Column(columnDefinition = "varchar(50) not null ")
    private String organizationName ;

    @NotEmpty(message = " event description can not be empty ! ")
    @Size(min = 10 , message = " description can not be less than 10 characters ! ")
    @Column(columnDefinition = "text not null ")
    private String description ;

    @NotEmpty(message = " event city can not be empty ! ")
    @Column(columnDefinition = "varchar(20) not null ")
    private String city;

    @NotNull(message = " event start date can not be empty ! ")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date not null")
    private LocalDate start_date;

    @NotNull(message = " event end date can not be empty ! ")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(columnDefinition = "date not null")
    private LocalDate end_date;

    @NotNull(message = " event start time can not be empty ! ")
    @JsonFormat(pattern = "HH:mm:ss")
    @Column(columnDefinition = "timestamp not null")
    private LocalTime start_time;

    @NotNull(message = " event end time can not be empty ! ")
    @JsonFormat(pattern = "HH:mm:ss")
    @Column(columnDefinition = "timestamp not null")
    private LocalTime end_time;

    @NotEmpty
    @URL
    @Column(columnDefinition = "text not null")
    private String url;

    @NotEmpty(message = " event status can not be empty ! ")
    @Pattern(regexp = "^(InActive|Active)$")
    @Column(columnDefinition = "varchar(20) not null ")
    private String status ; //-

    @OneToOne(mappedBy = "externalEvent", cascade = CascadeType.ALL)
    @JsonIgnore
    @PrimaryKeyJoinColumn
    private ExternalEventRequest externalEventRequest; //-

    @ManyToOne
    @JsonIgnore
    private EventOwner eventOwner; //in dto its name is event owner id


}
