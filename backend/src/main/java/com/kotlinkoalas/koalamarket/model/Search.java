package com.kotlinkoalas.koalamarket.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Search {
    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private String search;

    private String dni;
}
