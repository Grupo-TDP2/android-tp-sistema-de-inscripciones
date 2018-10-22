package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Final;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Finals.FinalsServiceAPI;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

public class UnsubscribeFromFinalAsyncTask extends ServiceAsyncTask<Object, Void, Boolean>
{
    public UnsubscribeFromFinalAsyncTask(ForeGroundListener listener) {
        super(listener);
    }

    @Override
    protected ServiceResponse<Boolean> doRun(Object[] ts) {
        if( ts.length == 2 && ts[0] instanceof Student &&
                ts[1] instanceof Final )
        {
            return new FinalsServiceAPI()
                    .unsubscribe((Student) ts[0], (Final) ts[1]);
        }
        return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.CONFLICT);
    }
}
