package com.kotlinkoalas.koalamarket.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
public class Buyer extends Client{

    @Column(name = "buying_limit")
    private String buyingLimit;

    @Column(name = "points")
    private int points;

    @Column(name = "bizum")
    private String bizum;

    @Column(name = "paypal")
    private String paypal;

    @OneToMany(mappedBy = "buyer")
    @ToString.Exclude
    private List<Address> shippingAddresses;

    @OneToMany(mappedBy = "buyer")
    @ToString.Exclude
    private List<Address> billingAddresses;

    @OneToMany(mappedBy = "buyer")
    @ToString.Exclude
    private List<CreditCard> creditCards;

    public Buyer(){
        super();
        this.shippingAddresses = new ArrayList<>();
        this.billingAddresses = new ArrayList<>();
        this.creditCards = new ArrayList<>();
        this.buyingLimit = "";
        this.points = 0;
        this.bizum = "";
        this.paypal = "";
    }
}