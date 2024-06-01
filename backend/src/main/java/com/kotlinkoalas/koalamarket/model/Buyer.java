package com.kotlinkoalas.koalamarket.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.gson.annotations.Expose;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
public class Buyer extends Client{

    @Expose
    @Column(name = "buying_limit")
    private String buyingLimit;

    @Expose
    @Column(name = "points")
    private int points;

    @Expose
    @Column(name = "bizum")
    private String bizum;

    @Expose
    @Column(name = "paypal")
    private String paypal;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "buyer_id")
    @ToString.Exclude
    @JsonManagedReference
    private List<Address> shippingAddresses;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "buyer_id")
    @ToString.Exclude
    @JsonManagedReference
    private List<Address> billingAddresses;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "buyer_id")
    @ToString.Exclude
    @JsonManagedReference
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Buyer buyer = (Buyer) o;
        return Objects.equals(this.getDni(), buyer.getDni());
    }
}