package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Notification;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Persistance.UserCredentials;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.LoginAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.SendFirebaseTokenTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.ServiceAsyncTask;

import java.io.Serializable;

public class SplashActivity extends AppCompatActivity implements ServiceAsyncTask.ForeGroundListener
{
    private UserCredentials credentials;

    @Override
    protected void onResume() {
        super.onResume();
        AppModel.getInstance().setVisibility(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppModel.getInstance().setVisibility(false);
    }

    @Override
    public void onCreate(Bundle savedInstance)
    {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstance);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.splash_layout);
        credentials = new UserCredentials(this);
        String mail = credentials.getUserMail();
        String password = credentials.getUserPassword();
        if( mail != null && password != null )
        {
            new LoginAsyncTask(this).execute(mail, password);
        } else
        {
            goToLoginActivity(null, null);
        }
    }

    @Override
    public void onError(ServiceAsyncTask serviceAsyncTask, ServiceResponse.ServiceStatusCode error)
    {
        if( serviceAsyncTask instanceof LoginAsyncTask )
        {
            int message = error ==  ServiceResponse.ServiceStatusCode.NO_CONNECTION ?
                    R.string.connectivityFailed : R.string.failed_login;
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            goToLoginActivity(credentials.getUserMail(), credentials.getUserPassword());
        } else
        {
            goToMainActivity();
        }
    }

    @Override
    public void onSuccess(ServiceAsyncTask serviceAsyncTask, Object data)
    {
        if( serviceAsyncTask instanceof LoginAsyncTask )
        {
            Student student = (Student) data;
            AppModel.getInstance().setStudent(student);
            new SendFirebaseTokenTask(this).execute(student);
        } else
        {
            Intent startingInten = getIntent();
            Log.d("START", "starting intent is null? " + startingInten);
            if( startingInten != null )
            {
                Serializable type = startingInten.getSerializableExtra("type");
                if( type != null && type instanceof Notification.Type)
                {
                    if( type == Notification.Type.UNSUBSCRIBE_EXAM )
                    {
                        goToMyFinalsActivity();
                        return;
                    } else if( type == Notification.Type.UNSUBSCRIBE_COURSE )
                    {
                        goToMyCoursesActivity();
                        return;
                    }
                }
            }
            goToMainActivity();
        }
    }

    @Override
    public void onStartingAsyncTask(ServiceAsyncTask serviceAsyncTask)
    {

    }

    private void goToLoginActivity(String mail, String password)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra("EMAIL", mail);
        intent.putExtra("PASSWORD", password);
        startActivity(intent);
        finish();
    }

    private void goToMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToMyFinalsActivity()
    {
        Intent[] intents = new Intent[]
                {
                        new Intent(this, MainActivity.class),
                        new Intent(this, MyFinalsActivity.class)
                };
        startActivities(intents);
        finish();
    }

    private void goToMyCoursesActivity()
    {
        Intent[] intents = new Intent[]
                {
                    new Intent(this, MainActivity.class),
                    new Intent(this, MyCoursesActivity.class)
                };
        startActivities(intents);
        finish();
    }
}
