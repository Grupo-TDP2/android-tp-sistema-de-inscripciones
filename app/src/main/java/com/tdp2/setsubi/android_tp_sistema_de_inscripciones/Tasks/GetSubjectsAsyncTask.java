package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Department;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

import java.util.ArrayList;
import java.util.List;

public class GetSubjectsAsyncTask extends ServiceAsyncTask<Object, Void, List<Subject>>
{

    public GetSubjectsAsyncTask(ForeGroundListener listener) {
        super(listener);
    }

    @Override
    protected ServiceResponse<List<Subject>> doRun(Object[] ts)
    {
        if( !(ts.length == 1 && ts[0] instanceof Department && AppModel.getInstance().getSubjects() != null) )
        {
            return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.ERROR);
        }
        return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.SUCCESS, filter(AppModel.getInstance().getSubjects(),(Department) ts[0]));
    }

    private List<Subject> filter(List<Subject> subjects, Department department)
    {
        List<Subject> filtered = new ArrayList<>();
        int departmentId = department.getId();
        for( Subject subject : subjects )
        {
            if( subject.getDepartment().getId() == departmentId )
            {
                filtered.add(subject);
            }
        }
        return filtered;
    }
}
