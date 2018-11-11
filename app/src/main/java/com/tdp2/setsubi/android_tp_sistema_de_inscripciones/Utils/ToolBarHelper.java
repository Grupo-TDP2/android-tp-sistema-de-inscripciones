package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.MainActivity;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.NotificationsActivity;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;

public class ToolBarHelper
{
    public static void onCreate(final AppCompatActivity activity)
    {
        Toolbar myToolbar = activity.findViewById(R.id.my_toolbar);
        activity.setSupportActionBar(myToolbar);
        activity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_home_white);
        myToolbar.findViewById(R.id.notification).setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), NotificationsActivity.class);
            v.getContext().startActivity(intent);
        });
    }

    public static boolean onClickedHome(AppCompatActivity activity)
    {
        Intent intent = new Intent(activity.getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
        return true;
    }

    public static void setTitle(AppCompatActivity appCompatActivity, int resource)
    {
        TextView textView = appCompatActivity.findViewById(R.id.toolbar_title);
        textView.setText(resource);
    }

    public static void setTitle(AppCompatActivity appCompatActivity, String string)
    {
        TextView textView = appCompatActivity.findViewById(R.id.toolbar_title);
        textView.setText(string);
    }

    public static ImageView getNotificationView(AppCompatActivity appCompatActivity)
    {
        return appCompatActivity.findViewById(R.id.my_toolbar).findViewById(R.id.notification);
    }
}
