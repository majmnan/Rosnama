package com.example.rosnama.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$")
    @Column(columnDefinition = "varchar(20) not null")
    private String password;


    @NotNull(message = "Age should not be empty")
    @Column(columnDefinition = "int not null")
    @Max(100)
    private Integer age;


    @NotEmpty
    @Pattern(regexp = "^9665\\d{8}$")
    @Column(columnDefinition = "varchar(15) not null unique")
    private String phoneNumber;

    @NotEmpty(message = "password should not be empty")
    @Email(message = "Email should be valid")
    @Column(columnDefinition = "varchar(40) not null unique")
    private String email;

    knjkndalnalfknadfwed

    khvljhbk;jnl;kn
    lmlkmlkm
}