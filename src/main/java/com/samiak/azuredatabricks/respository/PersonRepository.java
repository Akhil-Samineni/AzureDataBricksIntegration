package com.samiak.azuredatabricks.respository;

import com.samiak.azuredatabricks.model.Person;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonRepository extends MongoRepository<Person, String> {
}