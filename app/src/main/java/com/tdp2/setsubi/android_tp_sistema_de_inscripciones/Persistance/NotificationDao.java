package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Persistance;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Notification;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface NotificationDao
{
    @Query("SELECT * FROM notification")
    List<Notification> getAll();

    @Insert
    void insert(Notification notification);

    @Query("DELETE FROM notification")
    void deletAll();
}
