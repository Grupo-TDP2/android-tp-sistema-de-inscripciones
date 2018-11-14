package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AndroidServices;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Notifications.NotificationServiceAPI;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.ServiceAsyncTask;

public class MyFirebaseInstanceId extends FirebaseInstanceIdService implements ServiceAsyncTask.ForeGroundListener {


    @Override
    public void onTokenRefresh()
    {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        if(AppModel.getInstance().getStudent() != null)
        {
            ServiceResponse<Boolean> token = NotificationServiceAPI.getInstance()
                    .sendToken(AppModel.getInstance().getStudent(),refreshedToken);
            if( token.getStatusCode() == ServiceResponse.ServiceStatusCode.SUCCESS)
            {
                Log.d("FCM", "Changed token of user sucessfuly");
            } else
            {
                Log.d("FCM", "Changed token of user unsuccessfuly: " + token.getStatusCode());
            }
        }
    }

    @Override
    public void onError(ServiceAsyncTask serviceAsyncTask, ServiceResponse.ServiceStatusCode error) {

    }

    @Override
    public void onSuccess(ServiceAsyncTask serviceAsyncTask, Object data) {

    }

    @Override
    public void onStartingAsyncTask(ServiceAsyncTask serviceAsyncTask) {

    }
}
