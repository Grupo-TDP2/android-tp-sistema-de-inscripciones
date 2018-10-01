package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.LoginService;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

public interface LoginServieInterface
{
    ServiceResponse<Student> login(String email, String password);
}
