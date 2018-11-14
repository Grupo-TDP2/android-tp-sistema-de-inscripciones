package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.MyCourse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Cursos.ServiceCoursesAPI;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

import java.util.List;

public class GetMyCoursesTask extends ServiceAsyncTask<Student, Void, List<MyCourse>>
{
    public GetMyCoursesTask(ForeGroundListener listener)
    {
        super(listener);
    }

    @Override
    protected ServiceResponse<List<MyCourse>> doRun(Student[] ts)
    {
        if( ts.length == 1 )
        {
            return new ServiceCoursesAPI().getCourses(ts[0]);
        }
        return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.PARAMETER_ERROR);
    }
}
