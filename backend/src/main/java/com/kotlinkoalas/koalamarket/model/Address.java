package com.kotlinkoalas.koalamarket.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(name = "address", nullable = false)
    private String address;

    @JsonBackReference
    @ManyToOne
    private transient Buyer buyer;

    public Address(String address) {
        this.address = address;
    }
}