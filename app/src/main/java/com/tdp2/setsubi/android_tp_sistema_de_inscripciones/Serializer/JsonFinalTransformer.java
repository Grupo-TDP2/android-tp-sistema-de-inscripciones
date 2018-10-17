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
    //TODO READ SUBSCRIBED AND READ IF APPROVED COURSE
    @Override
    public Final transform(JsonElement object) {
        if( object.isJsonObject() )
        {
            int id, aula;
            boolean supportsLibre = false, isSubscribed = false;
            Date finalDate = null;
            Sede sede;
            JsonObject finalObject = object.getAsJsonObject();
            if( finalObject.has(JsonKeys.COURSE) && finalObject.get(JsonKeys.COURSE).isJsonObject())
            {
                JsonObject course = finalObject.getAsJsonObject(JsonKeys.COURSE);
                boolean subscibedToCourse = false, approvedCourse = false;
                if( JsonUtils.isBool(course, JsonKeys.INSCRIBED) )
                {
                    subscibedToCourse = JsonUtils.getBool(course, JsonKeys.INSCRIBED);
                }
                if( JsonUtils.isBool(course, JsonKeys.ACCEPTS_FREE_CONDITION) )
                {
                    supportsLibre = JsonUtils.getBool(course, JsonKeys.ACCEPTS_FREE_CONDITION)
                            && ( (subscibedToCourse && !approvedCourse) || !subscibedToCourse);
                }
            } else return null;

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

            //TODO SUBJECT AND CATEDRA
            return new Final(id, isSubscribed, supportsLibre,
                    AppModel.getInstance().getSelecteSubject(),
                    "Wachenchauzer",
                    finalDate, sede, aula);
        }
        return null;
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
     "inscribed?": true
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
