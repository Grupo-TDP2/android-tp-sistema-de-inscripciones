package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks;

import android.content.Context;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Notification;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Notifications.NotificationServiceAPI;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

import java.util.List;

public class GetNotificationsAsyncTask extends ServiceAsyncTask<Context,Void,List<Notification>> {

    public GetNotificationsAsyncTask(ForeGroundListener listener)
    {
        super(listener);
    }

    @Override
    protected ServiceResponse<List<Notification>> doRun(Context[] ts)
    {
        if( ts.length != 1 ) return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.PARAMETER_ERROR);
        return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.SUCCESS,
                NotificationServiceAPI.getInstance().getAllNotifications(ts[0]));
    }
}
