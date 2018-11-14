package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AndroidServices;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.SplashActivity;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Notification;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Notifications.NotificationServiceAPI;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.AnyNullCheck;

import java.util.Date;
import java.util.Map;

public class MyFirebaseMessageService extends FirebaseMessagingService
{
    private final String titleKey = "title", bodyKey = "body", typeKey = "type";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        if (remoteMessage.getData().size() > 0)
        {
            Map<String,String> data = remoteMessage.getData();
            String title = data.get(titleKey);
            String body = data.get(bodyKey);
            String typeKey = data.get(this.typeKey);
            Notification.Type type = getType(typeKey);
            if(!AnyNullCheck.any(title,body,type))
            {
                NotificationServiceAPI.getInstance().onReceivedNotification(this.getApplicationContext(),
                        new Notification(0,title,body,type, new Date(), false));
            }
            if(!AnyNullCheck.any(title, body) && !AppModel.getInstance().isAppVisible())
            {
                showNotification(title, body, type);
            }
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void showNotification(String title, String body, Notification.Type type) {
        int icon = R.drawable.ic_edit_white_140dp;
        if( type != null )
        {
            switch (type)
            {
                case UNSUBSCRIBE_COURSE:
                    icon = R.drawable.ic_date_range_white_24dp;
                    break;
                case UNSUBSCRIBE_EXAM:
                    icon = R.drawable.ic_exam;
            }
        }
        Intent intent = new Intent(this, SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(typeKey, type);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(icon)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setColor(ContextCompat.getColor(this,R.color.colorAccent))
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Notificacion de eventos del periodo de cursada",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        Log.d("NOTIFICATION", "Notifying notification");
        notificationManager.notify(0, notificationBuilder.build());
    }

    private Notification.Type getType(String typeKey) {
        if( typeKey == null ) return null;
        switch (typeKey)
        {
            case "unsubscribe_exam":
                return Notification.Type.UNSUBSCRIBE_EXAM;
            case "unsubscribe_course":
                return Notification.Type.UNSUBSCRIBE_COURSE;
        }
        return null;
    }
}
