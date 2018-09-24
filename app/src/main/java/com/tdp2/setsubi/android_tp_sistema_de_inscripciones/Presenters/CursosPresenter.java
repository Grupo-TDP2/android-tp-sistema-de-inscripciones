package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.CursoAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.CursosActivity;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Course;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.GetCoursesAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.ServiceAsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CursosPresenter implements CursosActivity.CursosLogic, CursoAdapter.SubscribeListener, ServiceAsyncTask.ForeGroundListener<List<Course>> {
    private CursoAdapter adapter = null;
    private List<Course> courses = new ArrayList<>();
    private boolean canSubscribe = true;
    private CursosActivity activity;

    public CursosPresenter(CursosActivity activity)
    {
        this.activity = activity;
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
        Subject subject = AppModel.getInstance().getSelecteSubject();
        return String.format(Locale.getDefault(), "%02d.%02d %s", subject.getDepartmentCode(), subject.getCode(), subject.getName());
    }

    @Override
    public void loadData()
    {
        AppModel appModel = AppModel.getInstance();
        new GetCoursesAsyncTask(this).execute(appModel.getStudent(), appModel.getSelectedCareer(), appModel.getSelecteSubject());
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

    @Override
    public void onError(ServiceResponse.ServiceStatusCode error)
    {
        activity.stopLoading();
        activity.onFailedtoLoadCursos();
    }

    @Override
    public void onSuccess(List<Course> data)
    {
        activity.stopLoading();
        courses.clear();
        courses.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStartingAsyncTask() {
        activity.startLoading();
    }
}
