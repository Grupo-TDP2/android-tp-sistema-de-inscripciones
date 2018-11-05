package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.EnrolmentResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils;

public class JsonEnrolmentTransformer extends JsonTransformer<EnrolmentResponse>
{
    @Override
    public EnrolmentResponse transform(JsonElement object)
    {
        if( object.isJsonObject() )
        {
            int courseId, enrollId;
            JsonObject jsonObject = object.getAsJsonObject();
            if( JsonUtils.isInt(jsonObject, JsonKeys.ID) )
            {
                enrollId = JsonUtils.getInt(jsonObject, JsonKeys.ID);
            } else return null;

            if( jsonObject.has(JsonKeys.COURSE) && jsonObject.get(JsonKeys.COURSE).isJsonObject() )
            {
                JsonObject course = jsonObject.getAsJsonObject(JsonKeys.COURSE);
                if( JsonUtils.isInt(course, JsonKeys.ID) )
                {
                    courseId = JsonUtils.getInt(course, JsonKeys.ID);
                } else return null;
            } else return null;

            if( JsonUtils.isString(jsonObject, JsonKeys.TYPE) )
            {
                return new EnrolmentResponse(courseId, enrollId, getType(JsonUtils.getString(jsonObject, JsonKeys.TYPE)));
            }
        }
        return null;
    }

    private EnrolmentResponse.EnrolmentType getType(String string) {
        switch (string)
        {
            case "conditional":
                return EnrolmentResponse.EnrolmentType.CONDITIONAL;
        }
        return EnrolmentResponse.EnrolmentType.REGULAR;
    }
}
