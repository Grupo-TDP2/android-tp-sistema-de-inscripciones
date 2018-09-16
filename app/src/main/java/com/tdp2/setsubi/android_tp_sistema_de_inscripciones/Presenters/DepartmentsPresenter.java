package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.DepartmentsActivity;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Department;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class DepartmentsPresenter implements DepartmentsActivity.Presenter
{
    private DepartmentsActivity activity;
    private List<Department> departmentList = new ArrayList<>();

    public DepartmentsPresenter(DepartmentsActivity activity)
    {
        this.activity = activity;
        departmentList.add(new Department(75, "Computacion"));
        departmentList.add(new Department(66, "Electronica"));
        sortDepartments();
    }

    @Override
    public ArrayList<String> getDepartments()
    {
        ArrayList<String> departments = new ArrayList<>();
        for( Department department : departmentList )
        {
            departments.add(String.format(Locale.getDefault(), "%02d  %s", department.getId(), department.getName()));
        }
        return departments;
    }

    @Override
    public void onSelectedDepartment(int position) {
        //TODO DO SELECTION TO LOAD MATERIAS
        activity.goToClassesActivity();
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
}
