package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import android.content.Context;
import android.net.Uri;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Persistance.UserCredentials;

public class MyDataPresenter
{
    public interface View
    {
        void setUserImage(String uriPath);
        void setName(String name, String surname);
        void setPadron(String padron);
        void setPriority(int priority);
        void setEmail(String email);
        Context getContext();
    }

    private View view;
    private UserCredentials credentials;
    public MyDataPresenter(View view)
    {
        this.view = view;
        credentials = new UserCredentials(view.getContext());
    }

    public void loadData()
    {
        Student student = AppModel.getInstance().getStudent();
        view.setName(student.getName(),student.getLastname());
        view.setPadron(student.getPadron());
        view.setPriority(student.getPriotity());
        view.setEmail(student.getEmail());
        view.setUserImage(credentials.getImageUriOf(student.getId()));
    }
}
