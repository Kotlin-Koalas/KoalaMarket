package com.kotlinkoalas.koalamarket.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dni;

    private String name;

    private String surname;

    private String shippingAddress;

    private String billingAddress;

    private String estimatedDate;

    private String totalPrice;

    private String paymentMethod;

    private String status;

}
