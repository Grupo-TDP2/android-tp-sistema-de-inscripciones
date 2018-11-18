package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;

public class PriorityDialog extends Dialog
{
    public PriorityDialog(AppCompatActivity a)
    {
        super(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.priority_layout);
        Button ok = findViewById(R.id.ok_button);
        ok.setOnClickListener(v -> dismiss());
        TextView priority = findViewById(R.id.priority_number);
        priority.setText(String.valueOf(AppModel.getInstance().getStudent().getPriotity()));
    }
}

