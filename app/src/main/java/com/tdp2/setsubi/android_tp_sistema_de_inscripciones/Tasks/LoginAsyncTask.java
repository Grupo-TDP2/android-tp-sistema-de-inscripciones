package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.LoginService.LoginService;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

public class LoginAsyncTask extends ServiceAsyncTask<String,Void,Student>
{
    public LoginAsyncTask(ForeGroundListener listener) {
        super(listener);
    }

    @Override
    protected ServiceResponse<Student> doRun(String[] ts)
    {
        if( ts.length != 2 ) return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.ERROR);
        return new LoginService().login(ts[0],ts[1]);
    }
}
