package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import android.content.Context;
import android.widget.Toast;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Persistance.UserCredentials;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.LoginAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.ServiceAsyncTask;

public class MainActivityPresenter
{
    private View view;
    private UserCredentials credentials;

    public MainActivityPresenter(View view) {
        this.view = view;
        credentials = new UserCredentials(view.getContext()) ;
    }

    public void navigateTo(String path) {
        view.navigateToActivity(path);
    }

    public void logout() {
        credentials.saveUserCredentials(null, null);
    }

    public interface View
    {
        void navigateToActivity(String path);
        Context getContext();
    }
}
