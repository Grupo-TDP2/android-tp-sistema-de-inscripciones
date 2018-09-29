package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models;

public class Student
{
    private int id;
    private String name;
    private String lastname;
    private String authorization;
     /*String email;
    String personal_document_number;
    String school_document_number;
    String birthdate, phone_number, address;*/

    public Student(int id, String name, String lastname) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAuthorization() {
        return authorization;
    }
}
