package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;

import java.util.Date;

public class ApprovedSubject
{
    private Subject subject;
    private Date approvedDate;
    private int calification;

    public ApprovedSubject(Subject subject, Date date, int calification)
    {
        this.subject = subject;
        this.approvedDate = date;
        this.calification = calification;
    }

    public Subject getSubject() {
        return subject;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public int getCalification() {
        return calification;
    }
}
