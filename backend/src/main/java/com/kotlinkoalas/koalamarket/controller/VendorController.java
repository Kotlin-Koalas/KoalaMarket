package com.kotlinkoalas.koalamarket.controller;

import com.kotlinkoalas.koalamarket.model.Vendor;
import com.kotlinkoalas.koalamarket.repo.VendorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class VendorController {

    private final VendorRepository repository;

    VendorController(VendorRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/vendors")
    List<Vendor> all() {
        return repository.findAll();
    }

    @PostMapping("/vendors")
    public Vendor newVendor(@RequestBody Vendor vendor) {
        return repository.save(vendor);
    }

    @PutMapping("/vendors/{id}")
    Vendor replaceVendor(@RequestBody Vendor newVendor, @PathVariable String id) {

        return repository.findById(id)
                .map(vendor -> {
                    vendor.setDni(newVendor.getDni());
                    return repository.save(vendor);
                })
                .orElseGet(() -> {
                    newVendor.setDni(id);
                    return repository.save(newVendor);
                });
    }

    @DeleteMapping("/vendors/{id}")
    String deleteVendor(@PathVariable String id) {
        repository.deleteById(id);
        return "Successfully deleted";
    }

    @PostMapping("/vendors/login")
    public Vendor login(@RequestBody Map<String, Object> payload) {
        String email = (String) payload.get("email");
        String password = (String) payload.get("password");

        Vendor existingVendor = repository.findByEmail(email);

        if (existingVendor != null && Objects.equals(existingVendor.getPassword(), password)) {
            return existingVendor;
        } else {
            return new Vendor();
        }
    }

    @PostMapping("/vendors/register")
    public ResponseEntity<String> register(@RequestBody Map<String, Object> payload) {
        String cif = (String) payload.get("cif");
        String name = (String) payload.get("name");
        String surname = (String) payload.get("surname");
        String userID = (String) payload.get("userID");
        String email = (String) payload.get("email");
        String password = (String) payload.get("password");

        String iban = (String) payload.get("iban");

        Vendor newVendor = new Vendor();

        newVendor.setDni(cif);
        newVendor.setName(name);
        newVendor.setSurname(surname);
        newVendor.setUserID(userID);
        newVendor.setEmail(email);
        newVendor.setPassword(password);

        newVendor.setIban(iban);

        Vendor existingVendor = repository.findByDni(newVendor.getDni());

        if (existingVendor != null) {
            return ResponseEntity.status(400).body("A buyer with the same DNI already exists");
        } else {
            repository.save(newVendor);
            return ResponseEntity.ok("Registration successful");
        }
    }

}
