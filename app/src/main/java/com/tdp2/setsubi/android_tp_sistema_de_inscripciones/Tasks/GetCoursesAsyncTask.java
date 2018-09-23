package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Career;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.ClassModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Course;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Department;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Cursos.ServiceCourserMock;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

import java.util.List;

public class GetCoursesAsyncTask extends ServiceAsyncTask<Object, Void, List<Course>>
{
    public GetCoursesAsyncTask(ForeGroundListener<List<Course>> listener) {
        super(listener);
    }

    @Override
    protected ServiceResponse<List<Course>> doRun(Object[] ts)
    {
        if( !(ts.length == 2 && ts[0] instanceof Student && ts[1] instanceof ClassModel))
        {
            return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.ERROR);
        }
        return new ServiceCourserMock().getCourses((Student) ts[0],(ClassModel)ts[1]);
    }
}
