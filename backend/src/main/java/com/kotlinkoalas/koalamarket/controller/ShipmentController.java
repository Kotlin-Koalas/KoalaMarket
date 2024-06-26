package com.kotlinkoalas.koalamarket.controller;

import com.kotlinkoalas.koalamarket.model.Shipment;
import com.kotlinkoalas.koalamarket.service.ShipmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        String cif = (String) payload.get("cif");
        return shipmentService.addShipment(dni, name, surname, shippingAddress, billingAddress, estimatedDate, totalPrice, paymentMethod, status, cif);
    }

    @PostMapping("/shipments/{id}/status")
    public ResponseEntity<String> updateShipmentStatus(@PathVariable Long id, @RequestBody Map<String, Object> payload) {
        String status = (String) payload.get("status");
        return shipmentService.updateShipmentStatus(id, status);
    }

    @GetMapping("/buyers/{dni}/shipments")
    public List<Shipment> getShipmentsByDni(@PathVariable String dni) {
        return shipmentService.getShipmentsByDni(dni);
    }

    @GetMapping("/vendors/{cif}/shipments")
    public List<Shipment> getShipmentsByCif(@PathVariable String cif) {
        return shipmentService.getShipmentsByCif(cif);
    }
}
