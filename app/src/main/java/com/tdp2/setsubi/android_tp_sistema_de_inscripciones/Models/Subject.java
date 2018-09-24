package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models;

public class Subject
{
    private int id;
    private int code;
    private Department department;
    private String name;
    private int credits;

    public Subject(int id, int code, String name, int credits, Department department)
    {
        this.id = id;
        this.code = code;
        this.department = department;
        this.name = name;
        this.credits = credits;
    }

    public Department getDepartment()
    {
        return department;
    }

    public int getId() {
        return id;
    }

    public int getCode() {
        return code;
    }

    public int getDepartmentCode() {
        return department.getCode();
    }

    public String getName() {
        return name;
    }

    public int getCredits()
    {
        return credits;
    }
}
