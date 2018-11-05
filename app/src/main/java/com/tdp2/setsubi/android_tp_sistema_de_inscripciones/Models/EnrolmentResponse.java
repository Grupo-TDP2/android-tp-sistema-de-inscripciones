package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models;

public class EnrolmentResponse
{
    public enum EnrolmentType
    {
        REGULAR,
        CONDITIONAL
    }

    private int courseId;
    private EnrolmentType enrolmentType;
    private int enrolmentId;

    public EnrolmentResponse(int courseId, int enrolmentId, EnrolmentType enrolmentType)
    {
        this.courseId = courseId;
        this.enrolmentId = enrolmentId;
        this.enrolmentType = enrolmentType;
    }

    public EnrolmentType getEnrolmentType() {
        return enrolmentType;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getEnrolmentId() {
        return enrolmentId;
    }
}
