package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.MyFinalsAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Final;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.MyFinalsPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.ToolBarHelper;

import java.util.List;

public class MyFinalsActivity extends AppCompatActivity
{
    public interface Presenter extends MyFinalsAdapter.Listener
    {
        List<Final> getDataSet();
        void loadMyFinals();
    }
    private RecyclerView recyclerView;
    private MyFinalsAdapter adapter;
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_finales);

        ToolBarHelper.onCreate(this);
        ToolBarHelper.setTitle(this, R.string.my_finals);
        presenter = new MyFinalsPresenter(this);
        recyclerView = findViewById(R.id.finals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));
        adapter = new MyFinalsAdapter(presenter.getDataSet(), presenter);
        recyclerView.setAdapter(adapter);
        presenter.loadMyFinals();
    }

    public void notifyRemoved(int position)
    {
        adapter.notifyItemRemoved(position);
    }

    public void showToast(int message)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}