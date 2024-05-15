package com.kotlinkoalas.koalamarket.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@ToString
public class CreditCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String cardNumber;

    private String expirationDate;

    private String cvv;

    @ManyToOne
    @JsonBackReference
    private transient Buyer buyer;


    public CreditCard(String cvc, String cardNumber, String expirationDate) {
        this.cvv = cvc;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
    }

    public CreditCard() {

    }
}
