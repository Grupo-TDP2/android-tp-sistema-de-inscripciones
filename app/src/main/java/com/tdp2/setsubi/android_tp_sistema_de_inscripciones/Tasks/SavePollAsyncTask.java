package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.MyCourse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.PollService;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

public class SavePollAsyncTask extends ServiceAsyncTask<Object, Void, Boolean>
{

    public SavePollAsyncTask(ForeGroundListener listener) {
        super(listener);
    }

    @Override
    protected ServiceResponse<Boolean> doRun(Object[] ts)
    {
        if( ts.length == 4
                && ts[0] instanceof Student
                && ts[1] instanceof MyCourse
                && ts[2] instanceof Integer
                && ts[3] instanceof String)
        {
            return new PollService().savePoll((Student) ts[0],(MyCourse) ts[1],(Integer) ts[2],(String) ts[3]);
        }
        return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.PARAMETER_ERROR);
    }
}
