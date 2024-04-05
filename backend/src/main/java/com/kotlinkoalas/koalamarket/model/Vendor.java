package com.kotlinkoalas.koalamarket.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "vendor")
public class Vendor {
    @Column(name = "bank_data", nullable = false)
    private String bankData;

    @Id
    @Column(name = "cif", length = 30, nullable = false, unique = true)
    private String cif;

    @Id
    @Column(name = "dni", length = 30, nullable = false)
    private String dni;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dni", referencedColumnName = "dni")
    private User user;
}
