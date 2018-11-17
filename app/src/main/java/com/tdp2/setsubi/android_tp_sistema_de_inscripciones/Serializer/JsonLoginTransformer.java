package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import android.content.Intent;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.AnyNullCheck;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonGetter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils;

public class JsonLoginTransformer extends JsonTransformer<Student>
{
    @Override
    public Student transform(JsonElement object)
    {
        if(object.isJsonObject())
        {
            JsonObject jsonObject = object.getAsJsonObject();
            String authorization = null;
            if( JsonUtils.isString(jsonObject, JsonKeys.ACCESS_TOKEN) )
            {
                authorization = JsonUtils.getString(jsonObject, JsonKeys.ACCESS_TOKEN);
            }
            JsonGetter user = new JsonGetter(jsonObject).atObject(JsonKeys.USER);
            Integer id = user.asInt(JsonKeys.ID), priority = user.asInt(JsonKeys.PRIORITY);
            String name = user.asString(JsonKeys.FIRST_NAME), lastName = user.asString(JsonKeys.LAST_NAME),
            padron = user.asString(JsonKeys.SCHOOL_DOCUMENT_NUMBER);
            String email = user.asString(JsonKeys.EMAIL);
            if(!AnyNullCheck.any(id, email, name, lastName, authorization, priority, padron))
            {
                Student student = new Student(id, email,name, lastName, authorization);
                student.setPadron(padron);
                student.setPriority(priority);
                return student;
            }
        }
        return null;
    }

    public static JsonObject getPostLogin(String email, String password) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(JsonKeys.EMAIL, email);
        jsonObject.addProperty(JsonKeys.PASSWORD, password);
        return jsonObject;
    }
}
