package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

public class MainActivityPresenter {

    private View view;

    public MainActivityPresenter(View view) {
        this.view = view;
    }

    public void navigateTo(String path) {
        view.navigateToActivity(path);
    }

    public interface View {

        void navigateToActivity(String path);

    }
}
