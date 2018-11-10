package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Persistance;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Notification;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Persistance.NotificationConvertes.Converter;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

public class NotificationPersistance
{
    @Database(entities = {Notification.class}, version = 1)
    @TypeConverters({Converter.class})
    public abstract class AppDatabase extends RoomDatabase
    {
        public abstract NotificationDao userDao();
    }
}
