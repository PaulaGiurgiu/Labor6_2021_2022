package com.uni.labor6_2021_2022_javafx.model;

import java.util.ArrayList;
import java.util.List;

public class Vorlesung {
    private String name;
    private long lehrerID;
    private long vorlesungID;
    private int maxEnrollment;
    private List<Long> studentsEnrolled;
    private int credits;

    /**
     * wir erstellen ein neues Objekt von Typ Vorlesung
     * @param name ein String
     * @param lehrerID eine "Long"-Zahl
     * @param vorlesungID eine "Long"-Zahl
     * @param maxEnrollment eine Zahl
     * @param credits eine Zahl
     */
    public Vorlesung(String name, long lehrerID, long vorlesungID, int maxEnrollment, int credits)
    {
        this.name = name;
        this.lehrerID = lehrerID;
        this.vorlesungID = vorlesungID;
        this.maxEnrollment = maxEnrollment;
        this.studentsEnrolled = new ArrayList<>();
        this.credits = credits;
    }

    public Vorlesung(String name, long lehrerID, long vorlesungID, int maxEnrollment, List<Long> studentsEnrolled, int credits)
    {
        this.name = name;
        this.lehrerID = lehrerID;
        this.vorlesungID = vorlesungID;
        this.maxEnrollment = maxEnrollment;
        this.studentsEnrolled = studentsEnrolled;
        this.credits = credits;
    }


    public Vorlesung(String name, long vorlesungID, int credits){
        this.name = name;
        this.vorlesungID = vorlesungID;
        this.credits = credits;
    }

    @Override
    public String toString() {
        return "Vorlesung{" +
                "name='" + name + '\'' +
                ", lehrer=" + lehrerID +
                ", vorlesungID=" + vorlesungID +
                ", maxEnrollment=" + maxEnrollment +
                ", studentsEnrolled=" + studentsEnrolled +
                ", credits=" + credits +
                '}';
    }

    /* Getters */
    public String getName() {
        return name;
    }

    public long getLehrer() {
        return lehrerID;
    }

    public long getVorlesungID() { return vorlesungID; }

    public int getMaxEnrollment() {
        return maxEnrollment;
    }

    public List<Long> getStudentsEnrolled() {
        return studentsEnrolled;
    }

    public int getCredits() {
        return credits;
    }

    /* Setters */
    public void setName(String name) {
        this.name = name;
    }

    public void setLehrer(long lehrerID) {
        this.lehrerID = lehrerID;
    }

    public void setVorlesungID(long vorlesungID) { this.vorlesungID = vorlesungID; }

    public void setMaxEnrollment(int maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
    }

    public void setStudentsEnrolled(List<Long> studentsEnrolled) {
        this.studentsEnrolled = studentsEnrolled;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}
