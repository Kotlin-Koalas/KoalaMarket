package com.kotlinkoalas.koalamarket.controller;

import com.kotlinkoalas.koalamarket.service.ShipmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ShipmentController {

    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @PostMapping("/buyers/{dni}/shipments")
    public ResponseEntity<String> addShipment(@PathVariable String dni, @RequestBody Map<String, Object> payload) {
        String name = (String) payload.get("name");
        String surname = (String) payload.get("surname");
        String shippingAddress = (String) payload.get("shippingAddress");
        String billingAddress = (String) payload.get("billingAddress");
        String estimatedDate = (String) payload.get("estimatedDate");
        String totalPrice = (String) payload.get("totalPrice");
        String paymentMethod = (String) payload.get("paymentMethod");
        String status = (String) payload.get("status");
        return shipmentService.addShipment(dni, name, surname, shippingAddress, billingAddress, estimatedDate, totalPrice, paymentMethod, status);
    }

    @PostMapping("/buyers/{dni}/shipments/{id}/status")
    public ResponseEntity<String> updateShipmentStatus(@PathVariable String dni, @PathVariable Long id, @RequestBody Map<String, Object> payload) {
        String status = (String) payload.get("status");
        return shipmentService.updateShipmentStatus(id, status);
    }
}
