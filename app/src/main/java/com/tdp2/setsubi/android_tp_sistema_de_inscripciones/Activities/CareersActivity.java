package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.TextRecyclerViewAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.CareersActivityPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.LoadingSnackbar;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.ToolBarHelper;

public class CareersActivity extends AppCompatActivity implements
        CareersActivityPresenter.View, TextRecyclerViewAdapter.ItemClickListener {

    private CareersActivityPresenter presenter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private TextRecyclerViewAdapter recyclerViewAdapter;
    private Snackbar snackbar;

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
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));
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

    @Override
    public void failedToLoadCareers() {
        Toast.makeText(this, R.string.connectivityFailed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startLoading() {
        stopLoading();
        snackbar = LoadingSnackbar.createLoadingSnackBar(findViewById(R.id.careers_recycler_view));
        snackbar.show();
    }

    @Override
    public void stopLoading() {
        if( snackbar != null )
        {
            snackbar.dismiss();
            snackbar = null;
        }
    }
}
