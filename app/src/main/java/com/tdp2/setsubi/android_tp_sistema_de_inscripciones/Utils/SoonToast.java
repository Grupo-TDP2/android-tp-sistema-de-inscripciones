package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils;

import android.content.Context;
import android.widget.Toast;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;

public class SoonToast
{
    public static void show(Context context)
    {
        Toast.makeText(context, R.string.proximamente, Toast.LENGTH_SHORT).show();
    }
}
