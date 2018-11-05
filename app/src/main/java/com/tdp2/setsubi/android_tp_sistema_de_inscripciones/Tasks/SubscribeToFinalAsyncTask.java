package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Final;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Finals.FinalsServiceAPI;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

public class SubscribeToFinalAsyncTask extends ServiceAsyncTask<Object, Void, Integer>
{
    public SubscribeToFinalAsyncTask(ForeGroundListener listener)
    {
        super(listener);
    }

    @Override
    protected ServiceResponse<Integer> doRun(Object[] ts)
    {
        if( ts.length == 3 && ts[0] instanceof Student &&
                ts[1] instanceof Final
                && ts[2] instanceof Boolean )
        {
            return new FinalsServiceAPI()
                    .subscribe((Student) ts[0], (Final) ts[1], (boolean)ts[2]);
        }
        return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.CONFLICT);
    }
}
