package com.uni.labor6_2021_2022_javafx.repository;

import com.uni.labor6_2021_2022_javafx.exception.DeleteVorlesungFromLehrerException;
import com.uni.labor6_2021_2022_javafx.exception.RegisterException;
import com.uni.labor6_2021_2022_javafx.model.Lehrer;
import com.uni.labor6_2021_2022_javafx.model.Person;
import com.uni.labor6_2021_2022_javafx.model.Student;
import com.uni.labor6_2021_2022_javafx.model.Vorlesung;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class RegistrationSystemTest {
    private RegistrationSystem registrationSystem = new RegistrationSystem();
    private final Person person1 = new Person(1L,"Zoe", "Miller");
    private final Person person2 = new Person(2L, "Alice", "Hart");
    private final Person person3 = new Person(3L,"Alice", "Miller");
    private final Person person4 = new Person(4L, "Tom", "John");
    private final Person person5 = new Person(5L, "Jack", "Storm");

    private final Student student1 = new Student(person1, 1111);
    private final Student student2 = new Student(person2, 1112);
    private final Student student3 = new Student(person3, 1113);

    private final Lehrer lehrer1 = new Lehrer(person4, 1);
    private final Lehrer lehrer2 = new Lehrer(person5, 2);

    private final Vorlesung vorlesung1 = new Vorlesung("BD", lehrer1.getLehrerID(), 100, 30, 5);
    private final Vorlesung vorlesung2 = new Vorlesung("BD2", lehrer2.getLehrerID(), 101, 31, 6);
    private final Vorlesung vorlesung3 = new Vorlesung("BD3", lehrer1.getLehrerID(), 102, 32, 7);

    public RegistrationSystemTest(){

        registrationSystem.getStudentRepository().save(student1);
        registrationSystem.getStudentRepository().save(student2);

        registrationSystem.getLehrerRepository().save(lehrer1);
        registrationSystem.getLehrerRepository().save(lehrer2);

        registrationSystem.getVorlesungRepository().save(vorlesung1);
        registrationSystem.getVorlesungRepository().save(vorlesung2);
        registrationSystem.getVorlesungRepository().save(vorlesung3);
    }

    @Test
    void register() throws RegisterException {
        Assertions.assertTrue(registrationSystem.register(vorlesung1.getVorlesungID(), student1.getStudentID()));
        Assertions.assertTrue(registrationSystem.register(vorlesung1.getVorlesungID(), student2.getStudentID()));
        Assertions.assertTrue(registrationSystem.register(vorlesung2.getVorlesungID(), student1.getStudentID()));
        Assertions.assertTrue(registrationSystem.register(vorlesung2.getVorlesungID(), student2.getStudentID()));

        List<Long> list = new ArrayList<>();
        Assertions.assertEquals(100, registrationSystem.getVorlesungRepository().findOne(100L).getVorlesungID());
        list.add(1111L);
        list.add(1112L);
        Assertions.assertEquals(list, registrationSystem.getVorlesungRepository().findOne(100L).getStudentsEnrolled());


        list.clear();
        Assertions.assertEquals(101, registrationSystem.getVorlesungRepository().findOne(101L).getVorlesungID());
        list.add(1111L);
        list.add(1112L);
        Assertions.assertEquals(list, registrationSystem.getVorlesungRepository().findOne(101L).getStudentsEnrolled());

        list.clear();
        Assertions.assertEquals(102, registrationSystem.getVorlesungRepository().findOne(102L).getVorlesungID());
        Assertions.assertEquals(list, registrationSystem.getVorlesungRepository().findOne(102L).getStudentsEnrolled());

        Assertions.assertEquals(1111, registrationSystem.getStudentRepository().findOne(1111L).getStudentID());
        list.add(100L);
        list.add(101L);
        Assertions.assertEquals(list, registrationSystem.getStudentRepository().findOne(1111L).getEnrolledCourses());
        Assertions.assertEquals(11, registrationSystem.getStudentRepository().findOne(1111L).getTotalCredits());

        Assertions.assertEquals(1112, registrationSystem.getStudentRepository().findOne(1112L).getStudentID());
        Assertions.assertEquals(list, registrationSystem.getStudentRepository().findOne(1112L).getEnrolledCourses());
        Assertions.assertEquals(11, registrationSystem.getStudentRepository().findOne(1112L).getTotalCredits());

    }

    @Test
    void unregister() throws RegisterException {
        register();
        registrationSystem.unregister(vorlesung1.getVorlesungID(), student1.getStudentID());

        List<Long> list = new ArrayList<>();
        Assertions.assertEquals(100, registrationSystem.getVorlesungRepository().findOne(100L).getVorlesungID());
        list.add(1112L);
        Assertions.assertEquals(list, registrationSystem.getVorlesungRepository().findOne(100L).getStudentsEnrolled());

        list.clear();
        Assertions.assertEquals(101, registrationSystem.getVorlesungRepository().findOne(101L).getVorlesungID());
        list.add(1111L);
        list.add(1112L);
        Assertions.assertEquals(list, registrationSystem.getVorlesungRepository().findOne(101L).getStudentsEnrolled());

        list.clear();
        Assertions.assertEquals(1111, registrationSystem.getStudentRepository().findOne(1111L).getStudentID());
        list.add(101L);
        Assertions.assertEquals(list, registrationSystem.getStudentRepository().findOne(1111L).getEnrolledCourses());
        Assertions.assertEquals(6, registrationSystem.getStudentRepository().findOne(1111L).getTotalCredits());

        list.clear();
        Assertions.assertEquals(1112, registrationSystem.getStudentRepository().findOne(1112L).getStudentID());
        list.add(100L);
        list.add(101L);
        Assertions.assertEquals(list, registrationSystem.getStudentRepository().findOne(1112L).getEnrolledCourses());
        Assertions.assertEquals(11, registrationSystem.getStudentRepository().findOne(1112L).getTotalCredits());

    }

    @Test
    void retrieveCoursesWithFreePlaces() throws  RegisterException {
        Assertions.assertTrue(registrationSystem.register(vorlesung1.getVorlesungID(), student1.getStudentID()));
        Assertions.assertTrue(registrationSystem.register(vorlesung1.getVorlesungID(), student2.getStudentID()));
        Assertions.assertTrue(registrationSystem.register(vorlesung2.getVorlesungID(), student1.getStudentID()));
        Assertions.assertTrue(registrationSystem.register(vorlesung2.getVorlesungID(), student2.getStudentID()));


        HashMap<Integer, Vorlesung> map = new HashMap<Integer, Vorlesung>();
        map.put(32, vorlesung3);
        map.put(28, vorlesung1);
        map.put(29, vorlesung2);
        Assertions.assertEquals(map, registrationSystem.retrieveCoursesWithFreePlaces());

    }

    @Test
    void retrieveStudentsEnrolledForACourse() throws RegisterException {
        register();

        List<Long> list = new ArrayList<>();
        list.add(1111L);
        list.add(1112L);
        Assertions.assertEquals(list, registrationSystem.retrieveStudentsEnrolledForACourse(vorlesung1.getVorlesungID()));
        Assertions.assertEquals(list, registrationSystem.retrieveStudentsEnrolledForACourse(vorlesung2.getVorlesungID()));
        list.clear();
        Assertions.assertEquals(list, registrationSystem.retrieveStudentsEnrolledForACourse(vorlesung3.getVorlesungID()));

    }

    @Test
    void getAllCourses() throws RegisterException {
        register();
        Assertions.assertEquals(3, registrationSystem.getAllCourses().size());
    }

    @Test
    void getAllStudents() throws RegisterException {
        register();
        Assertions.assertEquals(2, registrationSystem.getAllStudents().size());
    }

    @Test
    void getAllLehrer() throws RegisterException {
        register();
        Assertions.assertEquals(2, registrationSystem.getAllLehrer().size());
    }

    @Test
    void changeCreditFromACourse() throws RegisterException {
        Assertions.assertTrue(registrationSystem.register(vorlesung1.getVorlesungID(), student1.getStudentID()));
        Assertions.assertTrue(registrationSystem.register(vorlesung2.getVorlesungID(), student1.getStudentID()));

        registrationSystem.changeCreditFromACourse(100L, 10);
        Assertions.assertEquals(1111, registrationSystem.getStudentRepository().findOne(1111L).getStudentID());
        Assertions.assertEquals(16, registrationSystem.getStudentRepository().findOne(1111L).getTotalCredits());

    }

    @Test
    void deleteVorlesungFromLehrer() throws RegisterException, DeleteVorlesungFromLehrerException {
        register();
        registrationSystem.deleteVorlesungFromLehrer(lehrer1.getLehrerID(), vorlesung1.getVorlesungID());

        List<Vorlesung> list = new ArrayList<>();
        list.add(vorlesung2);
        list.add(vorlesung3);
        Assertions.assertEquals(list, registrationSystem.getAllCourses());
    }
}