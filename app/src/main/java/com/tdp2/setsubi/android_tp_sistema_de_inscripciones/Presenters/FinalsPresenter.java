package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import android.util.Log;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.FinalsActivity;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.ActionPeriod;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Final;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.FinalSubscriptionResult;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.GetFinalsForsSubjectAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.GetFinalsOfCourseAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.GetFinalsPeriodAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.ServiceAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.SubscribeToFinalAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.UnsubscribeFromFinalAsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class FinalsPresenter implements FinalsActivity.Presenter, ServiceAsyncTask.ForeGroundListener
{
    private FinalsActivity activity;
    private List<Final> finals = new ArrayList<>();
    private int interactingPosition = -1;
    private int loadedBothThings = 0;

    private void loadedOne()
    {
        loadedBothThings++;
        if( loadedBothThings == 2 )
        {
            activity.stopLoading();
            loadedBothThings = 0;
        }
    }

    public FinalsPresenter(FinalsActivity activity)
    {
        this.activity = activity;
    }

    @Override
    public void loadFinals()
    {
        AppModel model = AppModel.getInstance();
        new GetFinalsPeriodAsyncTask(this).execute(model.getStudent());
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
            new SubscribeToFinalAsyncTask(this).execute(student, fina, regular);
        }
    }

    @Override
    public void onError(ServiceAsyncTask serviceAsyncTask, ServiceResponse.ServiceStatusCode error)
    {

        if( error == ServiceResponse.ServiceStatusCode.NO_CONNECTION )
        {
            activity.stopLoading();
            activity.showToast(R.string.connectivityFailed);
        } else if( serviceAsyncTask instanceof GetFinalsForsSubjectAsyncTask
                || serviceAsyncTask instanceof GetFinalsOfCourseAsyncTask )
        {
            loadedOne();
            activity.showToast(R.string.error_while_loading_finals);
        } else if( serviceAsyncTask instanceof UnsubscribeFromFinalAsyncTask )
        {
            activity.stopLoading();
            interactingPosition = -1;
            activity.showToast(R.string.error_when_unsubscribing);
        } else if( serviceAsyncTask instanceof SubscribeToFinalAsyncTask )
        {
            activity.stopLoading();
            interactingPosition = -1;
            activity.showToast(R.string.error_when_subscribing);
        } else if( serviceAsyncTask instanceof GetFinalsPeriodAsyncTask )
        {
            loadedOne();
            activity.showToast(R.string.error_when_loading_finals_period);
        }
    }

    @Override
    public void onSuccess(ServiceAsyncTask serviceAsyncTask, Object data)
    {
        if( serviceAsyncTask instanceof GetFinalsForsSubjectAsyncTask
                || serviceAsyncTask instanceof GetFinalsOfCourseAsyncTask )
        {
            loadedOne();
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
        } else if( serviceAsyncTask instanceof GetFinalsPeriodAsyncTask )
        {
            loadedOne();
            activity.setSubscriptionEnabled(((ActionPeriod)data).isDateInPeriod(new Date()));
        } else
        {
            activity.stopLoading();
            Final fina = finals.get(interactingPosition);
            if( fina.isSubscribed() )
            {
                fina.unsubscribe();
            } else if( serviceAsyncTask instanceof SubscribeToFinalAsyncTask )
            {
                FinalSubscriptionResult result = (FinalSubscriptionResult)data;
                fina.setSubscription(result.getSubscriptionId(), result.getSubscribedFree());
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
