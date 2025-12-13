package com.example.rosnama.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EventOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty
    @Column(columnDefinition = "varchar(20) not null unique")
    private String username;

    @NotEmpty
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
            message = "Password must have at least 8 characters, " +
                               "and less than 20 characters, " +
                               "one uppercase letter, one lowercase letter, one number, " +
                               "one special character, one digit!")
    @Column(columnDefinition = "varchar(20) not null")
    private String password;

    @NotEmpty
    @Email
    @Column(columnDefinition = "varchar(40) not null")
    private String email;

    @NotEmpty
    @Pattern(regexp = "^9665\\d{8}$")
    @Column(columnDefinition = "varchar(15) not null")
    private String phone;

    @NotEmpty
    @URL
    @Column(columnDefinition = "text not null")
    private String url;

    @NotEmpty
    @Pattern(regexp = "^(Active|InActive)$")
    @Column(columnDefinition = "varchar(10) not null")
    private String status;

    @NotNull
    @PositiveOrZero
    @Column(columnDefinition = "int not null")
    private Double balance;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventOwner")
    private Set<ExternalEvent> externalEvents;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventOwner")
    private Set<InternalEvent> internalEvents;
}
