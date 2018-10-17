package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Career;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Course;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Cursos.ServiceCoursesAPI;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

import java.util.List;

public class GetCoursesAsyncTask extends ServiceAsyncTask<Object, Void, List<Course>>
{
    public GetCoursesAsyncTask(ForeGroundListener listener) {
        super(listener);
    }

    @Override
    protected ServiceResponse<List<Course>> doRun(Object[] ts)
    {
        if( !(ts.length == 3 && ts[0] instanceof Student && ts[1] instanceof Career && ts[2] instanceof Subject))
        {
            return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.ERROR);
        }
        return new ServiceCoursesAPI().getCourses((Student) ts[0],(Career)ts[1], (Subject)ts[2]);
    }
}
