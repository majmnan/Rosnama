package com.example.rosnama.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@AllArgsConstructor
@Entity
@Setter
@Getter
@NoArgsConstructor
public class InternalEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Title Can't be  null")
    @Column(columnDefinition = "varchar (20) not null")
    private String title;

    @NotEmpty(message = "City Can't be not null")
    @Column(columnDefinition = "varchar(25) not null")
    private String city;

    @NotEmpty(message = "Location Can't be empty")
    @URL
    @Column(columnDefinition = "text not null")
    private String location;

    @NotEmpty(message = "Description  Can't be empty")
    @Column(columnDefinition = " text not null")
    private String description;

    @NotNull(message = "start_date  Can't be  null")
    @Column (columnDefinition = "DATE not null")
    private Date start_date;

    @NotNull(message = "end_date Can't be not null")
    @Column (columnDefinition = "DATE not null")
    private Date end_date;

    @NotNull(message = "start_time Can't be  null ")
    @Column (columnDefinition = "timestamp not null")
    private LocalTime start_time;


    @NotNull(message = "end_time Can't be  null ")
    @Column (columnDefinition = "timestamp not null")
    private LocalTime end_time;

    @ManyToOne
    @JsonIgnore
    private EventOwner eventOwner;

    //regestration relation
}
