package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
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
            if( authorization != null )
            {
                return new Student(4, "Estudiante", "Ejemplo", authorization);
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
