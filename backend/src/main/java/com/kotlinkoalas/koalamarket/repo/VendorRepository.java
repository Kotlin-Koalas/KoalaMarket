package com.kotlinkoalas.koalamarket.repo;

import com.kotlinkoalas.koalamarket.model.Clothes;
import com.kotlinkoalas.koalamarket.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface VendorRepository extends JpaRepository<Vendor, String> {}