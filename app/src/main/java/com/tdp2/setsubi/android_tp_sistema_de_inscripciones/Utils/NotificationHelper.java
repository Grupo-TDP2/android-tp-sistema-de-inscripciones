package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Notification;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Notifications.NotificationServiceAPI;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Notifications.NotificationsService;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.GetNotificationsAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.ServiceAsyncTask;

import java.util.List;

public class NotificationHelper implements ServiceAsyncTask.ForeGroundListener, NotificationsService.NotificationListener
{
    private ImageView view;

    public NotificationHelper(ImageView notification)
    {
        this.view = notification;
        new GetNotificationsAsyncTask(this).execute(view.getContext());
        NotificationServiceAPI.getInstance().addListener(this);
    }

    public void destroy()
    {
        NotificationServiceAPI.getInstance().removeListener(this);
        view = null;
    }

    @Override
    public void onError(ServiceAsyncTask serviceAsyncTask, ServiceResponse.ServiceStatusCode error) {

    }

    @Override
    public void onSuccess(ServiceAsyncTask serviceAsyncTask, Object data) {
        List<Notification> notificationList = (List<Notification>) data;
        for( Notification notification : notificationList )
        {
            if( !notification.isSeen() )
            {
                showNewNotifications();
                return;
            }
        }
        showNoNewNotifications();
    }

    private void showNoNewNotifications()
    {
        setNotificationColor(R.color.textPrimary);
    }

    private void showNewNotifications()
    {
       setNotificationColor(R.color.actionButtonColor);
    }

    private void setNotificationColor(int color)
    {
        if( view != null )
        {
            view.setColorFilter(ContextCompat.getColor(view.getContext(), color));
        }
    }

    @Override
    public void onStartingAsyncTask(ServiceAsyncTask serviceAsyncTask) {
        setNotificationColor(R.color.textPrimary);
    }

    @Override
    public void onNewNotification(List<Notification> notifications) {
        showNewNotifications();
    }
}
