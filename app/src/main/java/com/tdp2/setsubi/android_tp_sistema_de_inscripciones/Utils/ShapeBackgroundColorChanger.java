package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.content.ContextCompat;
import android.view.View;

public class ShapeBackgroundColorChanger
{
    public static void changeColor(View view, int colorId)
    {
        Context context = view.getContext();
        Drawable background = view.getBackground();
        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable)background).getPaint().setColor(ContextCompat.getColor(context, colorId));
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable)background).setColor(ContextCompat.getColor(context, colorId));
        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable)background).setColor(ContextCompat.getColor(context, colorId));
        }
    }

    public static void changeBackground(View view, int background)
    {
        int left, top, right, bottom;
        left = view.getPaddingLeft();
        top = view.getPaddingTop();
        right = view.getPaddingRight();
        bottom = view.getPaddingBottom();
        view.setBackgroundResource(background);
        view.setPadding(left,top,right,bottom);
    }
}
