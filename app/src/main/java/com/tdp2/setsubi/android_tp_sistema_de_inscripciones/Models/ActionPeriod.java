package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models;

import java.util.Date;

public class ActionPeriod
{
    private Date start;
    private Date end;

    public ActionPeriod(Date start, Date end)
    {
        this.start = start;
        this.end = end;
    }

    public boolean isDateInPeriod(Date date)
    {
        return !start.after(date) && !end.before(date);
    }
}
