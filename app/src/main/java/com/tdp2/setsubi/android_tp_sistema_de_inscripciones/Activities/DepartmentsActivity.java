package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.TextRecyclerViewAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.DepartmentsPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.ToolBarHelper;

import java.util.ArrayList;

public class DepartmentsActivity extends AppCompatActivity
        implements TextRecyclerViewAdapter.ItemClickListener
{
    public interface Presenter
    {
        ArrayList<String> getDepartments();
        void onSelectedDepartment(int position);
    }

    private TextRecyclerViewAdapter adapter;
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments);
        ToolBarHelper.onCreate(this);
        ToolBarHelper.setTitle(this, R.string.departments_title);
        presenter = new DepartmentsPresenter(this);

        RecyclerView recyclerView = findViewById(R.id.departments_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TextRecyclerViewAdapter(this, presenter.getDepartments());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position)
    {
        presenter.onSelectedDepartment(position);
    }

    public void goToClassesActivity()
    {
        Intent intent = new Intent(this, ClassesActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return ToolBarHelper.onClickedHome(this);
    }
}
