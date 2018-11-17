package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.VoidJsonTransformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.ContentType;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestBuilder;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestMethod;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestPerformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestProperty;

public class UserService
{
    public ServiceResponse<Boolean> saveUserData(Student student)
    {
        JsonObject studentJson = new JsonObject();
        studentJson.addProperty(JsonKeys.FIRST_NAME, student.getName());
        studentJson.addProperty(JsonKeys.LAST_NAME, student.getLastname());
        studentJson.addProperty(JsonKeys.EMAIL, student.getEmail());
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("student", studentJson);
        return new RequestPerformer<>(APIUriBuilder.getURIBuiled()
                .appendPath("students")
                .appendPath("me").toString(), new RequestBuilder(RequestMethod.PATCH)
                .addRequestProperty(RequestProperty.AUTHORIZATION, student.getAuthorization())
                .addRequestProperty(RequestProperty.CONTENT_TYPE, ContentType.JSON.getValue())
                .setBodyText(new Gson().toJson(jsonObject)),
                new VoidJsonTransformer()).perform();
    }
}
