package com.example.rosnama.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String username;

    @NotEmpty(message = "password should not be empty")
    private String password;


    @NotNull(message = "Age should not be empty")
    @Max(100)
    private Integer age;
    @Pattern(regexp = "^[0-9]{10}$")
    @NotEmpty(message = "phoneNumber should not be empty")
    private String phoneNumber;

    @NotEmpty(message = "password should not be empty")
    @Email(message = "Email should be valid")
    private String email;




}
