package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutCompat;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;


import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Dialogs.PriorityDialog;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.MainActivityPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.NotificationHelper;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.SoonToast;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.ToolBarHelper;

public class MainActivity extends AppCompatActivity implements MainActivityPresenter.View
{
    private MainActivityPresenter presenter;
    private DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ToolBarHelper.onCreate(this);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        ToolBarHelper.setTitle(this, R.string.fiuba_inscripciones);
        initNavMenu();
        presenter = new MainActivityPresenter(this);

        Button academicOfferButton = this.findViewById(R.id.academicOfferButton);
        academicOfferButton.setOnClickListener(new AcademicOfferClickHandler());

        Button newCourseButton = this.findViewById(R.id.newCourseButton);
        newCourseButton.setOnClickListener(new NewCourseClickHandler());

        findViewById(R.id.newFinalExamButton).setOnClickListener(new FinalsExamsHandler());
        findViewById(R.id.myCoursesButton).setOnClickListener(new MyCoursesHandler());
        findViewById(R.id.myExamsButton).setOnClickListener(new MyExamsHandler());
        findViewById(R.id.priorityButton).setOnClickListener(new PriorityHandler());
        findViewById(R.id.myDataButton).setOnClickListener(new MyDataHandler());
    }

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

    private void initNavMenu()
    {
        drawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem)
                    {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        switch (menuItem.getItemId())
                        {
                            case R.id.nav_approved_subjects:
                                startActivity(new Intent(MainActivity.this, ApprovedSubjectActivity.class));
                        }
                        drawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });
    }

    @Override
    public void navigateToActivity(String path) {
        Intent navigationIntent = null;

        switch (path) {
            case "academicOffer": case "newCourse": case "finals":
                navigationIntent = new Intent(MainActivity.this, CareersActivity.class);
                break;
            case "myFinals":
                navigationIntent = new Intent(MainActivity.this, MyFinalsActivity.class);
                break;
            case "myCourses":
                navigationIntent = new Intent(MainActivity.this, MyCoursesActivity.class);
                break;
            case "myData":
                navigationIntent = new Intent(MainActivity.this, MyDataActivtiy.class);
                break;
        }
        if( navigationIntent != null )
        {
            startActivity(navigationIntent);
        }
    }

    @Override
    public Context getContext() {
        return this.getApplicationContext();
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.log_out_title)
                .setMessage(R.string.log_out_message)
                .setPositiveButton(R.string.log_out_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.logout();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton(R.string.log_out_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    protected class AcademicOfferClickHandler implements View.OnClickListener {

        public void onClick(View v){
            presenter.navigateTo("academicOffer");
        }
    }

    protected class NewCourseClickHandler implements View.OnClickListener {
        public void onClick(View v){
            presenter.navigateTo("newCourse");
        }
    }

    protected class SoonClickHandler implements View.OnClickListener
    {
        public void onClick(View v){
            SoonToast.show(MainActivity.this);
        }
    }

    protected class PriorityHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            PriorityDialog cdd = new PriorityDialog(MainActivity.this);
            cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            cdd.show();
            Window window = cdd.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    protected class MyCoursesHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            presenter.navigateTo("myCourses");
        }
    }

    protected class MyDataHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            presenter.navigateTo("myData");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        drawerLayout.openDrawer(GravityCompat.START, true);
        return true;
    }

    private class MyExamsHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            presenter.navigateTo("myFinals");
        }
    }

    private class FinalsExamsHandler implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            presenter.navigateTo("finals");
        }
    }
}
