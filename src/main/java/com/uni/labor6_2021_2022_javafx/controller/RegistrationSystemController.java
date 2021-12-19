package com.uni.labor6_2021_2022_javafx.controller;

import com.uni.labor6_2021_2022_javafx.exception.*;
import com.uni.labor6_2021_2022_javafx.model.*;
import com.uni.labor6_2021_2022_javafx.repository.LehrerRepository;
import com.uni.labor6_2021_2022_javafx.repository.RegistrationSystem;
import com.uni.labor6_2021_2022_javafx.repository.StudentRepository;
import com.uni.labor6_2021_2022_javafx.repository.VorlesungRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RegistrationSystemController {
    private final RegistrationSystem registrationSystem;

    public RegistrationSystemController(){
        this.registrationSystem = new RegistrationSystem();
        this.controller_addPerson(1L, "Zoe", "Miller");
        this.controller_addPerson(2L, "Alice", "Hart");
        this.controller_addPerson(3L, "Alice", "Miller");
        this.controller_addPerson(4L, "Tom", "John");
        this.controller_addPerson(5L, "Jack", "Storm");

        this.controller_addStudent(1L, 1111L);
        this.controller_addStudent(2L, 1112L);

        this.controller_addLehrer(4L, 11L);
        this.controller_addLehrer(5L, 12L);

        this.controller_addVorlesung("BD", 11L, 100L, 30, 5);
        this.controller_addVorlesung("BD2", 12L, 101L, 31, 6);
        this.controller_addVorlesung("BD3", 11L, 102L, 32, 7);
        this.controller_addVorlesung("BD4", 11L, 103L, 30, 8);
        this.controller_addVorlesung("BD5", 12L, 104L, 31, 9);
        this.controller_addVorlesung("BD6", 11L, 105L, 32, 10);
    }

    /**
     * @param VorlesungID eine "long" Zahl
     * @param StudentID eine "long" Zahl
     * @throws RegisterException falls der Student mit der Id "StudentID" nicht an der Vorlesung mit der Id "VorlesungID" registriert wurde
     */
    public void controller_register(long VorlesungID, long StudentID) throws RegisterException {
        registrationSystem.register(VorlesungID, StudentID);
    }

    /**
     * @param VorlesungID eine "long" Zahl
     * @param StudentID eine "long" Zahl
     * @throws RegisterException falls der Student mit der Id "StudentID" nicht aus der Vorlesung mit der Id "VorlesungID" "unregistriert" wurde
     */
    public void controller_unregister(long VorlesungID, long StudentID) throws RegisterException {
        registrationSystem.unregister(VorlesungID, StudentID);
    }

    public StudentRepository controller_studentRepository(){
        return this.registrationSystem.getStudentRepository();
    }
    public LehrerRepository controller_lehrerRepository(){return this.registrationSystem.getLehrerRepository();}

    public VorlesungRepository controller_vorlesungRepository(){
        return this.registrationSystem.getVorlesungRepository();
    }

    /**
     * @return die Liste aller Vorlesungen
     */
    public List<Vorlesung> controller_getAllCourses(){
        return registrationSystem.getAllCourses();
    }

    /**
     * @return die Liste aller Personen
     */
    public List<Person> controller_getAllPersons() {
        return registrationSystem.getAllPersons();
    }

    /**
     * @return die Liste aller Studenten
     */
    public List<Student> controller_getAllStudents() {
        return registrationSystem.getAllStudents();
    }

    /**
     * @return die Liste aller Lehrer
     */
    public List<Lehrer> controller_getAllLehrer() {
        return registrationSystem.getAllLehrer();
    }

    /**
     * @return ein HashMap mit der Vorlesungen, die freie Platze haben und deren Anzahl
     */
    public HashMap<Integer, Vorlesung> controller_retrieveCoursesWithFreePlaces() {
        return registrationSystem.retrieveCoursesWithFreePlaces();
    }

    /**
     * @param VorlesungID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @return eine Liste von Studenten, die an der gegebenen Vorlesung teilnehmen
     * @throws ExistException falls die Vorlesung nicht in der Liste ist
     */
    public List<Long> controller_retrieveStudentsEnrolledForACourse(long VorlesungID) throws ExistException {
        return registrationSystem.retrieveStudentsEnrolledForACourse(VorlesungID);
    }

    /**
     * @param VorlesungID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @param newCredit die neue Anzahl von Credits
     * @throws RegisterException falls die alte Studenten nicht mehr an der Vorlesung teilnehmen konnten
     * @throws ExistException falls die Vorlesung existiert nicht
     */
    public void controller_changeCreditFromACourse(long VorlesungID, int newCredit) throws RegisterException, ExistException{
        registrationSystem.changeCreditFromACourse(VorlesungID, newCredit);
    }

    /**
     * @param LehrerID eine "Long" Zahl, die ein "Lehrer" Id entspricht
     * @param VorlesungID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @throws DeleteVorlesungFromLehrerException falls der Lehrer nich die Vorlesung löschen kann
     * @throws RegisterException falls die Vorlesung existiert nicht
     */
    public void controller_deleteVorlesungFromLehrer(long LehrerID, long VorlesungID) throws DeleteVorlesungFromLehrerException, RegisterException {
        registrationSystem.deleteVorlesungFromLehrer(LehrerID, VorlesungID);
    }

    /**
     * @return die sortierte Liste der Studenten
     */
    public List<Student> controller_sortListeStudenten() {
        Collections.sort(registrationSystem.getAllStudents(), new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getPersonID().compareTo(o2.getPersonID());
            }
        });

        return registrationSystem.getAllStudents();
    }

    /**
     * @return die sortierte Liste der Vorlesungen
     */
    public List<Vorlesung> controller_sortListeVorlesungen(){
        Collections.sort(registrationSystem.getAllCourses(), new Comparator<Vorlesung>() {
            @Override
            public int compare(Vorlesung o1, Vorlesung o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return registrationSystem.getAllCourses();
    }

    /**
     * @return die filtrierte Liste der Studenten
     */
    public List<Student> controller_filterListeStudenten(){
        List<Student> studentList = this.controller_getAllStudents().stream()
                .filter(student -> student.getEnrolledCourses().size() >=2).collect(Collectors.toList());

        return studentList;
    }

    /**
     * @return die filtrierte Liste der Vorlesungen
     */
    public List<Vorlesung> controller_filterListeVorlesungen(){
        List<Vorlesung> vorlesungList = this.controller_getAllCourses().stream()
                .filter(vorlesung -> vorlesung.getMaxEnrollment() > 30).collect(Collectors.toList());

        return vorlesungList;
    }

    /**
     * @param personID eine "Long"-Zahl
     * @param Vorname ein String
     * @param Nachname ein String
     */
    public void controller_addPerson(Long personID, String Vorname, String Nachname){
        this.registrationSystem.getPersonRepository()
                .save(new Person(personID, Vorname, Nachname));
    }

    /**
     * @param PersonID eine "Long"-Zahl
     * @param StudentID eine "Long"-Zahl
     */
    public void controller_addStudent(Long PersonID, Long StudentID){
        if (registrationSystem.getPersonRepository().findOne(PersonID) != null) {
            registrationSystem.getStudentRepository()
                    .save(new Student(registrationSystem.getPersonRepository().findOne(PersonID), StudentID));
        }
    }

    /**
     * @param PersonID eine "Long"-Zahl
     * @param LehrerID eine "Long"-Zahl
     */
    public void controller_addLehrer(Long PersonID, Long LehrerID) {
        if (registrationSystem.getPersonRepository().findOne(PersonID) != null){
            registrationSystem.getLehrerRepository()
                    .save(new Lehrer(registrationSystem.getPersonRepository().findOne(PersonID), LehrerID));
        }

    }

    /**
     * @param Name ein String
     * @param IdLehrer ein "Long"-Zahl
     * @param IdVorlesung ein "Long"-Zahl
     * @param MaxEnrollment ein Zahl
     * @param Credits ein Zahl
     */
    public void controller_addVorlesung(String Name, Long IdLehrer, Long IdVorlesung, int MaxEnrollment, int Credits){
        if (registrationSystem.getLehrerRepository().findOne(IdLehrer) != null){
            registrationSystem.getVorlesungRepository()
                    .save(new Vorlesung(Name, IdLehrer, IdVorlesung, MaxEnrollment, Credits));
        }
    }
}
