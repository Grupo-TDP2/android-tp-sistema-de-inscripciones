package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Final
{
    private int id;
    private int subscriptionId = -1;
    private boolean freeSubscription = false;
    private boolean supportsLibre;
    private boolean approvedCourseOfFinal;
    private boolean finalGiven = false;
    private Integer finalCalification = null;
    private String catedraName;
    private Date finalDate;
    private Sede sede;
    private int aula;

    public Subject getSubject() {
        return subject;
    }

    private Subject subject;

    public Final(int id, boolean supportsLibre, boolean approvedCourseOfFinal,
                 Subject subject, String catedraName, Date date, Sede sede, int aula)
    {
        this.id = id;
        this.catedraName = catedraName;
        this.finalDate = date;
        this.sede = sede;
        this.aula = aula;
        this.subject = subject;
        this.supportsLibre = supportsLibre;
        this.approvedCourseOfFinal = approvedCourseOfFinal;
    }

    public void setFinalGiven(boolean given, Integer finalCalification)
    {
        this.finalGiven = given;
        this.finalCalification = finalCalification;
    }

    public boolean isFinalGiven()
    {
        return finalGiven;
    }

    public boolean passedFinal()
    {
        return isFinalGiven() && finalCalification != null;
    }

    public Integer getFinalCalification()
    {
        return finalCalification;
    }

    public boolean isCanSubscribe()
    {
        return !isSubscribed() && isAtTimeToSubscribe()
                && (isSupportsLibre() || isApprovedCourseOfFinal());
    }

    public boolean isCanUnsubscribe() {
        return isSubscribed() && isAtTimeToSubscribe();
    }

    private boolean isAtTimeToSubscribe()
    {
        Date currentTime = Calendar.getInstance().getTime();
        long secs = (finalDate.getTime() - currentTime.getTime()) / 1000;
        int hours = (int) (secs / 3600);
        return hours >= 48;
    }

    public int getId() {
        return id;
    }

    public boolean isSubscribed() {
        return subscriptionId != -1;
    }


    public String getCatedraName() {
        return catedraName;
    }

    public boolean isSupportsLibre() {
        return supportsLibre;
    }

    public String getDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(finalDate);
    }

    public Date getFinalDate()
    {
        return finalDate;
    }

    public Sede getSede() {
        return sede;
    }

    public int getAula() {
        return aula;
    }

    public String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return sdf.format(finalDate);
    }

    public int compare(Final fina)
    {
        return finalDate.compareTo(fina.finalDate);
    }

    public void setSubscription(Integer id, boolean freeSubscription) {
        this.subscriptionId = id;
        this.freeSubscription = freeSubscription;
    }

    public boolean isFreeSubscription()
    {
        return freeSubscription;
    }

    public int getSubscriptionId(){
        return subscriptionId;
    }

    public boolean isApprovedCourseOfFinal() {
        return approvedCourseOfFinal;
    }

    public boolean hasAlreadyPassedDate()
    {
        Date currentTime = Calendar.getInstance().getTime();
        return finalDate.getTime() - currentTime.getTime() <= 0;
    }

    public void unsubscribe() {
        subscriptionId = -1;
    }
}
