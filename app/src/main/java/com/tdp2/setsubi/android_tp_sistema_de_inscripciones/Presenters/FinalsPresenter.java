package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.FinalsActivity;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Department;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Final;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Sede;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;

import java.util.ArrayList;
import java.util.List;

public class FinalsPresenter implements FinalsActivity.Presenter
{
    private FinalsActivity activity;
    private List<Final> finals = new ArrayList<>();

    public FinalsPresenter(FinalsActivity activity)
    {
        this.activity = activity;
        finals.add(new Final(1,false, true, false, true,
                new Subject(1,15, "Algoritmos y Programacion II", 6,
                        new Department(1, "Informatica", 75)),
                "Wachencauzer","10/01/2019","19:00", Sede.PASEO_COLON, 310));
        finals.add(new Final(2,true, false, true, false,
                new Subject(1,15, "Algoritmos y Programacion II", 6,
                        new Department(1, "Informatica", 75)),
                "Wachencauzer","17/01/2019","19:00", Sede.PASEO_COLON, 312));
        finals.add(new Final(2,false, true, false, false,
                new Subject(1,15, "Algoritmos y Programacion II", 6,
                        new Department(1, "Informatica", 75)),
                "Wachencauzer","17/01/2019","19:00", Sede.PASEO_COLON, 312));
        finals.add(new Final(2,true, false, false, false,
                new Subject(1,15, "Algoritmos y Programacion II", 6,
                        new Department(1, "Informatica", 75)),
                "Wachencauzer","17/01/2019","19:00", Sede.PASEO_COLON, 312));
    }

    @Override
    public void loadFinals() {

    }

    @Override
    public List<Final> getFinals() {
        return finals;
    }

    @Override
    public void clickedButton(int position)
    {

    }
}
