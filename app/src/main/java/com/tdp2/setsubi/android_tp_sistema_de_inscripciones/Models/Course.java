package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models;

import java.util.ArrayList;
import java.util.List;

public class Course
{
    private int id;
    private String catedra;
    private Sede sede;
    private List<CursoTimeBand> cursoTimeBands;
    private boolean isSubscribed;
    private int cupos;

    public Course(int id, String catedra, Sede sede, List<CursoTimeBand> schedules, int cupo, boolean isSubscribed)
    {
        this.id = id;
        this.catedra = catedra;
        this.sede = sede;
        this.cursoTimeBands = schedules;
        this.cupos = cupo;
        this.isSubscribed = isSubscribed;
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

    public boolean isSubscribed()
    {
        return isSubscribed;
    }

    public void setSubscribed(boolean isSubscribed)
    {
        this.isSubscribed = isSubscribed;
    }
}
