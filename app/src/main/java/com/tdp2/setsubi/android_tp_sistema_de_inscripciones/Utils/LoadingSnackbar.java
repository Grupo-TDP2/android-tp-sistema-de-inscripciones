package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils;

import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;

public class LoadingSnackbar
{
    public static Snackbar createLoadingSnackBar(View view)
    {
        Snackbar snackbar = Snackbar.make(view, R.string.loading, Snackbar.LENGTH_INDEFINITE);
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorPrimaryDark));
        return snackbar;
    }
}
