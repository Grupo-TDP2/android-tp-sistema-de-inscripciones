package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Career;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Final;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Finals.FinalsServiceAPI;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

import java.util.List;

public class GetFinalsForsSubjectAsyncTask extends ServiceAsyncTask<Object,Void,List<Final>>
{

    public GetFinalsForsSubjectAsyncTask(ForeGroundListener listener)
    {
        super(listener);
    }

    @Override
    protected ServiceResponse<List<Final>> doRun(Object[] ts)
    {
        if( ts.length == 3 && ts[0] instanceof Student &&
                ts[1] instanceof Career && ts[2] instanceof Subject )
        {
            return new FinalsServiceAPI()
                    .getFinalsForSubject((Student) ts[0], (Career) ts[1], (Subject) ts[2]);
        }
        return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.CONFLICT);
    }
}
