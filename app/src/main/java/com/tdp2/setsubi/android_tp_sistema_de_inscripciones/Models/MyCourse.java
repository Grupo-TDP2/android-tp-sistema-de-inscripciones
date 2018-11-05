package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models;

import java.util.List;

public class MyCourse
{
    private Course course;
    private CoursePeriod coursePeriod;
    private Subject subject;
    private Career career;
    private boolean canSeeFinals;

    public MyCourse(Career career, Subject subject, Course course, CoursePeriod period, boolean canSeeFinals)
    {
        this.course = course;
        this.coursePeriod = period;
        this.canSeeFinals = canSeeFinals;
        this.subject = subject;
        this.career = career;
    }

    public int getId() {
        return course.getId();
    }

    public String getCatedra() {
        return course.getCatedra();
    }

    public Sede getSede() {
        return course.getSede();
    }

    public List<CursoTimeBand> getCursoTimeBands() {
        return course.getCursoTimeBands();
    }

    public int getCupos() {
        return course.getCupos();
    }

    public void setCupos(int cupos) {
        this.course.setCupos(cupos);
    }

    public boolean isSubscribed()
    {
        return course.isSubscribed();
    }

    public void setSubscribed(int enrollId)
    {
        course.setSubscribed(enrollId);
    }

    public Integer getEnrollId()
    {
        return course.getEnrollId();
    }

    public boolean isEnabledToEnroll() {
        return course.isEnabledToEnroll();
    }

    public int getYear()
    {
        return coursePeriod.getYear();
    }

    public CoursePeriod.Period getPeriod()
    {
        return coursePeriod.getPeriod();
    }

    public boolean isCanSeeFinals() {
        return canSeeFinals;
    }

    public Subject getSubject() {
        return subject;
    }

    public Course getCourse() {
        return course;
    }

    public Career getCareer() {
        return career;
    }
}
