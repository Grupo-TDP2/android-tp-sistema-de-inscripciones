package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.LoginService;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonLoginTransformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.APIUriBuilder;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.ContentType;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestBuilder;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestMethod;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestPerformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestProperty;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

public class LoginService implements LoginServieInterface
{
    @Override
    public ServiceResponse<Student> login(String email, String password)
    {
        JsonObject object = JsonLoginTransformer.getPostLogin(email, password);
        return new RequestPerformer<>(APIUriBuilder.getURIBuiled().appendPath("student_sessions").toString(),
                new RequestBuilder(RequestMethod.POST, new Gson().toJson(object))
                        .addRequestProperty(RequestProperty.CONTENT_TYPE.getKey(), ContentType.JSON.getValue()),
                new JsonLoginTransformer())
                .perform();
    }

}
