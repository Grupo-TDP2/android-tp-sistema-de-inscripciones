package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.TextRecyclerViewAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.DepartmentsPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.LoadingSnackbar;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.ToolBarHelper;

import java.util.ArrayList;

public class DepartmentsActivity extends AppCompatActivity
        implements TextRecyclerViewAdapter.ItemClickListener, LoadingView
{
    public interface Presenter
    {
        ArrayList<String> getDepartments();
        void onSelectedDepartment(int position);
        void loadData();
    }

    private TextRecyclerViewAdapter adapter;
    private Presenter presenter;
    private Snackbar snackbar;

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
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));
        adapter = new TextRecyclerViewAdapter(this, presenter.getDepartments());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        presenter.loadData();
    }

    @Override
    public void onItemClick(View view, int position)
    {
        presenter.onSelectedDepartment(position);
    }

    public void goToClassesActivity()
    {
        Intent intent = new Intent(this, SubjectsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return ToolBarHelper.onClickedHome(this);
    }

    public void failedToLoadDepartments()
    {
        Toast.makeText(this, R.string.connectivityFailed, Toast.LENGTH_SHORT).show();
    }

    public void updatedList() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void startLoading() {
        stopLoading();
        snackbar = LoadingSnackbar.createLoadingSnackBar(findViewById(R.id.departments_recycler_view));
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
