package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Career;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.ClassModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Department;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Cursos.ServiceCourserMock;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

import java.util.List;

public class GetMateriasAsyncTask extends ServiceAsyncTask<Object, Void, List<ClassModel>>
{

    public GetMateriasAsyncTask(ForeGroundListener<List<ClassModel>> listener) {
        super(listener);
    }

    @Override
    protected ServiceResponse<List<ClassModel>> doRun(Object[] ts)
    {
        if( !(ts.length == 3 && ts[0] instanceof Student && ts[1] instanceof Career && ts[2] instanceof Department) )
        {
            return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.ERROR);
        }
        return new ServiceCourserMock().getClasses((Student) ts[0],(Career) ts[1],(Department) ts[2]);
    }
}
