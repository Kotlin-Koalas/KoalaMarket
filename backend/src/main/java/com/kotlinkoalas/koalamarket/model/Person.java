package com.kotlinkoalas.koalamarket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Generated;

@Data
@Entity
@Table(name = "person")
public class Person {
    private @Id @GeneratedValue Long id;
    private String name;
}
