package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.MyFinalsActivity;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Department;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Final;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Sede;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;

import java.util.ArrayList;
import java.util.List;

public class MyFinalsPresenter implements MyFinalsActivity.Presenter
{
    private MyFinalsActivity activity;
    private List<Final> finals = new ArrayList<>();

    public MyFinalsPresenter(MyFinalsActivity activity)
    {
        this.activity = activity;
        finals.add(new Final(1,true, false, false, false,
                new Subject(1,15, "Algoritmos y Programacion II", 6,
                        new Department(1, "Informatica", 75)),
                "Wachencauzer","10/01/2019","19:00", Sede.PASEO_COLON, 310));
        finals.add(new Final(2,true, false, true, false,
                new Subject(1,15, "Algoritmos y Programacion II", 6,
                    new Department(1, "Informatica", 75)),
                "Wachencauzer","17/01/2019","19:00", Sede.PASEO_COLON, 312));
    }


    @Override
    public List<Final> getDataSet()
    {
        return finals;
    }

    @Override
    public void loadMyFinals()
    {
        //TODO ASYNC TASK
    }

    @Override
    public void onUnsubscribe(int position)
    {
        if( position < finals.size() )
        {
            finals.remove(position);
            activity.notifyRemoved(position);
            activity.showToast(R.string.unsubribed_final_success);
        }
        //TODO UNSUBSCRIBE

    }
}
