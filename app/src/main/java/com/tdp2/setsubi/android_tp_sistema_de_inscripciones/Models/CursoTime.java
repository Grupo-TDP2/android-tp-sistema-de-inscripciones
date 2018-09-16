package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models;

public class CursoTime
{
    public CursoTime(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    private int hours;
    private int minutes;
}
