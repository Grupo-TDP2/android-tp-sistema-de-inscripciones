package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.CursoAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.CursosPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;

public class CursosActivity extends AppCompatActivity implements LoadingView
{
    private CursosLogic presenter;
    private Snackbar snackbar;

    public interface CursosLogic
    {
        CursoAdapter getAdapter();
        String getCourseName();
        void loadData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursos);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_btn);

        presenter = new CursosPresenter(this);
        TextView title = findViewById(R.id.toolbar_title);
        title.setText(presenter.getCourseName());
        RecyclerView view = findViewById(R.id.cursos_list);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(presenter.getAdapter());
        presenter.loadData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onFailedtoLoadCursos() {
        Toast.makeText(this, R.string.connectivityFailed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startLoading() {
        stopLoading();
        snackbar = Snackbar.make(findViewById(R.id.cursos_list), R.string.loading, Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

    @Override
    public void stopLoading() {
        if( snackbar != null )
        {
            snackbar.dismiss();
            snackbar = null;
        }
    }
}
