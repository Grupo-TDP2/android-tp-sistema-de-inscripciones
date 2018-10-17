package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.FinalsActivity;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Department;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Final;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Sede;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FinalsPresenter implements FinalsActivity.Presenter
{
    private FinalsActivity activity;
    private List<Final> finals = new ArrayList<>();

    public FinalsPresenter(FinalsActivity activity)
    {
        this.activity = activity;
    }

    @Override
    public void loadFinals() {

    }

    @Override
    public List<Final> getFinals() {
        return finals;
    }

    @Override
    public String getSubjectName() {
        Subject subject = AppModel.getInstance().getSelecteSubject();
        return String.format(Locale.getDefault(), "%02d.%02d %s", subject.getDepartmentCode(), subject.getCode(), subject.getName());
    }

    @Override
    public void clickedButton(int position)
    {

    }
}
