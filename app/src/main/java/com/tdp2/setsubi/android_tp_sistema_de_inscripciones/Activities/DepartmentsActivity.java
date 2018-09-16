package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.TextRecyclerViewAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.DepartmentsPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;

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
        setContentView(R.layout.activity_classes);
        presenter = new DepartmentsPresenter(this);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_btn);

        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText(R.string.departments_title);

        RecyclerView recyclerView = findViewById(R.id.classes_recycler_view);
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
        onBackPressed();
        return true;
    }
}
