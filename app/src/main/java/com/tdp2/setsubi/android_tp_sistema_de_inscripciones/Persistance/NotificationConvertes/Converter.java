package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Persistance.NotificationConvertes;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Notification;

import java.util.Date;

import androidx.room.TypeConverter;

public class Converter
{
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Notification.Type fromInt(int value) {
        switch(value){
            case 0:
                return Notification.Type.UNSUBSCRIBE_COURSE;
            default:
                return Notification.Type.UNSUBSCRIBE_EXAM;
        }
    }

    @TypeConverter
    public static int typeToInt(Notification.Type type) {
       switch (type)
       {
           case UNSUBSCRIBE_COURSE:
               return 0;
           default:
                   return 1;
       }
    }
}
