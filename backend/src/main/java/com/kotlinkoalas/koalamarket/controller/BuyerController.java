package com.kotlinkoalas.koalamarket.controller;

import com.google.gson.Gson;
import com.kotlinkoalas.koalamarket.model.*;
import com.kotlinkoalas.koalamarket.repo.BuyerRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class BuyerController {

    private final BuyerRepository repository;

    BuyerController(BuyerRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/buyers")
    List<Buyer> all() {
        return repository.findAll();
    }

    @PutMapping("/buyers/{id}")
    Buyer replaceBuyer(@RequestBody Buyer newBuyer, @PathVariable String id) {
        return repository.findById(id)
                .map(buyer -> {
                    buyer.setDni(newBuyer.getDni());
                    return repository.save(buyer);
                })
                .orElseGet(() -> {
                    newBuyer.setDni(id);
                    return repository.save(newBuyer);
                });
    }

    @DeleteMapping("/buyers/{id}")
    String deleteBuyer(@PathVariable String id) {
        repository.deleteById(id);
        return "Successfully deleted";
    }

    @PostMapping("/buyers/login")
    public Buyer login(@RequestBody Map<String, Object> payload) {
        String email = (String) payload.get("email");
        String password = (String) payload.get("password");

        Buyer existingBuyer = repository.findByEmail(email);

        if (existingBuyer != null && Objects.equals(existingBuyer.getPassword(), password)) {
            return existingBuyer;
        } else {
            return new Buyer();
        }
    }

    @PostMapping("/buyers/register")
    public ResponseEntity<String> register(@RequestBody Map<String, Object> payload) {
        String dni = (String) payload.get("dni");
        String name = (String) payload.get("name");
        String surname = (String) payload.get("surname");
        String userID = (String) payload.get("userID");
        String email = (String) payload.get("email");
        String password = (String) payload.get("password");

        String CVC = (String) payload.get("cvc");
        String cardNumber = (String) payload.get("cardNumber");
        String expirationDate = (String) payload.get("expirationDate");

//        String shippingAddress = ;
//        String billingAddress = ;

        String bizum = (String) payload.get("bizum");
        String paypal = (String) payload.get("paypal");

        Buyer buyer = new Buyer();

        buyer.setDni(dni);
        buyer.setName(name);
        buyer.setSurname(surname);
        buyer.setUserID(userID);
        buyer.setEmail(email);
        buyer.setPassword(password);

        List<Address> shippingAddresses = buyer.getShippingAddresses();
        Address newShippingAddress = new Address((String) payload.get("shippingAddress"));
        newShippingAddress.setBuyer(buyer);
        shippingAddresses.add(newShippingAddress);
        buyer.setShippingAddresses(shippingAddresses);

        List<Address> billingAddresses = buyer.getBillingAddresses();
        Address newBillingAddress = new Address((String) payload.get("billingAddress"));
        newBillingAddress.setBuyer(buyer);
        billingAddresses.add(newBillingAddress);
        buyer.setBillingAddresses(billingAddresses);


        if (cardNumber != null && !cardNumber.isEmpty() && expirationDate != null && !expirationDate.isEmpty() && CVC != null && !CVC.isEmpty()){
            CreditCard creditCard = new CreditCard(CVC, cardNumber, expirationDate);
            List<CreditCard> creditCards = new ArrayList<>();
            creditCard.setBuyer(buyer);
            creditCards.add(creditCard);
            buyer.setCreditCards(creditCards);
        }

        if (bizum != null && !bizum.isEmpty()) {
            buyer.setBizum(bizum);
        }

        if (paypal != null && !paypal.isEmpty()) {
            buyer.setPaypal(paypal);
        }

        Buyer existingBuyer = repository.findByDni(buyer.getDni());

        if (existingBuyer == null) {
            repository.save(buyer);
            Gson gson = new Gson();
            String response = gson.toJson(buyer);
            return ResponseEntity.ok()
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                    .body(response);
        } else {
            return ResponseEntity.status(400)
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                    .body("{\"message\": \"A buyer already exists\"}");
        }
    }
}