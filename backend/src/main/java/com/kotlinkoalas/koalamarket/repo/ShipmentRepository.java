package com.kotlinkoalas.koalamarket.repo;

import com.kotlinkoalas.koalamarket.model.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;

import java.util.List;

@RepositoryRestController
public interface ShipmentRepository extends JpaRepository<Shipment, Long>{
    List<Shipment> findAllByDni(String dni);
    List<Shipment> findAllByCif(String cif);
}
