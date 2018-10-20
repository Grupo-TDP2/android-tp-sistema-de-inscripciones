package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Final;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Sede;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils;

import java.text.ParseException;
import java.util.Date;

public class JsonFinalTransformer extends JsonTransformer<Final>
{
    @Override
    public Final transform(JsonElement object) {
        if( object.isJsonObject() )
        {
            int id, aula, subscriptionId = -1;
            boolean supportsLibre = false, isSubscribed, approvedCourse;
            Date finalDate;
            Sede sede;
            String catedra;
            JsonObject finalObject = object.getAsJsonObject();
            if( JsonUtils.isBool(finalObject, JsonKeys.INSCRIBED) )
            {
                isSubscribed = JsonUtils.getBool(finalObject, JsonKeys.INSCRIBED);
            } else return null;

            if( finalObject.has(JsonKeys.COURSE) && finalObject.get(JsonKeys.COURSE).isJsonObject())
            {
                JsonObject course = finalObject.getAsJsonObject(JsonKeys.COURSE);
                catedra = JsonTransformHelper.getCatedra(course);
                boolean subscibedToCourse = getSubscribedToCourse(course);
                approvedCourse = JsonTransformHelper.hasApprovedCourse(course);

                if( JsonUtils.isBool(course, JsonKeys.ACCEPTS_FREE_CONDITION) )
                {
                    supportsLibre = JsonUtils.getBool(course, JsonKeys.ACCEPTS_FREE_CONDITION)
                            && (!subscibedToCourse || !approvedCourse);
                }
            } else return null;

            if( JsonUtils.isObject(finalObject, JsonKeys.REGISTRATION) )
            {
                JsonObject registration = finalObject.getAsJsonObject(JsonKeys.REGISTRATION);
                if( JsonUtils.isInt(registration, JsonKeys.ID) )
                {
                    subscriptionId = JsonUtils.getInt(registration, JsonKeys.ID);
                }
            }
            if( JsonUtils.isInt(finalObject, JsonKeys.ID) )
            {
                id = JsonUtils.getInt(finalObject, JsonKeys.ID);
            } else return null;

            aula = JsonTransformHelper.getAula(finalObject);
            sede = JsonTransformHelper.getSede(finalObject);
            if( JsonUtils.isString(finalObject, JsonKeys.DATE_TIME) )
            {
                try {
                    finalDate = JsonTransformHelper.getDate(JsonUtils.getString(finalObject, JsonKeys.DATE_TIME));
                } catch (ParseException e) {
                    return null;
                }
            } else return null;


            if( catedra == null ) return null;
            Final fina = new Final(id, isSubscribed, supportsLibre, approvedCourse,
                    AppModel.getInstance().getSelecteSubject(),
                    catedra,
                    finalDate, sede, aula);
            if( subscriptionId != -1 ) fina.setSubscriptionId(subscriptionId);
            return fina;
        }
        return null;
    }

    private String getCatedra(JsonObject course)
    {
        if( JsonUtils.isString(course, JsonKeys.NAME) )
        {
            return JsonUtils.getString(course, JsonKeys.NAME);
        }
        return null;
    }

    private boolean getSubscribedToCourse(JsonObject course) {
        if( JsonUtils.isBool(course, JsonKeys.INSCRIBED) )
        {
            return JsonUtils.getBool(course, JsonKeys.INSCRIBED);
        }
        return false;
    }
    /**
     * [
     {
     "id": 3,
     "exam_type": "final",
     "date_time": "2018-12-12T17:00:00.000Z",
     "final_exam_week": {
     "id": 13,
     "date_start_week": "2018-12-10",
     "year": "2018"
     },
     "course": {
     "id": 39,
     "name": "003",
     "vacancies": 0,
     "accept_free_condition_exam": false,
     "inscribed?": true,
     "enrolment": {
     "id": 34,
     "type": "normal",
     "student_id": 16,
     "course_id": 39,
     "created_at": "2018-10-15T22:11:42.210Z",
     "updated_at": "2018-10-20T03:45:48.003Z",
     "status": "approved",
     "final_qualification": null,
     "partial_qualification": 8
     }
     },
     "classroom": {
     "id": 29,
     "floor": "3",
     "number": "22",
     "building": {
     "id": 15,
     "name": "PC",
     "address": "Av. Paseo Colón 850",
     "postal_code": "1063",
     "city": "CABA"
     }
     }
     }
     ]
     */
}
