package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models;

public class Student
{
    private int id;
    private String email;
    private String name;
    private String lastname;
    private String authorization;
    private int priority = -1;
    private String padron = "";

    public Student(int id, String email, String name, String lastname, String authorization)
    {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.authorization = authorization;
        this.email = email;
    }

    public String getEmail() {
        return email;
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

    public String getPadron() {
        return padron;
    }

    public void setPadron(String padron)
    {
        this.padron = padron;
    }

    public int getPriotity() {
        return priority;
    }

    public void setPriority(int priority)
    {
        this.priority = priority;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
