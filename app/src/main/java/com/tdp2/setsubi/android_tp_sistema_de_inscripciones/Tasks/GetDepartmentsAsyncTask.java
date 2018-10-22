package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Career;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Cursos.ServiceCoursesAPI;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

import java.util.List;

public class GetDepartmentsAsyncTask extends ServiceAsyncTask<Object,Void,List<Subject>>
{
    public GetDepartmentsAsyncTask(ForeGroundListener listener) {
        super(listener);
    }

    @Override
    protected ServiceResponse<List<Subject>> doRun(Object[] ts)
    {
        if( !(ts.length == 2 && ts[0] instanceof Student && ts[1] instanceof Career) )
        {
            return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.ERROR);
        }
        return new ServiceCoursesAPI().getSubjects((Student)ts[0], (Career)ts[1]);
    }
}
