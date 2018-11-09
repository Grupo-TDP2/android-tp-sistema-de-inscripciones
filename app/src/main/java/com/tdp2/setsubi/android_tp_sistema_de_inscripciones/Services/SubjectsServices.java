package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.ApprovedSubject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonApprovedSubject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonArrayTransformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestBuilder;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestMethod;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestPerformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestProperty;

import java.util.List;

public class SubjectsServices
{
    public ServiceResponse<List<ApprovedSubject>> getApprovedSubjects(Student student)
    {
        return new RequestPerformer<>(APIUriBuilder.getURIBuiled()
                .appendPath("students").appendPath("me").appendPath("approved_subjects")
                .toString(),
                new RequestBuilder(RequestMethod.GET)
                .addRequestProperty(RequestProperty.AUTHORIZATION, student.getAuthorization()),
                new JsonArrayTransformer<>(new JsonApprovedSubject())).perform();
    }
}
