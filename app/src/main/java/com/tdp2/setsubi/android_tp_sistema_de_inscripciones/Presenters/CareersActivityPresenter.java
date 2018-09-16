package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import java.util.ArrayList;

public class CareersActivityPresenter {

    private View view;
    private ArrayList<String> dataset = new ArrayList<>();

    public CareersActivityPresenter(View view) {
        this.view = view;
    }

    public ArrayList<String> getDataset() {
        return dataset;
    }

    public void loadCareers() {
        //AsyncTask call comes here. For now, its mocked.
        dataset.add("Ingenieria en Informatica");
        dataset.add("Ingenieria Electronica");
        dataset.add("Ingenieria en Alimentos");

        view.updateData();
    }

    public interface View {

        void updateData();

    }
}
