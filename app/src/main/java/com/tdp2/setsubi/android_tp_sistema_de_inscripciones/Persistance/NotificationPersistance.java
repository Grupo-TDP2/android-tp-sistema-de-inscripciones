package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Persistance;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Notification;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Persistance.NotificationConvertes.Converter;


@Database(entities = {Notification.class}, version = 1, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class NotificationPersistance extends RoomDatabase
{
    public abstract NotificationDao userDao();
}
