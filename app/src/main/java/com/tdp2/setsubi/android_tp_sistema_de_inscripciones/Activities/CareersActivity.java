package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.CareersActivityPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;

public class CareersActivity extends AppCompatActivity implements CareersActivityPresenter.View {

    private CareersActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_careers);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_btn);

        presenter = new CareersActivityPresenter(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
