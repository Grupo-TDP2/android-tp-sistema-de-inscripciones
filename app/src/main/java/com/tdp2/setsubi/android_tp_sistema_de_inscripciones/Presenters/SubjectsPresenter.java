package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.SubjectsActivity;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Department;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.GetSubjectsAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.ServiceAsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class SubjectsPresenter implements SubjectsActivity.ClassesActivityPresenter, ServiceAsyncTask.ForeGroundListener
{
    private List<Subject> classes = new ArrayList<>();
    private ArrayList<String> viewClases = new ArrayList<>();
    private SubjectsActivity view;

    public SubjectsPresenter(SubjectsActivity view)
    {
        this.view = view;
    }

    @Override
    public String getDepartmentName()
    {
        Department department = AppModel.getInstance().getSelectedDepartment();
        return String.format(Locale.getDefault(), "%02d  %s",department.getCode(), department.getName());
    }

    @Override
    public ArrayList<String> getClasses()
    {
        transformClasses();
        return viewClases;
    }

    @Override
    public void onClicked(int position)
    {
        AppModel.getInstance().setSelectedSubject(classes.get(position));
        switch (AppModel.getInstance().getRoute())
        {
            case FREE_FINALS:
                view.goToFinals();
                break;
            default:
                view.goToCursos();
                break;
        }
    }

    @Override
    public void loadData()
    {
        AppModel appModel = AppModel.getInstance();
        new GetSubjectsAsyncTask(this).execute(appModel.getSelectedDepartment());
    }

    private void transformClasses()
    {
        sortClasses();
        viewClases.clear();
        for( Subject model : classes )
        {
            viewClases.add(String.format(Locale.getDefault(),
                    "%02d.%02d %s", model.getDepartmentCode(), model.getCode(), model.getName()));
        }
    }
    private void sortClasses()
    {
        Collections.sort(classes, new Comparator<Subject>() {
            @Override
            public int compare(Subject o1, Subject o2)
            {
                int diffDepartment = o1.getDepartmentCode() - o2.getDepartmentCode();
                if( diffDepartment == 0 ){
                    return o1.getCode() - o2.getCode();
                }
                return diffDepartment;
            }
        });
    }

    @Override
    public void onError(ServiceAsyncTask serviceAsyncTask, ServiceResponse.ServiceStatusCode error) {
        view.onFailedToLoadClasses();
    }

    @Override
    public void onSuccess(ServiceAsyncTask serviceAsyncTask, Object data)
    {
        classes = (List<Subject>)data;
        transformClasses();
        view.updatedList();
        if( classes.size() == 0 )
        {
            view.showNoAvailableSubjects();
        }
    }

    @Override
    public void onStartingAsyncTask(ServiceAsyncTask serviceAsyncTask) {

    }
}
