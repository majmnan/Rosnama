package com.example.rosnama.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","internal_event_id","date"})})
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private User user;

    @ManyToOne
    private InternalEvent internalEvent;

    @NotNull
    private LocalDate date;

    @Pattern(regexp = "^(?i)(Registered|Active|Used|Expired)$")
    @Column(columnDefinition = "varchar(15) not null")
    private String status;


}
