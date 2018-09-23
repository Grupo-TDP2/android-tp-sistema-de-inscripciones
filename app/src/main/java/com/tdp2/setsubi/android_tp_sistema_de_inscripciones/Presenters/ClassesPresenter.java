package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.ClassesActivity;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.ClassModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Department;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.GetMateriasAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.ServiceAsyncTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class ClassesPresenter implements ClassesActivity.ClassesActivityPresenter, ServiceAsyncTask.ForeGroundListener<List<ClassModel>> {

    private List<ClassModel> classes = new ArrayList<>();
    private ArrayList<String> viewClases = new ArrayList<>();
    private ClassesActivity view;

    public ClassesPresenter(ClassesActivity view)
    {
        this.view = view;
    }

    @Override
    public String getDepartmentName()
    {
        Department department = AppModel.getInstance().getSelectedDepartment();
        return String.format(Locale.getDefault(), "%02d  %s",department.getId(), department.getName());
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
        AppModel.getInstance().setSelecteClass(classes.get(position));
        view.goToCursos();
    }

    @Override
    public void loadData()
    {
        AppModel appModel = AppModel.getInstance();
        new GetMateriasAsyncTask(this).execute(appModel.getStudent(), appModel.getSelectedCareer(), appModel.getSelectedDepartment());
    }

    private void transformClasses()
    {
        sortClasses();
        viewClases.clear();
        for( ClassModel model : classes )
        {
            viewClases.add(String.format(Locale.getDefault(),
                    "%02d.%02d %s", model.getDepartment(), model.getCode(), model.getName()));
        }
    }
    private void sortClasses()
    {
        Collections.sort(classes, new Comparator<ClassModel>() {
            @Override
            public int compare(ClassModel o1, ClassModel o2)
            {
                int diffDepartment = o1.getDepartment() - o2.getDepartment();
                if( diffDepartment == 0 ){
                    return o1.getCode() - o2.getCode();
                }
                return diffDepartment;
            }
        });
    }

    @Override
    public void onError(ServiceResponse.ServiceStatusCode error) {
        view.onFailedToLoadClasses();
    }

    @Override
    public void onSuccess(List<ClassModel> data)
    {
        classes = data;
        transformClasses();
        view.updatedList();
    }

    @Override
    public void onStartingAsyncTask() {

    }
}
