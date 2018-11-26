package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models;

import java.util.ArrayList;
import java.util.List;

public class Course
{
    public enum Status
    {
        APPROVED,
        DISAPPROVED,
        NOT_EVALUATED
    }
    private int id;
    private String catedra;
    private Sede sede;
    private List<CursoTimeBand> cursoTimeBands;
    private Integer enrollId;
    private boolean enabledToEnroll;
    private int cupos;
    private Status status = Status.NOT_EVALUATED;

    public Course(int id, String catedra, Sede sede, List<CursoTimeBand> schedules,
                  int cupo, Integer enrollId, boolean enabledToEnroll)
    {
        this.id = id;
        this.catedra = catedra;
        this.sede = sede;
        this.cursoTimeBands = schedules;
        this.cupos = cupo;
        this.enrollId = enrollId;
        this.enabledToEnroll = enabledToEnroll;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
        return enrollId != null;
    }

    public void setSubscribed(Integer enrollId)
    {
        this.enrollId = enrollId;
    }

    public boolean isEnabledToEnroll() {
        return enabledToEnroll;
    }

    public Integer getEnrollId()
    {
        return enrollId;
    }
}
