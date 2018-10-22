package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Finals;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Career;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Final;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

import java.util.List;

public interface FinalsServiceInterface
{
    ServiceResponse<List<Final>> getFinalsForSubject(Student student, Career career, Subject subject);
    ServiceResponse<List<Final>> getStudentFinals(Student student);
    ServiceResponse<Integer> subscribe(Student student, Final fina);
    ServiceResponse<Boolean> unsubscribe(Student student, Final fina);
}
