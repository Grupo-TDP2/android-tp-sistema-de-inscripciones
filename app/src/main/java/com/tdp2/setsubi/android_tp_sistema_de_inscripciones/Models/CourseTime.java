package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models;

public class CourseTime
{
    public CourseTime(int hours, int minutes) {
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

    @Override
    public boolean equals(Object object)
    {
        if( object == null || (object instanceof  CourseTime) ) return false;
        CourseTime other = (CourseTime) object;
        return other.hours == hours && other.minutes == minutes;
    }
}
