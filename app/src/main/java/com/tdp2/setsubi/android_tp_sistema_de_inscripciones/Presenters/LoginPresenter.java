package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.LoginActivity;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.LoginAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.ServiceAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.TextValidator;

public class LoginPresenter implements LoginActivity.Presenter, ServiceAsyncTask.ForeGroundListener<Student>
{
    private LoginActivity activity;

    public LoginPresenter(LoginActivity activity)
    {
        this.activity = activity;
    }

    @Override
    public void onClickedLogin()
    {
        String username = activity.getUserName();
        String password = activity.getPassword();
        boolean canLogin = true;
        if( !TextValidator.isEmail(username) )
        {
            activity.showUserNameEmpty();
            canLogin = false;
        }

        if( password.isEmpty() )
        {
            activity.showPasswordempty();
            canLogin = false;
        }

        if( canLogin )
        {
            new LoginAsyncTask(this).execute(username, password);
        }
    }

    @Override
    public void onError(ServiceAsyncTask serviceAsyncTask, ServiceResponse.ServiceStatusCode error)
    {
        activity.stopLoading();
        int message = error ==  ServiceResponse.ServiceStatusCode.NO_CONNECTION ?
                R.string.connectivityFailed : R.string.failed_login;
        activity.showFailedLogin(message);
    }

    @Override
    public void onSuccess(ServiceAsyncTask serviceAsyncTask, Object data)
    {
        activity.stopLoading();
        AppModel.getInstance().setStudent((Student)data);
        activity.goToMainScreen();
    }

    @Override
    public void onStartingAsyncTask(ServiceAsyncTask serviceAsyncTask)
    {
        activity.startLoading();
    }
}
