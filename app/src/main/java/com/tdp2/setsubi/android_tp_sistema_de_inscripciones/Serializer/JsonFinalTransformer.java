package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import android.util.Log;

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
            boolean supportsLibre = false, approvedCourse, freeSubscription = false;
            Date finalDate;
            Sede sede;
            String catedra;
            JsonObject finalObject = object.getAsJsonObject();

            if( finalObject.has(JsonKeys.COURSE) && finalObject.get(JsonKeys.COURSE).isJsonObject())
            {
                JsonObject course = finalObject.getAsJsonObject(JsonKeys.COURSE);
                catedra = JsonTransformHelper.getCatedra(course);
                approvedCourse = JsonTransformHelper.hasApprovedCourse(course);
                if( JsonUtils.isBool(course, JsonKeys.ACCEPTS_FREE_CONDITION) )
                {
                    supportsLibre = JsonUtils.getBool(course, JsonKeys.ACCEPTS_FREE_CONDITION);
                }
            } else return null;

            if( JsonUtils.isObject(finalObject, JsonKeys.REGISTRATION) )
            {
                JsonObject registration = finalObject.getAsJsonObject(JsonKeys.REGISTRATION);
                if( JsonUtils.isInt(registration, JsonKeys.ID) )
                {
                    subscriptionId = JsonUtils.getInt(registration, JsonKeys.ID);
                }
                if( JsonUtils.isString(registration, JsonKeys.CONDITION) )
                {
                    freeSubscription = isFreeSubscription(JsonUtils.getString(registration, JsonKeys.CONDITION));
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
            Final fina = new Final(id, supportsLibre, approvedCourse,
                    AppModel.getInstance().getSelectedSubject(),
                    catedra,
                    finalDate, sede, aula);
            if( subscriptionId != -1 ) fina.setSubscription(subscriptionId, freeSubscription);
            return fina;
        }
        return null;
    }

    private boolean isFreeSubscription(String string)
    {
        return string.toLowerCase().equals("free");
    }

    private boolean getSubscribedToCourse(JsonObject course) {
        if( JsonUtils.isBool(course, JsonKeys.INSCRIBED) )
        {
            return JsonUtils.getBool(course, JsonKeys.INSCRIBED);
        }
        return false;
    }
}
