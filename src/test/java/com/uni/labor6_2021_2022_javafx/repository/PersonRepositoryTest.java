package com.uni.labor6_2021_2022_javafx.repository;

import com.uni.labor6_2021_2022_javafx.model.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class PersonRepositoryTest {
    private final PersonRepository personRepository = new PersonRepository();
    private final Person person = new Person(1L,"Zoe", "Miller");

    public PersonRepositoryTest() {
        personRepository.save(person);
    }

    @Test
    void findOne() {
        Assertions.assertEquals(person.getPersonID(), personRepository.findOne(1L).getPersonID());
    }

    @Test
    void update() {
        personRepository.update(new Person(1L, "Alice", "Miller"));
        Assertions.assertEquals("Alice", personRepository.findOne(1L).getVorname());
    }
}