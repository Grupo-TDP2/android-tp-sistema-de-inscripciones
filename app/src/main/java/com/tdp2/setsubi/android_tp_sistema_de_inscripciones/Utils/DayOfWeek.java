package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils;

public enum  DayOfWeek
{
    MONDAY(0),
    TUESDAY(1),
    WEDNESDAY(2),
    THURSDAY(3),
    FRIDAY(4),
    SATURDAY(5),
    SUNDAY(6);

    int day;
    DayOfWeek(int day){
        this.day = day;
    }

    public int getValue()
    {
        return day;
    }

    public static DayOfWeek getDayFrom(int day)
    {
        for( DayOfWeek dayOfWeek : DayOfWeek.values() )
        {
            if( dayOfWeek.getValue() == day )
            {
                return dayOfWeek;
            }
        }
        return null;
    }
}
