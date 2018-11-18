package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.MyCourse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.VoidJsonTransformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.ContentType;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestBuilder;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestMethod;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestPerformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestProperty;

public class PollService
{
    public ServiceResponse<Boolean> savePoll(Student student, MyCourse course, int calification, String opinion)
    {
        JsonObject poll = new JsonObject();
        poll.addProperty("course_id", course.getId());
        poll.addProperty("rate", calification);
        poll.addProperty("comment", opinion);
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("poll", poll);
        return new RequestPerformer<>(APIUriBuilder.getURIBuiled()
                .appendPath("students")
                .appendPath("me")
                .appendPath("polls")
                .toString(), new RequestBuilder(RequestMethod.POST)
                .addRequestProperty(RequestProperty.AUTHORIZATION, student.getAuthorization())
                .addRequestProperty(RequestProperty.CONTENT_TYPE, ContentType.JSON.getValue())
                .setBodyText(new Gson().toJson(jsonObject)),
                new VoidJsonTransformer()).perform();
    }
}
