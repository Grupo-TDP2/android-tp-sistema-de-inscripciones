package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyCourse
{
    private Course course;
    private CoursePeriod coursePeriod;
    private Subject subject;
    private Career career;
    private boolean alreadyAnsweredPoll = false;

    public boolean isAlreadyAnsweredPoll() {
        return alreadyAnsweredPoll;
    }

    public void setAlreadyAnsweredPoll(boolean alreadyAnsweredPoll) {
        this.alreadyAnsweredPoll = alreadyAnsweredPoll;
    }

    public MyCourse(Career career, Subject subject, Course course, CoursePeriod period)
    {
        this.course = course;
        this.coursePeriod = period;
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

    public Subject getSubject() {
        return subject;
    }

    public Course getCourse() {
        return course;
    }

    public Career getCareer() {
        return career;
    }

    public boolean inUnsubscribePeriod() {
        return inFirstWeek(coursePeriod.getStartDate());
    }

    private boolean inFirstWeek(Date startDate)
    {
        Calendar c=Calendar.getInstance();
        c.setTime(new Date());
        int year1=c.get(Calendar.YEAR);
        int week1=c.get(Calendar.WEEK_OF_YEAR);

        Calendar c2  =Calendar.getInstance();
        c2.setTimeInMillis(startDate.getTime());
        int year2=c2.get(Calendar.YEAR);
        int week2=c2.get(Calendar.WEEK_OF_YEAR);

        return year1 == year2 && week1 == week2;
    }
}
