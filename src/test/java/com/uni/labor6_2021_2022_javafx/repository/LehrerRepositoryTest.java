package com.uni.labor6_2021_2022_javafx.repository;

import com.uni.labor6_2021_2022_javafx.model.Lehrer;
import com.uni.labor6_2021_2022_javafx.model.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LehrerRepositoryTest {
    private final PersonRepository personRepository = new PersonRepository();
    private final Person person = new Person(1L,"Zoe", "Miller");

    private final LehrerRepository lehrerRepository = new LehrerRepository();
    private final Lehrer lehrer = new Lehrer(person, 1);

    public LehrerRepositoryTest() {
        personRepository.save(person);
        lehrerRepository.save(lehrer);
    }

    @Test
    void findOne() {
        Assertions.assertEquals(lehrer.getLehrerID(), lehrerRepository.findOne(1L).getLehrerID());
    }

    @Test
    void update() {
        Person person1 = new Person(1L, "Alice", "Miller");
        personRepository.update(person1);
        lehrerRepository.update(new Lehrer(person1, 1L));
    }
}