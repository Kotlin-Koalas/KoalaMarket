package com.kotlinkoalas.koalamarket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@ToString
public class CreditCard {
    @ManyToOne(targetEntity = Buyer.class)
    private Buyer buyer;

    @Id
    private String cardNumber;

    private String expirationDate;

    private String cvv;

    public CreditCard(String cvc, String cardNumber, String expirationDate) {
        this.cvv = cvc;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
    }

    public CreditCard() {

    }
}
