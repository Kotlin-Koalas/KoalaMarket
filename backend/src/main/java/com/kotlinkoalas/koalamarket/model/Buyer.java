package com.kotlinkoalas.koalamarket.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import com.kotlinkoalas.koalamarket.model.CreditCard;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Buyer extends Client{

    @Column(name = "buying_limit")
    private String buyingLimit;

    @Column(name = "points")
    private int points;

    @Column(name = "billing_address", nullable = false)
    private String billingAddress;

    @OneToMany(mappedBy = "buyer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<CreditCard> creditCards;
}