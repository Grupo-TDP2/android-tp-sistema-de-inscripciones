package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models;

public class ClassModel
{
    private int id;
    private int code;
    private int department;
    private String name;

    public ClassModel(int id, int code, int department, String name)
    {
        this.id = id;
        this.code = code;
        this.department = department;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getCode() {
        return code;
    }

    public int getDepartment() {
        return department;
    }

    public String getName() {
        return name;
    }
}
