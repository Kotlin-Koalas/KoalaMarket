package com.kotlinkoalas.koalamarket.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "buyer") // Optional: Customize table name
public class Buyer {

    @Column(name = "buying_limit")
    private String buyingLimit;

    @Column(name = "points", nullable = false)
    private int points;

    @Column(name = "billing_address", nullable = false)
    private String billingAddress;

    @Id
    @Column(name = "dni", length = 30, nullable = false)
    private String dni;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dni", referencedColumnName = "dni")
    private Client user;

}