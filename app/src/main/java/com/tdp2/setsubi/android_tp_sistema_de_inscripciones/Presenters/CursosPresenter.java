package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.CursoAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.CursosActivity;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Curso;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.CursoTime;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.CursoTimeBand;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Sede;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.DayOfWeek;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CursosPresenter implements CursosActivity.CursosLogic, CursoAdapter.SubscribeListener
{
    private CursoAdapter adapter = null;
    private List<Curso> cursos = new ArrayList<>();
    private boolean canSubscribe = true;

    public CursosPresenter()
    {
        cursos = Arrays.asList(new Curso(1,"Wachenchauzer", Sede.PASEO_COLON,
                Arrays.asList(new CursoTimeBand(DayOfWeek.MONDAY, 201, new CursoTime(10,30),
                        new CursoTime(12,30), CursoTimeBand.CursoTimeType.TEORICO, true),
                        new CursoTimeBand(DayOfWeek.FRIDAY, 202, new CursoTime(10,30),
                                new CursoTime(12,30), CursoTimeBand.CursoTimeType.PRACTIO, false))
                ,10));
    }

    @Override
    public CursoAdapter getAdapter()
    {
        if( adapter == null )
        {
            adapter = new CursoAdapter(cursos, canSubscribe,this);
        }
        return adapter;
    }

    @Override
    public String getCourseName() {
        return "75.15 Analisis Numerico";
    }

    @Override
    public void onSubscribe(int cursoId)
    {
        //TODO CALL TO API
        //FOR NOW DECREASE NUMBER
        int index = getCursoIndex(cursoId);
        if( index != -1 )
        {
            Curso curso = cursos.get(index);
            if( curso.getCupos() > 0 )
            {
                curso.setCupos(curso.getCupos() - 1);
                adapter.notifyItemChanged(index);
            }
        }
    }

    private int getCursoIndex(int cursoId) {
        for( int i = 0; i < cursos.size(); i++ )
        {
            Curso curso = cursos.get(i);
            if( curso.getId() == cursoId )
            {
                return i;
            }
        }
        return -1;
    }
}
