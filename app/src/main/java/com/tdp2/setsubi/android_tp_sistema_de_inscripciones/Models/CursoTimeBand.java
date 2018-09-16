package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.DayOfWeek;

public class CursoTimeBand
{
    public enum CursoTimeType
    {
        TEORICO,
        PRACTIO,
        TEORICO_PRACTICO
    }

    private DayOfWeek dayOfWeek;
    private CursoTime start;
    private CursoTime end;
    private CursoTimeType type;
    private boolean obligatory;
    private int aula;

    public CursoTimeBand(DayOfWeek dayOfWeek, int aula, CursoTime start, CursoTime end, CursoTimeType type, boolean obligatory) {
        this.dayOfWeek = dayOfWeek;
        this.end = end;
        this.start = start;
        this.aula = aula;
        this.type = type;
        this.obligatory = obligatory;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public CursoTime getStarTime() {
        return start;
    }

    public CursoTime getEndTime() {
        return end;
    }

    public CursoTimeType getType() {
        return type;
    }

    public boolean isObligatory() {
        return obligatory;
    }

    public int getAula() {
        return aula;
    }
}
