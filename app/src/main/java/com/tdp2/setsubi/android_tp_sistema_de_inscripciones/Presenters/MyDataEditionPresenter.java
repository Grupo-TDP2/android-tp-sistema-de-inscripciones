package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import android.content.Context;
import android.net.Uri;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.LoadingView;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Persistance.UserCredentials;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.EditUserAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.ServiceAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.TextValidator;

public class MyDataEditionPresenter implements ServiceAsyncTask.ForeGroundListener
{
    @Override
    public void onError(ServiceAsyncTask serviceAsyncTask, ServiceResponse.ServiceStatusCode error) {
        view.stopLoading();
        if( error == ServiceResponse.ServiceStatusCode.NO_CONNECTION )
        {
            view.showMessage(R.string.connectivityFailed);
        } else
        {
            view.showMessage(R.string.edition_failed);
        }
    }

    @Override
    public void onSuccess(ServiceAsyncTask serviceAsyncTask, Object data)
    {
        view.stopLoading();
        Student student = AppModel.getInstance().getStudent();
        student.setName(view.getName());
        student.setLastname(view.getSurname());
        student.setEmail(view.getEmail());
        String emailSaved = credentials.getUserMail();
        if( newUri != null )
        {
            credentials.saveImageUri(student.getId(), newUri);
        }
        if( emailSaved != null && !emailSaved.equals(view.getEmail()) )
        {
            credentials.saveUserCredentials(view.getEmail(), credentials.getUserPassword());
        }
        view.showMessage(R.string.succes_edition);
        view.goBack();
    }

    @Override
    public void onStartingAsyncTask(ServiceAsyncTask serviceAsyncTask) {
        view.startLoading();
    }

    public interface View extends LoadingView
    {
        String getName();
        String getSurname();
        String getEmail();
        void setName(String name);
        void setImage(String uriPath);
        void setSurname(String surname);
        void setEmail(String email);
        void showMessage(int stringId);
        void goBack();
        void showEmailError();
        void showSurnameError();
        void showNameError();
        Context getContext();
    }


    private View view;
    private UserCredentials credentials;
    private String newUri = null;

    public MyDataEditionPresenter(View view)
    {
        this.view = view;
        credentials = new UserCredentials(view.getContext());
        Student student = AppModel.getInstance().getStudent();
        view.setEmail(student.getEmail());
        view.setName(student.getName());
        view.setSurname(student.getLastname());
        view.setImage(credentials.getImageUriOf(student.getId()));
    }

    public void onSaveClicked()
    {
        String email = view.getEmail();
        String name = view.getName();
        String lastname = view.getSurname();
        boolean canEdit = true;
        if( name.length() == 0 )
        {
            view.showNameError();
            canEdit = false;
        }
        if( lastname.length() == 0 )
        {
            view.showSurnameError();
            canEdit = false;
        }

        if( !TextValidator.isEmail(email) )
        {
            view.showEmailError();
            canEdit = false;
        }

        if( canEdit )
        {
            Student inModel = AppModel.getInstance().getStudent();
            Student copy = new Student(inModel.getId(), view.getEmail(), view.getName(), view.getSurname(), inModel.getAuthorization());
            new EditUserAsyncTask(this).execute(copy);
        }
    }

    public void onImageChange(String uri)
    {
        newUri = uri;
    }
}
