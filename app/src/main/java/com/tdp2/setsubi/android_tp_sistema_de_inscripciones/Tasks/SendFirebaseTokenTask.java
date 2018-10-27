package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks;

import com.google.firebase.iid.FirebaseInstanceId;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Notifications.NotifcationServiceAPI;
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
        if( ts.length == 2 && ts[0] instanceof Student )
        {
            if( token == null )
            {
                return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.ERROR);
            }
            return new NotifcationServiceAPI().sendToken((Student)ts[0],(String)ts[1]);
        }
        return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.PARAMETER_ERROR);
    }
}
