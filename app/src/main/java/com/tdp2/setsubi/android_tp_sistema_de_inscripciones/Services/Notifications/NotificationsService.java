package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Notifications;

import android.content.Context;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Notification;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

import java.util.List;

public interface NotificationsService
{
    public interface NotificationListener
    {
        void onNewNotification(List<Notification> notifications);
    }

    ServiceResponse<Boolean> sendToken(Student student, String token);
    void onReceivedNotification(Context context, Notification notification);
    List<Notification> getAllNotifications(Context context);
    void setSeen(Context context, int id, boolean seen);
}
