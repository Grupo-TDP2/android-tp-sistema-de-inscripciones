package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Finals;

import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Career;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Final;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonArrayTransformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonFinalTransformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonMyFinalTransformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonSubscibeFinal;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonUnsubscibeFinal;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.APIUriBuilder;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.ContentType;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestBuilder;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestMethod;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestPerformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestProperty;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

import java.util.ArrayList;
import java.util.List;

public class FinalsServiceAPI implements FinalsServiceInterface
{
    private Uri.Builder getFinalInscriptionURI()
    {
        return APIUriBuilder.getURIBuiled().appendPath("students")
                .appendPath("me").appendPath("student_exams");
    }

    @Override
    public ServiceResponse<List<Final>> getFinalsForSubject(Student student, Career career, Subject subject)
    {
        List<Final> finals = new ArrayList<>();
        ServiceResponse<List<Final>> response;
        for(Integer course : subject.getCourses() )
        {
            response = getFinals(student.getAuthorization(), career.getId(), subject.getId(), course);
            if( response.getStatusCode() == ServiceResponse.ServiceStatusCode.SUCCESS)
            {
                finals.addAll(response.getServiceResponse());
            } else
            {
                return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.ERROR);
            }
        }
        return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.SUCCESS, finals);
    }

    private ServiceResponse<List<Final>> getFinals(String token, int career, int subject, int course)
    {
        return new RequestPerformer<>(APIUriBuilder.getURIBuiled()
                .appendPath("course_of_studies").appendPath(String.valueOf(career))
                .appendPath("subjects").appendPath(String.valueOf(subject))
                .appendPath("courses").appendPath(String.valueOf(course)).build().toString(),
                new RequestBuilder(RequestMethod.GET)
        .addRequestProperty(RequestProperty.AUTHORIZATION, token),
                new JsonArrayTransformer<>(new JsonFinalTransformer())).perform();
    }

    @Override
    public ServiceResponse<List<Final>> getStudentFinals(Student student)
    {
        return new RequestPerformer<>(getFinalInscriptionURI().build().toString(),
                new RequestBuilder(RequestMethod.GET)
                        .addRequestProperty(RequestProperty.AUTHORIZATION, student.getAuthorization()),
                new JsonArrayTransformer<>(new JsonMyFinalTransformer())).perform();
    }

    @Override
    public ServiceResponse<Integer> subscribe(Student student, Final fina)
    {
        JsonObject exam = new JsonObject();
        JsonObject container = new JsonObject();
        exam.addProperty("exam_id", fina.getId());
        exam.addProperty("condition", getCondition(fina.isSupportsLibre()));
        container.add("student_exam", exam);
        return new RequestPerformer<>(getFinalInscriptionURI().build().toString(),
                new RequestBuilder(RequestMethod.POST)
                        .addRequestProperty(RequestProperty.AUTHORIZATION, student.getAuthorization())
                .addRequestProperty(RequestProperty.CONTENT_TYPE, ContentType.JSON.getValue())
                .setBodyText(new Gson().toJson(container)),
                new JsonSubscibeFinal()).perform();
    }

    private String getCondition(boolean supportsLibre) {
        if( supportsLibre )
        {
            return "libre";
        }
        return "regular";
    }

    @Override
    public ServiceResponse<Integer> unsubscribe(Student student, Final fina)
    {
        return new RequestPerformer<>(getFinalInscriptionURI().appendPath(String.valueOf(fina.getId()))
                .toString(),
                new RequestBuilder(RequestMethod.DELETE)
                        .addRequestProperty(RequestProperty.AUTHORIZATION, student.getAuthorization()),
                new JsonUnsubscibeFinal()).perform();
    }
}
