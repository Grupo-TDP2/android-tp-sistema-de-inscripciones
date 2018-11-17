package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.ImageView;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.NotificationHelper;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.ToolBarHelper;

public class MyDataActivtiy extends AppCompatActivity
{
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
        setContentView(R.layout.activity_my_data);
        ToolBarHelper.onCreate(this);
        ToolBarHelper.setTitle(this, R.string.my_data);
        ImageView edit = findViewById(R.id.edit_my_data);
        edit.setOnClickListener(v ->
                startActivity(new Intent(MyDataActivtiy.this, MyDataEditionActivity.class)));
        //
    }

    public void setUserImage()
    {

    }

    public void setName(String name, String surname)
    {

    }

    public void setPadron(String padron)
    {

    }

    public void setPriority(int priority)
    {

    }

    @Override
    public boolean onSupportNavigateUp() {
        return ToolBarHelper.onClickedHome(this);
    }
}
