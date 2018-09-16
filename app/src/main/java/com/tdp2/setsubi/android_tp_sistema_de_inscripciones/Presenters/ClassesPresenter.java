package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.ClassesActivity;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.ClassModel;

import java.util.ArrayList;
import java.util.Arrays;
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
    }

    @Override
    public String getDepartmentName()
    {
        return "75  Departamento de Computacion";
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
}
