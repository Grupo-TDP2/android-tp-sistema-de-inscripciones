package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.Adapters.CursoAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.Presenters.CursosPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;

public class CursosActivity extends AppCompatActivity
{
    private CursosLogic presenter;

    public interface CursosLogic
    {
        CursoAdapter getAdapter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursos);
        setTitle("75.17 Analisis Numerico");
        presenter = new CursosPresenter();
        RecyclerView view = findViewById(R.id.cursos_list);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(presenter.getAdapter());
    }
}
