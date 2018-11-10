package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Notifications;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Notification;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Persistance.NotificationPersistance;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.VoidJsonTransformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.APIUriBuilder;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.ContentType;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestBuilder;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestMethod;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestPerformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestProperty;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

import java.util.ArrayList;
import java.util.List;

import androidx.room.Room;

public class NotifcationServiceAPI implements NotificationsService
{
    private static NotifcationServiceAPI instance = null;
    public static NotifcationServiceAPI getInstance()
    {
        if( instance == null )
        {
            instance = new NotifcationServiceAPI();
        }
        return instance;
    }

    private List<NotificationsService.NotificationListener> listeners = new ArrayList<>();
    private NotifcationServiceAPI(){}
    private NotificationPersistance.AppDatabase database = null;
    private NotificationPersistance.AppDatabase getDatabase(Context context)
    {
        if( database == null )
        {
            database = Room.databaseBuilder(context,
                    NotificationPersistance.AppDatabase.class, "notification-db").build();
        }
        return database;
    }
    @Override
    public ServiceResponse<Boolean> sendToken(Student student, String token)
    {
        JsonObject jsonToken = new JsonObject();
        jsonToken.addProperty("device_token", token);
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("student", jsonToken);
        return new RequestPerformer<>(APIUriBuilder.getURIBuiled()
                .appendPath("students")
                .appendPath("me").toString(), new RequestBuilder(RequestMethod.PATCH)
                .addRequestProperty(RequestProperty.AUTHORIZATION, student.getAuthorization())
                .addRequestProperty(RequestProperty.CONTENT_TYPE, ContentType.JSON.getValue())
                .setBodyText(new Gson().toJson(jsonObject)),
                new VoidJsonTransformer()).perform();
    }

    @Override
    public void onReceivedNotification(Context context, Notification notification)
    {
        NotificationPersistance.AppDatabase database = getDatabase(context);
        database.userDao().insert(notification);
        notifyAllNewNotification(database.userDao().getAll());
    }

    @Override
    public List<Notification> getAllNotifications() {
        return  database.userDao().getAll();
    }

    public void addListener(NotificationListener listener)
    {
        listeners.add(listener);
    }

    public void removeListener(NotificationListener listener)
    {
        listeners.remove(listener);
    }

    private void notifyAllNewNotification(final List<Notification> notifications)
    {
        for( final NotificationListener listener : listeners)
        {
            new Handler(Looper.getMainLooper()).post(() -> listener.onNewNotification(notifications));
        }
    }
}
