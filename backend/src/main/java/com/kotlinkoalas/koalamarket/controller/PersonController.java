package com.kotlinkoalas.koalamarket.controller;

import com.kotlinkoalas.koalamarket.model.Person;
import com.kotlinkoalas.koalamarket.repo.PersonRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

    private final PersonRepository repository;

    PersonController(PersonRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/persons")
    List<Person> all() {
        return repository.findAll();
    }

//    @GetMapping("/persons/{id}")
//    Person one(@PathVariable Long id) {
//    }

    @PostMapping("/persons")
    public Person newPerson(@RequestBody Person person) {
        return repository.save(person);
    }


    @PutMapping("/persons/{id}")
    Person replacePerson(@RequestBody Person newPerson, @PathVariable Long id) {

        return repository.findById(id)
                .map(person -> {
                    person.setName(newPerson.getName());
                    return repository.save(person);
                })
                .orElseGet(() -> {
                    newPerson.setId(id);
                    return repository.save(newPerson);
                });
    }

    @DeleteMapping("/persons/{id}")
    String deletePerson(@PathVariable Long id) {
        repository.deleteById(id);
        return "Successfully deleted";
    }
}
