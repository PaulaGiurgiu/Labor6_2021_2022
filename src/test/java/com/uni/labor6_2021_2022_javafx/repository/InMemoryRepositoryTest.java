package com.uni.labor6_2021_2022_javafx.repository;

import com.uni.labor6_2021_2022_javafx.model.Lehrer;
import com.uni.labor6_2021_2022_javafx.model.Person;
import com.uni.labor6_2021_2022_javafx.model.Student;
import com.uni.labor6_2021_2022_javafx.model.Vorlesung;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryRepositoryTest {
    private final PersonRepository personRepository = new PersonRepository();
    private final Person person1 = new Person(1L, "Alice", "Hart");
    private final Person person2 = new Person(2L, "Tom", "John");

    private final StudentRepository studentRepository = new StudentRepository();
    private final Student student1 = new Student(person1, 11L);

    private final LehrerRepository lehrerRepository = new LehrerRepository();
    private final Lehrer lehrer1 = new Lehrer(person2, 111L);

    private final VorlesungRepository vorlesungRepository = new VorlesungRepository();
    private final Vorlesung vorlesung1 = new Vorlesung("BD", lehrer1.getLehrerID(), 1111L, 30, 5);

    @Test
    void findOne() {
        personRepository.save(person1);
        assertEquals(person1, personRepository.findOne(1L));
        personRepository.save(person2);
        assertEquals(person2, personRepository.findOne(2L));

        studentRepository.save(student1);
        assertEquals(student1, studentRepository.findOne(11L));

        lehrerRepository.save(lehrer1);
        assertEquals(lehrer1, lehrerRepository.findOne(111L));

        vorlesungRepository.save(vorlesung1);
        assertEquals(vorlesung1, vorlesungRepository.findOne(1111L));
    }

    @Test
    void findAll() {
        personRepository.save(person1);
        assertEquals(1, personRepository.findAll().size());

        studentRepository.save(student1);
        assertEquals(1, studentRepository.findAll().size());

        lehrerRepository.save(lehrer1);
        assertEquals(1, lehrerRepository.findAll().size());

        vorlesungRepository.save(vorlesung1);
        assertEquals(1, vorlesungRepository.findAll().size());
    }

    @Test
    void save() {
        assertEquals(person1, personRepository.save(person1));

        assertEquals(student1, studentRepository.save(student1));

        assertEquals(lehrer1, lehrerRepository.save(lehrer1));

        assertEquals(vorlesung1, vorlesungRepository.save(vorlesung1));
    }

    @Test
    void delete() {
        personRepository.save(person1);
        assertEquals(1, personRepository.findAll().size());
        personRepository.delete(person1);
        assertEquals(0, personRepository.findAll().size());

        studentRepository.save(student1);
        assertEquals(1, studentRepository.findAll().size());
        studentRepository.delete(student1);
        assertEquals(0, studentRepository.findAll().size());

        lehrerRepository.save(lehrer1);
        assertEquals(1, lehrerRepository.findAll().size());
        lehrerRepository.delete(lehrer1);
        assertEquals(0, lehrerRepository.findAll().size());

        vorlesungRepository.save(vorlesung1);
        assertEquals(1, vorlesungRepository.findAll().size());
        vorlesungRepository.delete(vorlesung1);
        assertEquals(0, vorlesungRepository.findAll().size());

    }
}