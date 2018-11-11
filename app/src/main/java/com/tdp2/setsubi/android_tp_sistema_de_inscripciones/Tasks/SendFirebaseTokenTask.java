package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Notifications.NotificationServiceAPI;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

public class SendFirebaseTokenTask extends ServiceAsyncTask<Object,Void,Boolean> {
    public SendFirebaseTokenTask(ForeGroundListener listener)
    {
        super(listener);
    }

    @Override
    protected ServiceResponse<Boolean> doRun(Object[] ts)
    {
        String token = FirebaseInstanceId.getInstance().getToken();
        if( ts.length == 1 && ts[0] instanceof Student )
        {
            Log.d("TKN", "" + token);
            if( token == null )
            {
                return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.ERROR);
            }
            return NotificationServiceAPI.getInstance().sendToken((Student)ts[0],token);
        }
        return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.PARAMETER_ERROR);
    }
}
