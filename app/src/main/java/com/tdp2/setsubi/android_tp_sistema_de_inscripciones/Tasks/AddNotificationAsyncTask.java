package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks;

import android.content.Context;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Notification;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

public class AddNotificationAsyncTask extends ServiceAsyncTask<Object,Void,Void>
{
    public AddNotificationAsyncTask(ForeGroundListener listener) {
        super(listener);
    }

    @Override
    protected ServiceResponse<Void> doRun(Object[] ts)
    {
        if( ts.length == 2
                && ts[0] instanceof Context
                && ts[1] instanceof Notification)
        {
            return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.SUCCESS, null);
        }
        return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.PARAMETER_ERROR);
    }
}
