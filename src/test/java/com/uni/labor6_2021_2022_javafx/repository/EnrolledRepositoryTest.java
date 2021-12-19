package com.uni.labor6_2021_2022_javafx.repository;

import com.uni.labor6_2021_2022_javafx.model.Lehrer;
import com.uni.labor6_2021_2022_javafx.model.Person;
import com.uni.labor6_2021_2022_javafx.model.Student;
import com.uni.labor6_2021_2022_javafx.model.Vorlesung;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EnrolledRepositoryTest {
    private final PersonRepository personRepository = new PersonRepository();
    private final Person person1 = new Person(1L,"Zoe", "Miller");
    private final Person person2 = new Person(2L,"Alice", "Hart");

    private final StudentRepository studentRepository = new StudentRepository();
    private final Student student = new Student(person1, 1111);

    private final LehrerRepository lehrerRepository = new LehrerRepository();
    private final Lehrer lehrer = new Lehrer(person2, 1);

    private final VorlesungRepository vorlesungRepository = new VorlesungRepository();
    private final Vorlesung vorlesung = new Vorlesung("BD", lehrer.getLehrerID(), 100, 30, 5);

    private final EnrolledRepository enrolledRepository = new EnrolledRepository(vorlesungRepository, studentRepository);

    public EnrolledRepositoryTest() {
        personRepository.save(person1);
        personRepository.save(person2);
        studentRepository.save(student);
        lehrerRepository.save(lehrer);
        vorlesungRepository.save(vorlesung);
    }

    @Test
    void findOne() {
        Assertions.assertFalse(enrolledRepository.findOne(100L, 1111L));
    }

    @Test
    void save() {
        enrolledRepository.save(100L, 1111L);
        Assertions.assertTrue(enrolledRepository.findOne(100L, 1111L));

    }

    @Test
    void delete() {
        save();
        Assertions.assertTrue(enrolledRepository.findOne(100L, 1111L));
        enrolledRepository.delete(100L, 1111L);
        Assertions.assertFalse(enrolledRepository.findOne(100L, 1111L));
    }
}