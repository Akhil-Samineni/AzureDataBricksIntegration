package com.samiak.azuredatabricks.controller;

import com.samiak.azuredatabricks.model.Person;
import com.samiak.azuredatabricks.respository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MongoController {
    @Autowired
    PersonRepository personRepository;

    @GetMapping("/mongoDbPersons")
    public List<Person> getPersons() {
        List<Person> people = personRepository.findAll();
        return people;
    }
}
