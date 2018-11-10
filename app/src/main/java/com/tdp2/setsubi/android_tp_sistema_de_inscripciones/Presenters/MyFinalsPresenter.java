package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import android.util.Log;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.MyFinalsActivity;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Final;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.GetMyFinalsAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.ServiceAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.UnsubscribeFromFinalAsyncTask;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

public class MyFinalsPresenter implements MyFinalsActivity.Presenter,
        ServiceAsyncTask.ForeGroundListener
{
    private MyFinalsActivity activity;
    private List<Final> finals = new ArrayList<>();
    private int unsubcribinPosition = -1;
    public MyFinalsPresenter(MyFinalsActivity activity)
    {
        this.activity = activity;
    }


    @Override
    public List<Final> getDataSet()
    {
        return finals;
    }

    @Override
    public void loadMyFinals()
    {
        new GetMyFinalsAsyncTask(this).execute(AppModel.getInstance().getStudent());
    }

    @Override
    public void onUnsubscribe(int position)
    {
        if( unsubcribinPosition != -1 )
        {
            return;
        }

        if( position < finals.size() )
        {
            unsubcribinPosition = position;
            new UnsubscribeFromFinalAsyncTask(this).execute(AppModel.getInstance().getStudent(),
                    finals.get(position));
        }
    }

    @Override
    public void onError(ServiceAsyncTask serviceAsyncTask, ServiceResponse.ServiceStatusCode error)
    {
        activity.stopLoading();
        if( error == ServiceResponse.ServiceStatusCode.NO_CONNECTION )
        {
            activity.showToast(R.string.connectivityFailed);
        } else if( serviceAsyncTask instanceof GetMyFinalsAsyncTask )
        {
            activity.showToast(R.string.error_while_loading_finals);
        } else {
            unsubcribinPosition = -1;
            activity.showToast(R.string.error_when_unsubscribing);
        }
    }

    @Override
    public void onSuccess(ServiceAsyncTask serviceAsyncTask, Object data)
    {
        activity.stopLoading();
        if( serviceAsyncTask instanceof GetMyFinalsAsyncTask )
        {
            List<Final> finals = (List<Final>) data;
            this.finals.clear();
            this.finals.addAll(filterPossible(finals));

            if( this.finals.size() == 0 )
            {
                activity.showNoFinalsFound();
            } else {
                Collections.sort(this.finals, new Comparator<Final>() {
                    @Override
                    public int compare(Final o1, Final o2) {
                        return o1.compare(o2);
                    }
                });
                activity.notifyDataSetChanged();
            }
        } else
        {
            if( unsubcribinPosition < finals.size() )
            {
                finals.remove(unsubcribinPosition);
                activity.notifyRemoved(unsubcribinPosition);
                activity.showToast(R.string.unsubribed_final_success);
                if( this.finals.size() == 0 )
                {
                    activity.showNoFinalsFound();
                }
            }
            unsubcribinPosition = -1;
        }
    }

    private Collection<? extends Final> filterPossible(List<Final> finals) {
        ListIterator<Final> iterator = finals.listIterator();
        while (iterator.hasNext())
        {
            Final next = iterator.next();
            if( next.hasAlreadyPassedDate() && !next.isFinalGiven())
            {
                Log.d("FINAL","Filtered a final because it can't be enroled by student");
                iterator.remove();
            }
        }
        return finals;
    }

    @Override
    public void onStartingAsyncTask(ServiceAsyncTask serviceAsyncTask) {
        activity.startLoading();
    }
}
