package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Notifications.NotificationServiceAPI;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

public class SetSeenNotificationAsyncTask extends ServiceAsyncTask<Object,Void,Void>
{
    public SetSeenNotificationAsyncTask(ForeGroundListener listener) {
        super(listener);
    }

    @Override
    protected ServiceResponse<Void> doRun(Object[] ts)
    {
        if( ts.length == 3
                && ts[0] instanceof Context
                && ts[1] instanceof Integer
                && ts[2] instanceof Boolean)
        {
            NotificationServiceAPI.getInstance().setSeen((Context) ts[0],(Integer) ts[1],(Boolean) ts[2]);
            return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.SUCCESS, null);
        }
        return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.PARAMETER_ERROR);
    }
}
