package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks;

import android.os.AsyncTask;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

public abstract class ServiceAsyncTask<Params, Progress, T> extends AsyncTask<Params, Progress, ServiceResponse<T>>
{
    public interface ForeGroundListener
    {
        void onError(ServiceAsyncTask serviceAsyncTask, ServiceResponse.ServiceStatusCode error);
        void onSuccess(ServiceAsyncTask serviceAsyncTask, Object data);
        void onStartingAsyncTask(ServiceAsyncTask serviceAsyncTask);
    }
    private ForeGroundListener listener;

    public ServiceAsyncTask(ForeGroundListener listener)
    {
        this.listener = listener;
    }

    @SafeVarargs
    @Override
    protected final ServiceResponse<T> doInBackground(Params... ts) {
        return doRun(ts);
    }

    protected void onPreExecute()
    {
        listener.onStartingAsyncTask(this);
    }

    protected void onPostExecute(ServiceResponse<T> response)
    {
        if( response.getStatusCode() == ServiceResponse.ServiceStatusCode.SUCCESS)
        {
            listener.onSuccess(this, response.getServiceResponse());
        } else
        {
            listener.onError(this, response.getStatusCode());
        }
    }

    protected abstract ServiceResponse<T> doRun(Params[] ts);
}
