package com.example.rosnama.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor

public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "CategoryName should not be empty")
    @Column(columnDefinition = "varchar(45) not null ")
    private String name;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "category")
    private Set<InternalEvent>internalEvents;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "category")
    private Set <ExternalEvent> externalEvents;

}
