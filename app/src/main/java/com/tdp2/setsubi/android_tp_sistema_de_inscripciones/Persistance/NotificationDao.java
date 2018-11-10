package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Persistance;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Notification;

import java.util.List;



@Dao
public interface NotificationDao
{
    @Query("SELECT * FROM notification")
    List<Notification> getAll();

    @Insert
    void insert(Notification notification);

    @Query("DELETE FROM notification")
    void deletAll();

    @Query("UPDATE notification SET seen = :seen WHERE uid = :uid")
    void setSeen(int uid, boolean seen);
}
