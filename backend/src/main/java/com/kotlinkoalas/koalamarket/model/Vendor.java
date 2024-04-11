package com.kotlinkoalas.koalamarket.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Vendor extends Client {
    @Column(name = "bank_data", nullable = false)
    private String bankData;

    @Column(name = "cif", length = 30, nullable = false, unique = true)
    private String cif;

}
