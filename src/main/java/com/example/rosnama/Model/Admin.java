package com.example.rosnama.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = " error user name can not be empty ! ")
    @Size(min = 3 , message = "error user name can not be less than 3 characters ! ")
    @Column(columnDefinition = "varchar(40) not null unique")
    private String username;

    @NotEmpty(message = " error password can not be empty ! ")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
             message = "error password must have at least 8 characters , " +
                       "and less than 20 characters , " +
                       "one uppercase letter , one lowercase letter and one number +" +
                       "one special character , one digit ! ")
    @Column(columnDefinition = "varchar(20) not null")
    private String password;

    @NotEmpty(message = " error phone number can not be empty ! ")
    @Pattern(regexp = "^9665\\d{8}$", message = " error phone number must be in this format 966 5x xxx xxxx ! ")
    @Column(columnDefinition = "varchar(14) not null unique")
    private String phoneNumber;


    @NotEmpty(message = " error email can not be empty ! ")
    @Email(message = " please enter a valid email ! ")
    @Column(columnDefinition = "varchar(40) not null unique")
    private String email;


}
