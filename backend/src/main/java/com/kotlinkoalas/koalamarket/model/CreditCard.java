package com.kotlinkoalas.koalamarket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Entity

public class CreditCard {
    @ManyToOne(targetEntity = Buyer.class)
    private Buyer buyer;

    @Id
    private String cardNumber;

    private String expirationDate;

    private String cvv;
}
