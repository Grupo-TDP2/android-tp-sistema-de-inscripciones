package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.ClassesActivity;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.ClassModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class ClassesPresenter implements ClassesActivity.ClassesActivityPresenter
{

    private List<ClassModel> classes;
    private ClassesActivity view;

    public ClassesPresenter(ClassesActivity view)
    {
        this.view = view;
        classes = Arrays.asList(new ClassModel(1,21,75,"Algoritmos y Programacion III"),
                new ClassModel(2,17, 75, "Analisis Numerico I"));
        sortClasses();
    }

    @Override
    public String getDepartmentName()
    {
        return "Departamento de Computacion";
    }

    @Override
    public ArrayList<String> getClasses()
    {
        ArrayList<String> formatted = new ArrayList<>();
        for( ClassModel model : classes )
        {
            formatted.add(String.format(Locale.getDefault(),
                    "%02d.%02d %s", model.getDepartment(), model.getCode(), model.getName()));
       }
        return formatted;
    }

    @Override
    public void onClicked(int position)
    {
        //TODO GET ELEMENT AND PERFORM TASK TO GET THE CURSO DATA, THEN GO TO CURSOS
        view.goToCursos();
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
}
