package com.example.rosnama.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Username should not be empty")
    @Column(columnDefinition = "varchar(50) not null")
    private String username;

    @NotEmpty
    @Pattern( regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
             message = "Password must have at least 8 characters, " +
                            "and less than 20 characters, " +
                            "one uppercase letter, one lowercase letter, one number, " +
                            "one special character, one digit!")
    @Column(columnDefinition = "varchar(20) not null")
    private String password;

    @NotNull(message = "Age should not be empty")
    @Column(columnDefinition = "int not null")
    @Max(100)
    private Integer age;

    @NotEmpty
    @Pattern(regexp = "^9665\\d{8}$",
            message = "Phone number must be in the format 9665xxxxxxxx")
    @Column(columnDefinition = "varchar(15) not null")
    private String phoneNumber;

    @NotEmpty(message = "password should not be empty")
    @Email(message = "Email should be valid")
    @Column(columnDefinition = "varchar(40) not null ")
    private String email;

    @NotNull(message = "balance should not be null ")
    @PositiveOrZero(message = "balance must be 0 or higher")
    private Double balance;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Registration> registrations;

}