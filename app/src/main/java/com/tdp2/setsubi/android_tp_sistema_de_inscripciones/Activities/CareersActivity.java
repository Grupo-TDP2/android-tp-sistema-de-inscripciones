package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.TextRecyclerViewAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.CareersActivityPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;

public class CareersActivity extends AppCompatActivity implements CareersActivityPresenter.View, TextRecyclerViewAdapter.ItemClickListener {

    private CareersActivityPresenter presenter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private TextRecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_careers);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_btn);

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
        Intent navigationIntent = new Intent(CareersActivity.this, MainActivity.class);
        startActivity(navigationIntent);
        return true;
    }

    @Override
    public void onItemClick(View view, int position) {
        //Navigate to corresponding view
    }

    @Override
    public void updateData() {
        recyclerViewAdapter.notifyDataSetChanged();
    }
}
