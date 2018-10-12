package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models;

public class Final
{
    private int id;
    private boolean isSubscribed;
    private boolean canSubscribe;
    private boolean canUnsubscribe;
    private String catedraName;
    private String finalDate;
    private Sede sede;
    private int aula;
    private String time;
    private boolean regular;

    public Subject getSubject() {
        return subject;
    }

    private Subject subject;

    public Final(int id, boolean isSubscribed, boolean canSubscribe, boolean canUnsubscribe, boolean regular,
                 Subject subject, String catedraName, String finalDate,  String time, Sede sede, int aula)
    {
        this.id = id;
        this.isSubscribed = isSubscribed;
        this.catedraName = catedraName;
        this.finalDate = finalDate;
        this.sede = sede;
        this.aula = aula;
        this.time = time;
        this.canSubscribe = canSubscribe;
        this.canUnsubscribe = canUnsubscribe;
        this.subject = subject;
        this.regular = regular;
    }

    public boolean isCanSubscribe() {
        return canSubscribe;
    }

    public boolean isCanUnsubscribe() {
        return canUnsubscribe;
    }

    public int getId() {
        return id;
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public String getCatedraName() {
        return catedraName;
    }

    public String getFinalDate() {
        return finalDate;
    }

    public Sede getSede() {
        return sede;
    }

    public int getAula() {
        return aula;
    }

    public String getTime() {
        return time;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }

    public boolean isRegular() {
        return regular;
    }
}
