package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.ApprovedSubject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.SubjectsServices;

import java.util.List;

public class GetApprovedSubject extends ServiceAsyncTask<Student,Void,List<ApprovedSubject>>
{
    public GetApprovedSubject(ForeGroundListener listener) {
        super(listener);
    }

    @Override
    protected ServiceResponse<List<ApprovedSubject>> doRun(Student[] ts)
    {
        if( ts.length == 1 )
        {
            return new SubjectsServices().getApprovedSubjects(ts[0]);
        }
        return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.PARAMETER_ERROR);
    }
}
