package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.CursoAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.CursosActivity;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.EnrolmentResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Course;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.EnrolToClassAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.GetCoursesAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.ServiceAsyncTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class CursosPresenter implements CursosActivity.CursosLogic, CursoAdapter.SubscribeListener,
        ServiceAsyncTask.ForeGroundListener {
    private CursoAdapter adapter = null;
    private List<Course> courses = new ArrayList<>();
    private boolean canSubscribe = true;
    private CursosActivity activity;
    private boolean isSubscribing = false;

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
        int index = getCursoIndex(cursoId);
        if( index != -1 )
        {
            Course course = courses.get(index);

            if( course.isSubscribed() )
            {
                activity.showAlreadySubscribed();
            } else if (!isSubscribing)
            {
                isSubscribing = true;
                AppModel model = AppModel.getInstance();
                new EnrolToClassAsyncTask(this).execute(model.getStudent(), model.getSelectedCareer(), model.getSelecteSubject(), course);
            } else if ( isSubscribing)
            {
                activity.showStillSubscribing();
            }
        } else if( isSubscribing )
        {
            activity.showStillSubscribing();
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
    public void onError(ServiceAsyncTask serviceAsyncTask, ServiceResponse.ServiceStatusCode error)
    {
        activity.stopLoading();
        if( serviceAsyncTask instanceof EnrolToClassAsyncTask )
        {
            isSubscribing = false;
            activity.onFailedToEnroll();
        } else {
            activity.onFailedtoConnectivity();
        }
    }

    @Override
    public void onSuccess(ServiceAsyncTask serviceAsyncTask, Object data)
    {
        activity.stopLoading();
        if( data instanceof List )
        {
            List<Course> list = filterComplete((List<Course>)data);
            courses.clear();
            courses.addAll(list);
            Collections.sort(list, new Comparator<Course>() {
                @Override
                public int compare(Course o1, Course o2) {
                    return o1.getCatedra().compareTo(o2.getCatedra());
                }
            });
            adapter.setCanSubscribe(!anySubscribed());
            adapter.notifyDataSetChanged();
            if( list.size() == 0 )
            {
                activity.showNoCoursesAvailable();
            }
        } else if( data instanceof EnrolmentResponse)
        {
            isSubscribing = false;
            EnrolmentResponse response = (EnrolmentResponse) data;
            activity.showSuccessResponse(response);
            int index = getCursoIndex(response.getCourseId());
            if( index != -1 )
            {
                subscribeTo(index);
            }
        }
    }

    private List<Course> filterComplete(List<Course> list)
    {
        List<Course> courses = new ArrayList<>(list);
        ListIterator<Course> iterator = courses.listIterator();
        while (iterator.hasNext())
        {
            if( iterator.next().getCursoTimeBands().size() == 0 )
            {
                iterator.remove();
            }
        }
        return courses;
    }

    private boolean anySubscribed() {
        for( Course course : courses)
        {
            if( course.isSubscribed() )
            {
                return true;
            }
        }
        return false;
    }

    private void subscribeTo(int index)
    {
        Course course = courses.get(index);
        course.setSubscribed(true);
        if( course.getCupos() != 0 )
        {
            course.setCupos(course.getCupos() - 1);
        }
        adapter.setCanSubscribe(false);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStartingAsyncTask(ServiceAsyncTask serviceAsyncTask) {
        activity.startLoading();
    }
}
