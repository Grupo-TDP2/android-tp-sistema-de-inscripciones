package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import android.content.Context;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Persistance.UserCredentials;


public class MainActivityPresenter
{
    private View view;
    private UserCredentials credentials;

    public MainActivityPresenter(View view) {
        this.view = view;
        credentials = new UserCredentials(view.getContext()) ;
    }

    public void navigateTo(String path)
    {
        switch (path) {
            case "academicOffer": case "newCourse":
                AppModel.getInstance().setRoute(AppModel.SubjectRoute.COURSES);
                break;
            case "finals":
                AppModel.getInstance().setRoute(AppModel.SubjectRoute.FINALS_SUBSCRIPTION);
                break;
        }
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
