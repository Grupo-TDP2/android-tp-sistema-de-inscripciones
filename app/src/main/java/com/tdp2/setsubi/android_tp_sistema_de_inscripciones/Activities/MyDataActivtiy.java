package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;

import android.widget.ImageView;
import android.widget.TextView;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.MyDataPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.BitmapEncoder;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.NotificationHelper;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.ToolBarHelper;

import java.io.IOException;

public class MyDataActivtiy extends AppCompatActivity implements MyDataPresenter.View
{
    private NotificationHelper helper;
    private MyDataPresenter presenter;
    private ImageView userImage;
    private TextView nameAndSurname, email, padron, priority;

    @Override
    protected void onResume() {
        super.onResume();
        helper = new NotificationHelper(ToolBarHelper.getNotificationView(this));
        AppModel.getInstance().setVisibility(true);
        presenter.loadData();
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
        userImage = findViewById(R.id.user_image);
        nameAndSurname = findViewById(R.id.user_name_and_lastname);
        email = findViewById(R.id.email);
        padron = findViewById(R.id.padron);
        priority = findViewById(R.id.priority);
        presenter = new MyDataPresenter(this);
    }

    public void setUserImage(String uriPath)
    {
        if( uriPath == null )
        {
            userImage.setImageResource(R.drawable.ic_user_silhouette);
        } else
        {
            userImage.setImageBitmap(BitmapEncoder.decodeBase64(uriPath));
        }
    }

    public void setEmail(String email)
    {
        this.email.setText(email);
    }

    public void setName(String name, String surname)
    {
        nameAndSurname.setText(name + " " + surname);
    }

    public void setPadron(String padron)
    {
        this.padron.setText(padron);
    }

    public void setPriority(int priority)
    {
        this.priority.setText(String.valueOf(priority));
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return ToolBarHelper.onClickedHome(this);
    }
}
