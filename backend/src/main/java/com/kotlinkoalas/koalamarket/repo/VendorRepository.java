package com.kotlinkoalas.koalamarket.repo;

import com.kotlinkoalas.koalamarket.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface VendorRepository extends JpaRepository<Vendor, String> {
    Vendor findByDni(String dni);
    Vendor findByEmail(String email);
    boolean existsByDni(String email);
}