package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.MainActivityPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;

public class MainActivity extends AppCompatActivity implements MainActivityPresenter.View {

    private MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        presenter = new MainActivityPresenter(this);

        Button academicOfferButton = this.findViewById(R.id.academicOfferButton);
        academicOfferButton.setOnClickListener(new AcademicOfferClickHandler());

        Button newCourseButton = this.findViewById(R.id.newCourseButton);
        newCourseButton.setOnClickListener(new NewCourseClickHandler());

    }

    @Override
    public void navigateToActivity(String path) {
        Intent navigationIntent;

        switch (path) {
            case "academicOffer":
                navigationIntent = new Intent(MainActivity.this, CareersActivity.class);
                startActivity(navigationIntent);
            case "newCourse":
                navigationIntent = new Intent(MainActivity.this, CareersActivity.class);
                startActivity(navigationIntent);
        }
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
}
