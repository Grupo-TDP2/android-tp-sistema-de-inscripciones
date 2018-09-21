package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.CursoAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.CursosPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.ToolBarHelper;

public class CursosActivity extends AppCompatActivity
{
    private CursosLogic presenter;

    public interface CursosLogic
    {
        CursoAdapter getAdapter();
        String getCourseName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursos);
        ToolBarHelper.onCreate(this);
        presenter = new CursosPresenter();
        ToolBarHelper.setTitle(this, R.string.cursos);
        TextView title = findViewById(R.id.bottomTitle);
        title.setText(presenter.getCourseName());
        RecyclerView view = findViewById(R.id.cursos_list);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(presenter.getAdapter());
    }

    @Override
    public boolean onSupportNavigateUp() {
        return ToolBarHelper.onClickedHome(this);
    }
}
