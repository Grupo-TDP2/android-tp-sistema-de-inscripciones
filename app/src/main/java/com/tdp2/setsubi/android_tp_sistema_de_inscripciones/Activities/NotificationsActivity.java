package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.NotificationAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.NotificationPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.LoadingSnackbar;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.ToolBarHelper;

public class NotificationsActivity extends AppCompatActivity implements NotificationPresenter.NotificationView
{
    private NotificationAdapter adapter;
    private NotificationPresenter presenter;
    private Snackbar snackbar = null;
    private RecyclerView recyclerView;
    @Override
    protected void onResume()
    {
        super.onResume();
        AppModel.getInstance().setVisibility(true);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        AppModel.getInstance().setVisibility(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_white);

        ToolBarHelper.setTitle(this, R.string.notifications);
        presenter = new NotificationPresenter(this);
        recyclerView = findViewById(R.id.notifications_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));
        adapter = new NotificationAdapter(presenter.getDataSet(), presenter);
        recyclerView.setAdapter(adapter);
        presenter.loadNotifications();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return ToolBarHelper.onClickedHome(this);
    }


    @Override
    public void showNoNotifications() {
        findViewById(R.id.no_notifications).setVisibility(View.VISIBLE);
    }

    @Override
    public void updatedNotifications() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void goToMyFinals() {
        Intent intent = new Intent(this, MyFinalsActivity.class);
        startActivity(intent);
    }

    @Override
    public void goToMyCourses() {
        Intent intent = new Intent(this, MyCoursesActivity.class);
        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
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
