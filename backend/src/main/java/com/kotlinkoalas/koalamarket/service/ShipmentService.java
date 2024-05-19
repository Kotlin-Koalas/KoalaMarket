package com.kotlinkoalas.koalamarket.service;

import com.kotlinkoalas.koalamarket.model.Shipment;
import com.kotlinkoalas.koalamarket.repo.ShipmentRepository;
import org.springframework.stereotype.Service;

@Service
public class ShipmentService {

    private final ShipmentRepository repository;

    public ShipmentService(ShipmentRepository repository) {
        this.repository = repository;
    }

    public void addShipment(String dni, String name, String surname, String shippingAddress, String billingAddress, String estimatedDate, String totalPrice, String paymentMethod, String status) {
        Shipment newShipment = new Shipment();
        newShipment.setDni(dni);
        newShipment.setName(name);
        newShipment.setSurname(surname);
        newShipment.setShippingAddress(shippingAddress);
        newShipment.setBillingAddress(billingAddress);
        newShipment.setEstimatedDate(estimatedDate);
        newShipment.setTotalPrice(totalPrice);
        newShipment.setPaymentMethod(paymentMethod);
        newShipment.setStatus(status);
        repository.save(newShipment);
    }

    public void updateShipmentStatus(Long id, String status) {
        Shipment currentShipment = repository.findById(id).orElseThrow();
        currentShipment.setStatus(status);
        repository.save(currentShipment);
    }
}
