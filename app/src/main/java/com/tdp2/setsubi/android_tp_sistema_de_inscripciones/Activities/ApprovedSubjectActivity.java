package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.ApprovedSubjectAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.FiubaSpinnerAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.TextRecyclerViewAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.ApprovedSubjectPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.LoadingSnackbar;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.ToolBarHelper;

import java.util.List;

public class ApprovedSubjectActivity extends AppCompatActivity implements ApprovedSubjectPresenter.View
{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ApprovedSubjectPresenter presenter;
    private Snackbar snackbar = null;
    private TextView noFinals;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_subjects);

        ToolBarHelper.onCreate(this);
        ToolBarHelper.setTitle(this, R.string.approved_subjects);
        presenter = new ApprovedSubjectPresenter(this);
        noFinals = findViewById(R.id.noFinals);
        noFinals.setText(R.string.no_approved_subjects);
        recyclerView = findViewById(R.id.finals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ApprovedSubjectAdapter(presenter.getSubjects());
        recyclerView.setAdapter(adapter);
        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(new FiubaSpinnerAdapter(presenter.getDepartmentFilters()));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                presenter.onFilterSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        presenter.loadSubjects();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return ToolBarHelper.onClickedHome(this);
    }

    @Override
    public void dataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showNoSubjects() {
        noFinals.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(int resourceId) {
        Toast.makeText(this,resourceId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setSpinnerOptions(List<String> options) {
        spinner.setAdapter(new FiubaSpinnerAdapter(options));
    }

    @Override
    public Context getContext() {
        return this.getApplicationContext();
    }

    @Override
    public void startLoading() {
        if( snackbar == null )
        {
            snackbar = LoadingSnackbar.createLoadingSnackBar(recyclerView);
            snackbar.show();
        }
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
