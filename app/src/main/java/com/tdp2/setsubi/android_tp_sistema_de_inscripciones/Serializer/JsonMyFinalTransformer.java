package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Final;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Sede;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils;

import java.text.ParseException;
import java.util.Date;

import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.ID;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils.getInt;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils.isInt;

public class JsonMyFinalTransformer extends JsonTransformer<Final>
{
    @Override
    public Final transform(JsonElement object)
    {
        if( object.isJsonObject() )
        {
            int id, subscriptionId, aula;
            boolean supportsLibre = false, approvedCourse = false;
            Boolean isLibre;
            String catedra = null;
            Subject subject = null;
            Date finalDate = null;
            Sede sede;
            JsonObject finalObject = object.getAsJsonObject();
            if(JsonUtils.isString(finalObject, JsonKeys.CONDITION))
            {
                switch (JsonUtils.getString(finalObject, JsonKeys.CONDITION))
                {
                    case "free":
                        isLibre = true;
                        break;
                    default:
                        isLibre = false;
                }
            } else return null;
            if( JsonUtils.isInt(finalObject, JsonKeys.ID) )
            {
                subscriptionId = JsonUtils.getInt(finalObject, JsonKeys.ID);
            } else return null;

            if( finalObject.has(JsonKeys.EXAM) && finalObject.get(JsonKeys.EXAM).isJsonObject() )
            {
                JsonObject exam = finalObject.getAsJsonObject(JsonKeys.EXAM);
                if( isInt(exam, ID) )
                {
                    id = getInt(exam, ID);
                } else return null;
                aula = JsonTransformHelper.getAula(exam);
                sede = JsonTransformHelper.getSede(exam);
                if( sede == null || aula == -1 ) return null;

                if( JsonUtils.isString(exam, JsonKeys.DATE_TIME) )
                {
                    try {
                        finalDate = JsonTransformHelper.getDate(JsonUtils.getString(exam, JsonKeys.DATE_TIME));
                    } catch (ParseException e) {
                        return null;
                    }
                }
                if( exam.has(JsonKeys.COURSE) && exam.get(JsonKeys.COURSE).isJsonObject() )
                {
                    JsonObject course = exam.getAsJsonObject(JsonKeys.COURSE);
                    catedra = JsonTransformHelper.getCatedra(course);
                    approvedCourse = JsonTransformHelper.hasApprovedCourse(course);

                    if(JsonUtils.isObject(course, JsonKeys.SUBJECT))
                    {
                        JsonObject jsonSubject = course.getAsJsonObject(JsonKeys.SUBJECT);
                        subject = getSubject(jsonSubject);
                    }

                    if(JsonUtils.isBool(course, JsonKeys.ACCEPTS_FREE_CONDITION))
                    {
                        supportsLibre = JsonUtils.getBool(course, JsonKeys.ACCEPTS_FREE_CONDITION);
                    }
                }
                if( finalDate == null || catedra == null || subject == null ) return null;
            } else return null;

            Final fina = new Final(id, supportsLibre, approvedCourse,
                subject,
                catedra,
                finalDate, sede, aula);
            fina.setSubscription(subscriptionId, isLibre);
            return fina;
        }
        return null;
    }

    private Subject getSubject(JsonObject jsonObject)
    {
        return new JsonSubjectTransformer().transform(jsonObject);
    }


}
