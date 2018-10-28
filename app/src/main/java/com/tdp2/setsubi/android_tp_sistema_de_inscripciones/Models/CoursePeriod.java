package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models;

public class CoursePeriod
{
    public enum Period
    {
        FIRST,
        SECOND,
        SUMMER
    }
    private int year;
    private Period period;
    public CoursePeriod(int year, Period period)
    {
        this.period = period;
        this.year = year;
    }

    public int getYear()
    {
        return year;
    }

    public Period getPeriod() {
        return period;
    }
}
