package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import android.content.Intent;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.LoginActivity;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Persistance.UserCredentials;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.LoginAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.ServiceAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.TextValidator;

public class LoginPresenter implements LoginActivity.Presenter, ServiceAsyncTask.ForeGroundListener
{
    private LoginActivity activity;
    private boolean doingLogin = false;
    private UserCredentials credentials;
    private String mailUsed = null, passwordUsed = null;

    public LoginPresenter(LoginActivity activity)
    {
        this.activity = activity;
        credentials = new UserCredentials(activity);
    }

    @Override
    public void onClickedLogin()
    {
        mailUsed = activity.getUserName();
        passwordUsed = activity.getPassword();
        boolean canLogin = true;
        if( !TextValidator.isEmail(mailUsed) )
        {
            activity.showUserNameEmpty();
            canLogin = false;
        }

        if( passwordUsed.isEmpty() )
        {
            activity.showPasswordempty();
            canLogin = false;
        }

        if( canLogin )
        {
            if( doingLogin )
            {
                activity.showIsLoginIn();
            } else
            {
                doingLogin = true;
                new LoginAsyncTask(this).execute(mailUsed, passwordUsed);
            }
        }
    }

    @Override
    public void initInputs() {
        Intent intent = activity.getIntent();
        if( intent != null )
        {
            String mail = intent.getStringExtra("EMAIL");
            String password = intent.getStringExtra("PASSWORD");
            if( mail != null ) activity.setUserEmail(mail);
            if( password != null ) activity.setUserPassword(password);
        }
    }

    @Override
    public void onError(ServiceAsyncTask serviceAsyncTask, ServiceResponse.ServiceStatusCode error)
    {
        if( serviceAsyncTask instanceof LoginAsyncTask )
        {
            doingLogin = false;
            activity.stopLoading();
            int message = error ==  ServiceResponse.ServiceStatusCode.NO_CONNECTION ?
                    R.string.connectivityFailed : R.string.failed_login;
            activity.showFailedLogin(message);
        }
    }

    @Override
    public void onSuccess(ServiceAsyncTask serviceAsyncTask, Object data)
    {
        if( serviceAsyncTask instanceof LoginAsyncTask )
        {
            doingLogin = false;
            credentials.saveUserCredentials(mailUsed, passwordUsed);
            activity.stopLoading();
            AppModel.getInstance().setStudent((Student)data);
            activity.goToMainScreen();
        }
    }

    @Override
    public void onStartingAsyncTask(ServiceAsyncTask serviceAsyncTask)
    {

        if( serviceAsyncTask instanceof LoginAsyncTask )activity.startLoading();
    }
}
