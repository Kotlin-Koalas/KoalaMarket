package com.kotlinkoalas.koalamarket.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
public class Vendor extends Client {

    @Column(name = "iban", nullable = false)
    private String iban;


    public Vendor(){
        super();
        this.iban = "";
    }
}
