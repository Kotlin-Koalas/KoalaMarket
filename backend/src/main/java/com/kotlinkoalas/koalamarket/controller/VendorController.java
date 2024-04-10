package com.kotlinkoalas.koalamarket.controller;

import com.kotlinkoalas.koalamarket.model.Vendor;
import com.kotlinkoalas.koalamarket.repo.VendorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

//    @GetMapping("/persons/{id}")
//    Person one(@PathVariable Long id) {
//    }

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
}
