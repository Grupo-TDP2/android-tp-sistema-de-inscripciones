package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Career;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Department;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Cursos.ServiceCourserMock;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

import java.util.List;

public class GetDepartmentsAsyncTask extends ServiceAsyncTask<Object,Void,List<Department>>
{
    public GetDepartmentsAsyncTask(ForeGroundListener<List<Department>> listener) {
        super(listener);
    }

    @Override
    protected ServiceResponse<List<Department>> doRun(Object[] ts)
    {
        if(ts.length != 2 && !(ts[0] instanceof Student && ts[1] instanceof Career) )
        {
            return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.ERROR);
        }
        return new ServiceCourserMock().getDepartments((Student)ts[0], (Career)ts[1]);
    }
}
