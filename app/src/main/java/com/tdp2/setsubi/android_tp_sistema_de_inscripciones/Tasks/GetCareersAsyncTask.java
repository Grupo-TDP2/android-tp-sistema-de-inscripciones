package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Career;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Cursos.ServiceCourserMock;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

import java.util.List;

public class GetCareersAsyncTask extends ServiceAsyncTask<Student,Void,List<Career>>
{
    public GetCareersAsyncTask(ForeGroundListener<List<Career>> listener)
    {
        super(listener);
    }

    @Override
    protected ServiceResponse<List<Career>> doRun(Student[] ts)
    {
        if( ts.length == 0 ) return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.ERROR);
        return new ServiceCourserMock().getCareers(ts[0]);
    }
}
