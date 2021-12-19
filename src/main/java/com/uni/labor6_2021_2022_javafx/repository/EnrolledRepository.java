package com.uni.labor6_2021_2022_javafx.repository;

import com.uni.labor6_2021_2022_javafx.model.*;
import com.uni.labor6_2021_2022_javafx.repository.*;
import java.util.List;

public class EnrolledRepository {
    private VorlesungRepository vorlesungRepository;
    private StudentRepository studentRepository;

    public EnrolledRepository(VorlesungRepository vorlesungRepository, StudentRepository studentRepository) {
        this.vorlesungRepository = vorlesungRepository;
        this.studentRepository = studentRepository;
    }

    /**
     *
     * @param vorlesungID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @param studentID eine "Long" Zahl, die ein "Student" Id entspricht
     * @return "true", falls der Student an der Vorlesung teilnimmt, "false" sonst
     */
    public boolean findOne(Long vorlesungID, Long studentID){
        Long existRegister = this.vorlesungRepository.findOne(vorlesungID).getStudentsEnrolled()
                .stream()
                .filter(id -> id.equals(studentID))
                .findFirst()
                .orElse(null);

        if (existRegister == null)
            return false;
        else
            return true;
    }

    /**
     * //wir registrieren der Student an der Vorlesung
     * @param vorlesungID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @param studentID eine "Long" Zahl, die ein "Student" Id entspricht
     */
    public void save(Long vorlesungID, Long studentID){
        List<Long> studentList = this.vorlesungRepository.findOne(vorlesungID).getStudentsEnrolled();
        studentList.add(studentID);
        Vorlesung vorlesung = new Vorlesung(this.vorlesungRepository.findOne(vorlesungID).getName(),
                this.vorlesungRepository.findOne(vorlesungID).getLehrer(),
                this.vorlesungRepository.findOne(vorlesungID).getVorlesungID(),
                this.vorlesungRepository.findOne(vorlesungID).getMaxEnrollment(),
                studentList,
                this.vorlesungRepository.findOne(vorlesungID).getCredits());

        this.vorlesungRepository.update(vorlesung);

        List<Long> vorlesungList = this.studentRepository.findOne(studentID).getEnrolledCourses();
        vorlesungList.add(vorlesungID);
        Student student = new Student(new Person(this.studentRepository.findOne(studentID).getPersonID(),
                this.studentRepository.findOne(studentID).getVorname(),
                this.studentRepository.findOne(studentID).getNachname()),
                this.studentRepository.findOne(studentID).getStudentID(),
                this.studentRepository.findOne(studentID).getTotalCredits() + this.vorlesungRepository.findOne(vorlesungID).getCredits(),
                vorlesungList);

        this.studentRepository.update(student);
    }

    /**
     * //wir löschen der Student aus der Vorlesung
     * @param vorlesungID eine "Long" Zahl, die ein "Vorlesung" Id entspricht
     * @param studentID eine "Long" Zahl, die ein "Student" Id entspricht
     */
    public void delete(Long vorlesungID, Long studentID){
        List<Long> studentList = this.vorlesungRepository.findOne(vorlesungID).getStudentsEnrolled();
        studentList.remove(studentID);

        Vorlesung vorlesung = new Vorlesung(this.vorlesungRepository.findOne(vorlesungID).getName(),
                this.vorlesungRepository.findOne(vorlesungID).getLehrer(),
                this.vorlesungRepository.findOne(vorlesungID).getVorlesungID(),
                this.vorlesungRepository.findOne(vorlesungID).getMaxEnrollment(),
                studentList,
                this.vorlesungRepository.findOne(vorlesungID).getCredits());

        this.vorlesungRepository.update(vorlesung);

        List<Long> vorlesungList = this.studentRepository.findOne(studentID).getEnrolledCourses();
        vorlesungList.remove(vorlesungID);

        Student student = new Student(new Person(this.studentRepository.findOne(studentID).getPersonID(),
                this.studentRepository.findOne(studentID).getVorname(),
                this.studentRepository.findOne(studentID).getNachname()),
                this.studentRepository.findOne(studentID).getStudentID(),
                this.studentRepository.findOne(studentID).getTotalCredits() - this.vorlesungRepository.findOne(vorlesungID).getCredits(),
                vorlesungList);

        this.studentRepository.update(student);
    }
}
