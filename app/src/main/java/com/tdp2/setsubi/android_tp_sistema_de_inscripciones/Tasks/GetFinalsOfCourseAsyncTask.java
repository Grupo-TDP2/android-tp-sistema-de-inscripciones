package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Career;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Course;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Final;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Finals.FinalsServiceAPI;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

import java.util.List;

public class GetFinalsOfCourseAsyncTask extends ServiceAsyncTask<Object, Void, List<Final>>
{
    public GetFinalsOfCourseAsyncTask(ForeGroundListener listener) {
        super(listener);
    }

    @Override
    protected ServiceResponse<List<Final>> doRun(Object[] ts)
    {
        if( ts.length == 4
                && ts[0] instanceof Student && ts[1] instanceof Career
                && ts[2] instanceof Subject && ts[3] instanceof Course)
        {
            new FinalsServiceAPI().getFinalsForCourse((Student) ts[0], (Career) ts[1], (Subject) ts[2], (Course) ts[3]);
        }
        return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.PARAMETER_ERROR);
    }
}
