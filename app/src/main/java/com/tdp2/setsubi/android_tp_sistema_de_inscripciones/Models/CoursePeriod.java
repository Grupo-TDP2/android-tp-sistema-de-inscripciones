package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models;

import java.util.Date;

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
    private Date startDate;
    private Date endDate;

    public CoursePeriod(int year, Period period, Date startDate, Date endDate)
    {
        this.period = period;
        this.year = year;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getYear()
    {
        return year;
    }

    public Period getPeriod() {
        return period;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

}
