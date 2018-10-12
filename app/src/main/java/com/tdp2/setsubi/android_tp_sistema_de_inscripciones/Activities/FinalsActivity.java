package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.FinalsAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.TextRecyclerViewAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Final;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.DepartmentsPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.FinalsPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.ToolBarHelper;

import java.util.List;

public class FinalsActivity extends AppCompatActivity
{
    public interface Presenter extends FinalsAdapter.Listener {
        void loadFinals();
        List<Final> getFinals();
        String getSubjectName();
    }

    private FinalsAdapter adapter;
    private Presenter presenter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finals_of_subject);
        ToolBarHelper.onCreate(this);
        ToolBarHelper.setTitle(this, R.string.finals_title);
        TextView title = findViewById(R.id.finals_subject);
        presenter = new FinalsPresenter(this);
        title.setText(presenter.getSubjectName());

        recyclerView = findViewById(R.id.finals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));

        adapter = new FinalsAdapter(presenter.getFinals(), presenter);
        recyclerView.setAdapter(adapter);
        presenter.loadFinals();
    }
}
