package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.MainActivityPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.ServiceAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.SoonToast;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.ToolBarHelper;

public class MainActivity extends AppCompatActivity implements MainActivityPresenter.View
{

    private MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToolBarHelper.onCreate(this);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        ToolBarHelper.setTitle(this, R.string.fiuba_inscripciones);

        presenter = new MainActivityPresenter(this);

        Button academicOfferButton = this.findViewById(R.id.academicOfferButton);
        academicOfferButton.setOnClickListener(new AcademicOfferClickHandler());

        Button newCourseButton = this.findViewById(R.id.newCourseButton);
        newCourseButton.setOnClickListener(new NewCourseClickHandler());

        findViewById(R.id.newFinalExamButton).setOnClickListener(new SoonClickHandler());
        findViewById(R.id.myCoursesButton).setOnClickListener(new SoonClickHandler());
        findViewById(R.id.myExamsButton).setOnClickListener(new SoonClickHandler());
        findViewById(R.id.priorityButton).setOnClickListener(new SoonClickHandler());
        findViewById(R.id.myDataButton).setOnClickListener(new SoonClickHandler());
        presenter.doLogin();
    }

    @Override
    public void navigateToActivity(String path) {
        Intent navigationIntent;

        switch (path) {
            case "academicOffer":
                navigationIntent = new Intent(MainActivity.this, CareersActivity.class);
                startActivity(navigationIntent);
                break;
            case "newCourse":
                navigationIntent = new Intent(MainActivity.this, CareersActivity.class);
                startActivity(navigationIntent);
                break;
        }
    }

    @Override
    public void showToast(int stringId) {
        Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show();
    }

    protected class AcademicOfferClickHandler implements View.OnClickListener {

        public void onClick(View v){
            presenter.navigateTo("academicOffer");
        }
    }

    protected class NewCourseClickHandler implements View.OnClickListener {
        public void onClick(View v){
            presenter.navigateTo("newCourse");
        }
    }

    protected class SoonClickHandler implements View.OnClickListener
    {
        public void onClick(View v){
            SoonToast.show(MainActivity.this);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        SoonToast.show(this);
        return true;
    }
}
