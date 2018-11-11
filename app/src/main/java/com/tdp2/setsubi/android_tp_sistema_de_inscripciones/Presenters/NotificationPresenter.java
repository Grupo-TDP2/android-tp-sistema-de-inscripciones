package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import android.content.Context;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.LoadingView;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.NotificationAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Notification;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.GetNotificationsAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.ServiceAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.SetSeenNotificationAsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NotificationPresenter implements NotificationAdapter.Listener, ServiceAsyncTask.ForeGroundListener
{
    public interface NotificationView extends LoadingView
    {
        void showNoNotifications();
        void updatedNotifications();
        void goToMyFinals();
        void goToMyCourses();
        Context getContext();
    }

    private List<Notification> array = new ArrayList<>();
    private NotificationView view;

    public NotificationPresenter(NotificationView view)
    {
        this.view = view;
    }

    public List<Notification> getDataSet()
    {
        return array;
    }

    @Override
    public void onClickedActionAtIndex(int index)
    {
        Notification notification = array.get(index);
        if( notification.getType() == Notification.Type.UNSUBSCRIBE_EXAM )
        {
            view.goToMyFinals();
        } else
        {
            view.goToMyCourses();
        }
    }

    public void loadNotifications()
    {
        new GetNotificationsAsyncTask(this).execute(view.getContext());
    }

    @Override
    public void onError(ServiceAsyncTask serviceAsyncTask, ServiceResponse.ServiceStatusCode error)
    {
        if( serviceAsyncTask instanceof GetNotificationsAsyncTask )
        {
            view.stopLoading();
        }
    }

    @Override
    public void onSuccess(ServiceAsyncTask serviceAsyncTask, Object data)
    {
        if( serviceAsyncTask instanceof GetNotificationsAsyncTask )
        {
            view.stopLoading();
            array.clear();
            List<Notification> recieved = (List<Notification>) data;
            array.addAll(recieved);
            Collections.sort(array, (o1, o2) -> -1*o1.getReceivedDate().compareTo(o2.getReceivedDate()));
            view.updatedNotifications();
            if( array.size() == 0 )
            {
                view.showNoNotifications();
            } else
            {
                for( Notification notification : array )
                {
                    if( !notification.isSeen() )
                    {
                        new SetSeenNotificationAsyncTask(this)
                                .execute(view.getContext(), notification.getUid(), true);
                    }
                }
            }
        }
    }

    @Override
    public void onStartingAsyncTask(ServiceAsyncTask serviceAsyncTask)
    {
        if( serviceAsyncTask instanceof GetNotificationsAsyncTask )
        {
            view.startLoading();
        }
    }
}
