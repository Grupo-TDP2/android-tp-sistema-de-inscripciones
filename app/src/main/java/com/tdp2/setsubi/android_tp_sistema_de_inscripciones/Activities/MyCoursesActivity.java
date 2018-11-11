package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.MyCoursesAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.MyCoursesPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.LoadingSnackbar;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.NotificationHelper;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.ToolBarHelper;

public class MyCoursesActivity extends AppCompatActivity implements MyCoursesPresenter.View
{
    private TextView noCourses;
    private MyCoursesPresenter presenter;
    private MyCoursesAdapter adapter;
    private Snackbar snackbar = null;
    private NotificationHelper helper;

    @Override
    protected void onResume() {
        super.onResume();
        helper = new NotificationHelper(ToolBarHelper.getNotificationView(this));
        AppModel.getInstance().setVisibility(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        helper.destroy();
        AppModel.getInstance().setVisibility(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);
        noCourses = findViewById(R.id.noCoursses);
        ToolBarHelper.onCreate(this);
        presenter = new MyCoursesPresenter(this);
        ToolBarHelper.setTitle(this, R.string.my_courses);
        RecyclerView view = findViewById(R.id.my_courses_list);
        view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyCoursesAdapter(presenter.getCoursesList(), presenter);
        view.setAdapter(adapter);
        presenter.loadData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return ToolBarHelper.onClickedHome(this);
    }

    @Override
    public void showNoCourses() {
        noCourses.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorLoading(int stringResource) {
        Toast.makeText(this,stringResource, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goToFinals()
    {
        startActivity(new Intent(this, FinalsActivity.class));
    }

    @Override
    public void onRemoved(int position) {
        adapter.notifyItemRemoved(position);
    }

    @Override
    public void onLoadedCourses() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showSuccess(int stringResource) {
        Toast.makeText(this,stringResource, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setCanSeeFinals(boolean canSeeFinals) {
        adapter.setInFinalsPeriod(canSeeFinals);
    }

    @Override
    public void startLoading() {
        if( snackbar == null )
        {
            snackbar = LoadingSnackbar.createLoadingSnackBar(noCourses);
            snackbar.show();
        }
    }

    @Override
    public void stopLoading() {
        if( snackbar != null )
        {
            snackbar.dismiss();
        }
    }
}
