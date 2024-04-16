package com.kotlinkoalas.koalamarket.controller;

import com.kotlinkoalas.koalamarket.model.Client;
import com.kotlinkoalas.koalamarket.repo.ClientRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientController {

    private final ClientRepository repository;

    ClientController(ClientRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/clients")
    List<Client> all() {
        return repository.findAll();
    }

    @PutMapping("/clients/{id}")
    Client replaceClient(@RequestBody Client newClient, @PathVariable String id) {

        return repository.findById(id)
                .map(Client -> {
                    Client.setName(Client.getName());
                    return repository.save(Client);
                })
                .orElseGet(() -> {
                    newClient.setDni(id);
                    return repository.save(newClient);
                });
    }

    @DeleteMapping("/clients/{id}")
    String deleteClient(@PathVariable String id) {
        repository.deleteById(id);
        return "Successfully deleted";
    }
}
