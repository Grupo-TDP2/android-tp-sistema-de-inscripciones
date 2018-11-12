package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.SubjectsActivity;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.ApprovedSubject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Department;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.GetApprovedSubject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.GetSubjectsAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.ServiceAsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class SubjectsPresenter implements SubjectsActivity.ClassesActivityPresenter, ServiceAsyncTask.ForeGroundListener
{
    private List<Subject> classes = new ArrayList<>();
    private ArrayList<String> viewClases = new ArrayList<>();
    private SubjectsActivity view;
    private List<ApprovedSubject> approvedSubjects = new ArrayList<>();
    private boolean filterApprovedSubjects;
    private int loadedRequests = 0;

    public SubjectsPresenter(SubjectsActivity view)
    {
        this.view = view;
        filterApprovedSubjects = AppModel.getInstance().getRoute() == AppModel.SubjectRoute.FINALS_SUBSCRIPTION;
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
            case FINALS_SUBSCRIPTION:
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
        if( filterApprovedSubjects )
        {
            new GetApprovedSubject(this).execute(appModel.getStudent());
        }
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
        Collections.sort(classes, (o1, o2) -> {
            int diffDepartment = o1.getDepartmentCode() - o2.getDepartmentCode();
            if( diffDepartment == 0 ){
                return o1.getCode() - o2.getCode();
            }
            return diffDepartment;
        });
    }

    @Override
    public void onError(ServiceAsyncTask serviceAsyncTask, ServiceResponse.ServiceStatusCode error)
    {
        if( loaded() )
        {
            view.onFailedToLoadClasses();
        }
    }

    @Override
    public void onSuccess(ServiceAsyncTask serviceAsyncTask, Object data)
    {
        if( serviceAsyncTask instanceof GetSubjectsAsyncTask )
        {
            classes = (List<Subject>)data;
        } else if( serviceAsyncTask instanceof GetApprovedSubject )
        {
            approvedSubjects = (List<ApprovedSubject>)data;
        }
        if( loaded() )
        {
            filterApprovedSubjects();
            transformClasses();
            view.updatedList();
            if( classes.size() == 0 )
            {
                view.showNoAvailableSubjects();
            }
        }
    }

    private void filterApprovedSubjects() {
        for( ApprovedSubject subject : approvedSubjects )
        {
            int i = 0;
            for( Subject inModel : classes )
            {
                if( inModel.getId() == subject.getSubject().getId() && inModel.getDepartment().getId()
                        == subject.getSubject().getDepartment().getId() )
                {
                    classes.remove(i);
                    break;
                }
                i++;
            }
        }
    }

    private boolean loaded()
    {
        loadedRequests++;
        if( !filterApprovedSubjects || loadedRequests == 2 )
        {
            loadedRequests = 0;
            return true;
        }
        return false;
    }

    @Override
    public void onStartingAsyncTask(ServiceAsyncTask serviceAsyncTask) {

    }
}
