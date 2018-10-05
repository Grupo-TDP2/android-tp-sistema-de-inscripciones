package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import android.util.Log;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.DepartmentsActivity;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Career;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Department;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.GetDepartmentsAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.ServiceAsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class DepartmentsPresenter implements DepartmentsActivity.Presenter, ServiceAsyncTask.ForeGroundListener<List<Subject>> {
    private DepartmentsActivity activity;
    private List<Department> departmentList = new ArrayList<>();
    private ArrayList<String> viewList = new ArrayList<>();

    public DepartmentsPresenter(DepartmentsActivity activity)
    {
        this.activity = activity;
    }

    @Override
    public ArrayList<String> getDepartments()
    {
        viewList = new ArrayList<>();
        transformDepartments();
        return viewList;
    }

    private void transformDepartments()
    {
        sortDepartments();
        viewList.clear();
        for( Department department : departmentList )
        {
            viewList.add(String.format(Locale.getDefault(), "%02d  %s", department.getCode(), department.getName()));
        }
    }

    @Override
    public void onSelectedDepartment(int position)
    {
        AppModel.getInstance().setSelectedDepartment(departmentList.get(position));
        activity.goToClassesActivity();
    }

    @Override
    public void loadData() {
        Student student = AppModel.getInstance().getStudent();
        Career career = AppModel.getInstance().getSelectedCareer();
        new GetDepartmentsAsyncTask(this).execute(student, career);
    }

    private void sortDepartments()
    {
        Collections.sort(departmentList, new Comparator<Department>() {
            @Override
            public int compare(Department o1, Department o2) {
                return o1.getId() - o2.getId();
            }
        });
    }

    @Override
    public void onError(ServiceAsyncTask serviceAsyncTask, ServiceResponse.ServiceStatusCode error)
    {
        activity.stopLoading();
        activity.failedToLoadDepartments();
    }

    @Override
    public void onSuccess(ServiceAsyncTask serviceAsyncTask, Object object)
    {
        List<Subject> subjects = (List<Subject>)object;
        activity.stopLoading();
        Log.d("DEP","Received " + subjects.size());
        AppModel.getInstance().setSubjects(subjects);
        departmentList = AppModel.getInstance().getDepartments();
        transformDepartments();
        activity.updatedList();
    }

    @Override
    public void onStartingAsyncTask(ServiceAsyncTask serviceAsyncTask) {
        activity.startLoading();
    }
}
