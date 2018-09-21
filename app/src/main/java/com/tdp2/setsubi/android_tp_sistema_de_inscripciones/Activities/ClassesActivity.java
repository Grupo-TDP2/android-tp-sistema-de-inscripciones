package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.TextRecyclerViewAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.ClassesPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.ToolBarHelper;

import java.util.ArrayList;

public class ClassesActivity extends AppCompatActivity
        implements TextRecyclerViewAdapter.ItemClickListener
{
    public interface ClassesActivityPresenter
    {
        String getDepartmentName();
        ArrayList<String> getClasses();
        void onClicked(int position);
    }

    private ClassesActivityPresenter presenter;
    private TextRecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);
        presenter = new ClassesPresenter(this);
        ToolBarHelper.onCreate(this);
        ToolBarHelper.setTitle(this, R.string.materias);

        TextView bottomtitle = findViewById(R.id.bottomTitle);
        bottomtitle.setText(presenter.getDepartmentName());

        RecyclerView recyclerView = findViewById(R.id.departments_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TextRecyclerViewAdapter(this, presenter.getClasses());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position)
    {
        presenter.onClicked(position);
    }

    @Override
    public boolean onSupportNavigateUp() {
       return ToolBarHelper.onClickedHome(this);
    }

    public void goToCursos()
    {
        Intent intent = new Intent(this, CursosActivity.class);
        startActivity(intent);
    }
}
