package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import android.content.Intent;
import android.widget.Toast;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.CareersActivity;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.MainActivity;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.MyFinalsActivity;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.LoginAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.ServiceAsyncTask;

public class MainActivityPresenter
{
    private View view;

    public MainActivityPresenter(View view) {
        this.view = view;
    }

    public void navigateTo(String path)
    {
        switch (path) {
            case "academicOffer": case "newCourse":
                AppModel.getInstance().setRoute(AppModel.SubjectRoute.COURSES);
                break;
            case "finals":
                AppModel.getInstance().setRoute(AppModel.SubjectRoute.FINALS);
                break;
        }
        view.navigateToActivity(path);
    }

    public interface View
    {
        void navigateToActivity(String path);
    }
}
