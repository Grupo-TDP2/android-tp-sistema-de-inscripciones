package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.TextRecyclerViewAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.CareersActivityPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.ToolBarHelper;

public class CareersActivity extends AppCompatActivity implements CareersActivityPresenter.View, TextRecyclerViewAdapter.ItemClickListener {

    private CareersActivityPresenter presenter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private TextRecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_careers);

        ToolBarHelper.onCreate(this);
        ToolBarHelper.setTitle(this, R.string.carreras);
        presenter = new CareersActivityPresenter(this);

        recyclerView = findViewById(R.id.careers_recycler_view);
        recyclerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerViewAdapter = new TextRecyclerViewAdapter(this, presenter.getDataset());
        recyclerViewAdapter.setClickListener(this);
        recyclerView.setAdapter(recyclerViewAdapter);

        presenter.loadCareers();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return ToolBarHelper.onClickedHome(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        //Navigate to corresponding view
        presenter.onSelectedCarreer(position);
    }

    @Override
    public void updateData() {
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void navigateToDepartments() {
        Intent navigationIntent = new Intent(CareersActivity.this, DepartmentsActivity.class);
        startActivity(navigationIntent);
    }
}
