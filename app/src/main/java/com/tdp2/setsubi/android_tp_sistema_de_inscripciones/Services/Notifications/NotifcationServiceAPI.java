package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Notifications;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.VoidJsonTransformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.APIUriBuilder;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.ContentType;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestBuilder;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestMethod;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestPerformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestProperty;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

public class NotifcationServiceAPI implements NotificationsService
{
    @Override
    public ServiceResponse<Boolean> sendToken(Student student, String token)
    {
        JsonObject jsonToken = new JsonObject();
        jsonToken.addProperty("device_token", token);
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("student", jsonToken);
        return new RequestPerformer<>(APIUriBuilder.getURIBuiled()
                .appendPath("students")
                .appendPath("me").toString(), new RequestBuilder(RequestMethod.PATCH)
                .addRequestProperty(RequestProperty.AUTHORIZATION, student.getAuthorization())
                .addRequestProperty(RequestProperty.CONTENT_TYPE, ContentType.JSON.getValue())
                .setBodyText(new Gson().toJson(jsonObject)),
                new VoidJsonTransformer()).perform();
    }
}
