package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Career;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.GetCareersAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.ServiceAsyncTask;

import java.util.ArrayList;
import java.util.List;

public class CareersActivityPresenter implements ServiceAsyncTask.ForeGroundListener<List<Career>>
{
    private View view;
    private List<Career> careers = new ArrayList<>();
    private ArrayList<String> dataset = new ArrayList<>();

    public CareersActivityPresenter(View view) {
        this.view = view;
    }

    public ArrayList<String> getDataset() {
        return dataset;
    }

    public void loadCareers()
    {
        new GetCareersAsyncTask(this).execute(AppModel.getInstance().getStudent());
    }

    public void onSelectedCarreer(int position)
    {
        AppModel.getInstance().selectedCareer(careers.get(position));
        view.navigateToDepartments();
    }

    @Override
    public void onError(ServiceResponse.ServiceStatusCode error) {
        view.failedToLoadCareers();
    }

    @Override
    public void onSuccess(List<Career> data)
    {
        careers = data;
        dataset.clear();
        AppModel.getInstance().setStudentCareers(data);
        for( Career career : careers)
        {
            dataset.add(career.getName());
        }
        view.updateData();
    }

    @Override
    public void onStartingAsyncTask()
    {

    }

    public interface View {

        void updateData();

        void navigateToDepartments();

        void failedToLoadCareers();
    }
}
