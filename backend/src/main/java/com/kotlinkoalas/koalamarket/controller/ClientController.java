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

    @PutMapping("/clients/{dni}")
    Client replaceClient(@RequestBody Client newClient, @PathVariable String dni) {

        return repository.findById(dni)
                .map(Client -> {
                    Client.setName(Client.getName());
                    return repository.save(Client);
                })
                .orElseGet(() -> {
                    newClient.setDni(dni);
                    return repository.save(newClient);
                });
    }

    @DeleteMapping("/clients/{dni}")
    String deleteClient(@PathVariable String dni) {
        repository.deleteById(dni);
        return "Successfully deleted";
    }
}
