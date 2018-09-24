package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models;

public class Department
{
    private int id;
    private String name;
    private int code;

    public Department(int id, String name, int code)
    {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCode(){return code;}

    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Department))return false;
        Department otherMyClass = (Department)other;
        return otherMyClass.getId() == this.getId();
    }
}
