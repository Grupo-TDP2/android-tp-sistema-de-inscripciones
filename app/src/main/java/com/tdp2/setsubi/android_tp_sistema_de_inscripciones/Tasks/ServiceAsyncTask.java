package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks;

import android.os.AsyncTask;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

public abstract class ServiceAsyncTask<Params, Progress, T> extends AsyncTask<Params, Progress, ServiceResponse<T>>
{
    public interface ForeGroundListener<R>
    {
        void onError(ServiceResponse.ServiceStatusCode error);
        void onSuccess(R data);
        void onStartingAsyncTask();
    }
    private ForeGroundListener<T> listener;

    public ServiceAsyncTask(ForeGroundListener<T> listener)
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
        listener.onStartingAsyncTask();
    }

    protected void onPostExecute(ServiceResponse<T> response)
    {
        if( response.getStatusCode() == ServiceResponse.ServiceStatusCode.SUCCESS)
        {
            listener.onSuccess(response.getServiceResponse());
        } else
        {
            listener.onError(response.getStatusCode());
        }
    }

    protected abstract ServiceResponse<T> doRun(Params[] ts);
}