package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Final;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Finals.FinalsServiceAPI;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

import java.util.List;

public class GetMyFinalsAsyncTask extends ServiceAsyncTask<Student, Void, List<Final>>
{
    public GetMyFinalsAsyncTask(ForeGroundListener listener) {
        super(listener);
    }

    @Override
    protected ServiceResponse<List<Final>> doRun(Student[] ts) {
        if( ts.length == 1 )
        {
            return new FinalsServiceAPI()
                    .getStudentFinals(ts[0]);
        }
        return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.CONFLICT);
    }
}
