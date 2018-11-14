package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.ActionPeriod;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Finals.FinalsServiceAPI;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

public class GetFinalsPeriodAsyncTask extends ServiceAsyncTask<Student,Void,ActionPeriod>
{

    public GetFinalsPeriodAsyncTask(ForeGroundListener listener) {
        super(listener);
    }

    @Override
    protected ServiceResponse<ActionPeriod> doRun(Student[] ts)
    {
        if( ts.length == 1 )
        {
            return new FinalsServiceAPI().getFinalsSubscriptionPeriod(ts[0]);
        }
        return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.PARAMETER_ERROR);
    }
}
