package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.TextRecyclerViewAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.ClassesPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;

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
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_btn);

        TextView textView = findViewById(R.id.toolbar_title);
        textView.setText(presenter.getDepartmentName());

        RecyclerView recyclerView = findViewById(R.id.classes_recycler_view);
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
        onBackPressed();
        return true;
    }

    public void goToCursos()
    {
        Toast.makeText(this, "Going to cursos", Toast.LENGTH_SHORT).show();
        /* Intent intent = new Intent(this, );
        startActivity(intent);*/
    }
}
