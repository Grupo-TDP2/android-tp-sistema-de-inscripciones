package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models;

import java.util.ArrayList;
import java.util.List;

public class Course
{
    private int id;
    private String catedra;
    private Sede sede;
    private List<CursoTimeBand> cursoTimeBands;
    private List<Integer> subscribedStudents;
    private int cupos;

    public Course(int id, String catedra, Sede sede, List<CursoTimeBand> schedules, int cupo)
    {
        this.id = id;
        this.catedra = catedra;
        this.sede = sede;
        this.cursoTimeBands = schedules;
        this.cupos = cupo;
        this.subscribedStudents = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getCatedra() {
        return catedra;
    }

    public Sede getSede() {
        return sede;
    }

    public List<CursoTimeBand> getCursoTimeBands() {
        return cursoTimeBands;
    }

    public int getCupos() {
        return cupos;
    }

    public void setCupos(int cupos) {
        this.cupos = cupos;
    }

    public boolean isSubscribed(int student)
    {
        return subscribedStudents.contains(student);
    }

    public void addSubscribed(int id)
    {
        if( !subscribedStudents.contains(id) )
        {
            subscribedStudents.add(id);
        }
    }
}
