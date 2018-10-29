package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.ApprovedSubjectAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.TextRecyclerViewAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.ApprovedSubjectPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.LoadingSnackbar;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.ToolBarHelper;

public class ApprovedSubjectActivity extends AppCompatActivity implements ApprovedSubjectPresenter.View
{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ApprovedSubjectPresenter presenter;
    private Snackbar snackbar = null;
    private TextView noFinals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_finales);

        ToolBarHelper.onCreate(this);
        ToolBarHelper.setTitle(this, R.string.approved_subjects);
        presenter = new ApprovedSubjectPresenter(this);
        noFinals = findViewById(R.id.noFinals);
        noFinals.setText(R.string.no_approved_subjects);
        recyclerView = findViewById(R.id.finals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));
        adapter = new ApprovedSubjectAdapter(presenter.getSubjects());
        recyclerView.setAdapter(adapter);
        presenter.loadSubjects();
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
