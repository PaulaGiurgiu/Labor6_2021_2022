package com.uni.labor6_2021_2022_javafx.repository;

import com.uni.labor6_2021_2022_javafx.model.Person;
import com.uni.labor6_2021_2022_javafx.model.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StudentRepositoryTest {
    private final PersonRepository personRepository = new PersonRepository();
    private final Person person = new Person(1L,"Zoe", "Miller");

    private final StudentRepository studentRepository = new StudentRepository();
    private final Student student = new Student(person, 1111);

    public StudentRepositoryTest() {
        personRepository.save(person);
        studentRepository.save(student);
    }

    @Test
    void findOne() {
        Assertions.assertEquals(student.getStudentID(), studentRepository.findOne(1111L).getStudentID());
    }

    @Test
    void update() {
        Person person1 = new Person(1L, "Alice", "Miller");
        personRepository.update(person1);
        studentRepository.update(new Student(person1, 1111L));
    }
}