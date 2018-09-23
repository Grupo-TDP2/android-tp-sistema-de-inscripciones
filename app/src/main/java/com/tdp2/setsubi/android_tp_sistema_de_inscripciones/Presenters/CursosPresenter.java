package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.CursoAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.CursosActivity;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Course;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.CourseTime;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.CursoTimeBand;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Sede;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.DayOfWeek;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CursosPresenter implements CursosActivity.CursosLogic, CursoAdapter.SubscribeListener
{
    private CursoAdapter adapter = null;
    private List<Course> courses = new ArrayList<>();
    private boolean canSubscribe = true;

    public CursosPresenter()
    {
        courses = Arrays.asList(new Course(1,"Wachenchauzer", Sede.PASEO_COLON,
                Arrays.asList(new CursoTimeBand(DayOfWeek.MONDAY, 201, new CourseTime(10,30),
                        new CourseTime(12,30), CursoTimeBand.CursoTimeType.TEORICO, true),
                        new CursoTimeBand(DayOfWeek.FRIDAY, 202, new CourseTime(10,30),
                                new CourseTime(12,30), CursoTimeBand.CursoTimeType.PRACTIO, false))
                ,10));
    }

    @Override
    public CursoAdapter getAdapter()
    {
        if( adapter == null )
        {
            adapter = new CursoAdapter(courses, canSubscribe,this);
        }
        return adapter;
    }

    @Override
    public String getCourseName() {
        return "75.15 Analisis Numerico I B";
    }

    @Override
    public void onSubscribe(int cursoId)
    {
        //TODO CALL TO API
        //FOR NOW DECREASE NUMBER
        int index = getCursoIndex(cursoId);
        if( index != -1 )
        {
            Course course = courses.get(index);
            if( course.getCupos() > 0 )
            {
                course.setCupos(course.getCupos() - 1);
                adapter.notifyItemChanged(index);
            }
        }
    }

    private int getCursoIndex(int cursoId) {
        for(int i = 0; i < courses.size(); i++ )
        {
            Course course = courses.get(i);
            if( course.getId() == cursoId )
            {
                return i;
            }
        }
        return -1;
    }
}
