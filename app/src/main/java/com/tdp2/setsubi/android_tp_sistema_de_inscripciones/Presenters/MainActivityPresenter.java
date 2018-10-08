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

public class MainActivityPresenter implements ServiceAsyncTask.ForeGroundListener<Student>
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

    public void doLogin()
    {
        new LoginAsyncTask(this).execute("leandro.masello@example.com","12345678");
    }

    public interface View {

        void navigateToActivity(String path);
        void showToast(int stringId);
    }

    @Override
    public void onError(ServiceAsyncTask serviceAsyncTask, ServiceResponse.ServiceStatusCode error)
    {
        view.showToast(R.string.failed_login);
    }

    @Override
    public void onSuccess(ServiceAsyncTask serviceAsyncTask, Object data)
    {
        AppModel.getInstance().setStudent((Student) data);
    }

    @Override
    public void onStartingAsyncTask(ServiceAsyncTask serviceAsyncTask) {

    }

}
