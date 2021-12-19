package com.uni.labor6_2021_2022_javafx.repository;

import com.uni.labor6_2021_2022_javafx.model.Lehrer;
import com.uni.labor6_2021_2022_javafx.model.Person;
import com.uni.labor6_2021_2022_javafx.model.Vorlesung;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class VorlesungRepositoryTest {
    private final PersonRepository personRepository = new PersonRepository();
    private final Person person = new Person(1L,"Zoe", "Miller");

    private final LehrerRepository lehrerRepository = new LehrerRepository();
    private final Lehrer lehrer = new Lehrer(person, 1);

    private final VorlesungRepository vorlesungRepository = new VorlesungRepository();
    private final Vorlesung vorlesung = new Vorlesung("BD", lehrer.getLehrerID(), 100, 30, 5);

    public VorlesungRepositoryTest() {
        personRepository.save(person);
        lehrerRepository.save(lehrer);
        vorlesungRepository.save(vorlesung);
    }

    @Test
    void findOne() {
        Assertions.assertEquals(vorlesung.getVorlesungID(), vorlesungRepository.findOne(100L).getVorlesungID());
    }

    @Test
    void update() {
        vorlesungRepository.update(new Vorlesung("BD2", lehrer.getLehrerID(), 100, 30, 5));
        Assertions.assertEquals("BD2", vorlesungRepository.findOne(100L).getName());
    }
}