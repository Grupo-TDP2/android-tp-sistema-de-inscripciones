package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;

import android.widget.ArrayAdapter;

import java.util.List;

public class FinalConditionAdapter extends ArrayAdapter<String>
{
    public FinalConditionAdapter(@NonNull Context context, List<String> options)
    {
        super(context, android.R.layout.simple_spinner_item, options);
    }
}
