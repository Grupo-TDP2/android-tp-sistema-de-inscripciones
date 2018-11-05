package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import android.util.Log;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.FinalsActivity;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Final;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.GetFinalsForsSubjectAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.GetFinalsOfCourseAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.ServiceAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.SubscribeToFinalAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.UnsubscribeFromFinalAsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class FinalsPresenter implements FinalsActivity.Presenter, ServiceAsyncTask.ForeGroundListener
{
    private FinalsActivity activity;
    private List<Final> finals = new ArrayList<>();
    private int interactingPosition = -1;
    private boolean finalWasRegular = false;

    public FinalsPresenter(FinalsActivity activity)
    {
        this.activity = activity;
    }

    @Override
    public void loadFinals()
    {
        AppModel model = AppModel.getInstance();
        if( model.getRoute() == AppModel.SubjectRoute.FINALS_OF_COURSE )
        {
            new GetFinalsOfCourseAsyncTask(this)
                    .execute(model.getStudent(), model.getSelectedCareer(),
                            model.getSelectedSubject(), model.getSelectedCourse());
        } else
        {
            new GetFinalsForsSubjectAsyncTask(this)
                    .execute(model.getStudent(), model.getSelectedCareer(), model.getSelectedSubject());
        }
    }

    @Override
    public List<Final> getFinals() {
        return finals;
    }

    @Override
    public String getSubjectName() {
        Subject subject = AppModel.getInstance().getSelectedSubject();
        return String.format(Locale.getDefault(), "%02d.%02d %s", subject.getDepartmentCode(), subject.getCode(), subject.getName());
    }

    @Override
    public void clickedButton(int position, boolean regular)
    {
        if( interactingPosition != -1 ) return;
        interactingPosition = position;
        Final fina = finals.get(position);
        Student student = AppModel.getInstance().getStudent();
        if( fina.isSubscribed() )
        {
            new UnsubscribeFromFinalAsyncTask(this).execute(student, fina);
        } else
        {
            finalWasRegular = regular;
            new SubscribeToFinalAsyncTask(this).execute(student, fina, regular);
        }
    }

    @Override
    public void onError(ServiceAsyncTask serviceAsyncTask, ServiceResponse.ServiceStatusCode error)
    {
        activity.stopLoading();
        if( error == ServiceResponse.ServiceStatusCode.NO_CONNECTION )
        {
            activity.showToast(R.string.connectivityFailed);
        } else if( serviceAsyncTask instanceof GetFinalsForsSubjectAsyncTask
                || serviceAsyncTask instanceof GetFinalsOfCourseAsyncTask )
        {
            activity.showToast(R.string.error_while_loading_finals);
        } else if( serviceAsyncTask instanceof UnsubscribeFromFinalAsyncTask )
        {
            interactingPosition = -1;
            activity.showToast(R.string.error_when_unsubscribing);
        } else if( serviceAsyncTask instanceof SubscribeToFinalAsyncTask )
        {
            interactingPosition = -1;
            activity.showToast(R.string.error_when_subscribing);
        }
    }

    @Override
    public void onSuccess(ServiceAsyncTask serviceAsyncTask, Object data) {
        activity.stopLoading();
        if( serviceAsyncTask instanceof GetFinalsForsSubjectAsyncTask
                || serviceAsyncTask instanceof GetFinalsOfCourseAsyncTask )
        {
            finals.clear();
            finals.addAll(filterPossible((List<Final>)data));
            Collections.sort(finals, new Comparator<Final>() {
                @Override
                public int compare(Final o1, Final o2) {
                    return o1.compare(o2);
                }
            });
            activity.notifyDataSetChanged();
            if( finals.size() == 0 )
            {
                activity.showNoFinalsAvailable();
            }
        } else
        {
            Final fina = finals.get(interactingPosition);
            fina.setSubscribed(!fina.isSubscribed());
            if( serviceAsyncTask instanceof SubscribeToFinalAsyncTask )
            {
                fina.setSubscription((Integer)data, finalWasRegular); //TODO READ CONDITION
            }
            activity.notifyFinalChange(interactingPosition);
            activity.showToast(fina.isSubscribed() ? R.string.subscribed_final_success : R.string.unsubribed_final_success);
            interactingPosition = -1;
        }
    }

    private List<Final> filterPossible(List<Final> data)
    {
        ListIterator<Final> iterator = data.listIterator();
        while (iterator.hasNext())
        {
            Final next = iterator.next();
            if( next.hasAlreadyPassedDate() )
            {
                Log.d("FINAL","Filtered a final because it can't be enroled");
                iterator.remove();
            }
        }
        return data;
    }



    @Override
    public void onStartingAsyncTask(ServiceAsyncTask serviceAsyncTask) {
        activity.startLoading();
    }
}
