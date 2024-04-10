package com.kotlinkoalas.koalamarket.repo;

import com.kotlinkoalas.koalamarket.model.Client;
import com.kotlinkoalas.koalamarket.model.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestController
public interface ClientRepository extends JpaRepository<Client, String> {}