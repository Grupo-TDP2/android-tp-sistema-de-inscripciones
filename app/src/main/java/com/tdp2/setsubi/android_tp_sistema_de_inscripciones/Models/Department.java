package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models;

public class Department
{
    private int id;
    private String name;

    public Department(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
