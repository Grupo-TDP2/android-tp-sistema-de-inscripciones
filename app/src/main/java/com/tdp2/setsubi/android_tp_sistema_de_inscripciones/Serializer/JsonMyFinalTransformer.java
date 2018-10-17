package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Department;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Final;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Sede;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils;

import java.text.ParseException;
import java.util.Date;

public class JsonMyFinalTransformer extends JsonTransformer<Final>
{
    @Override
    public Final transform(JsonElement object)
    {
        if( object.isJsonObject() )
        {
            int id, aula;
            boolean supportsLibre = false;
            Date finalDate = null;
            Sede sede;
            JsonObject finalObject = object.getAsJsonObject();
            if(JsonUtils.isString(finalObject, JsonKeys.CONDITION))
            {
                switch (JsonUtils.getString(finalObject, JsonKeys.CONDITION))
                {
                    case "libre":
                        supportsLibre = true;
                }
            } else return null;

            if( finalObject.has(JsonKeys.EXAM) && finalObject.get(JsonKeys.EXAM).isJsonObject() )
            {
                JsonObject exam = finalObject.getAsJsonObject(JsonKeys.EXAM);
                aula = JsonTransformHelper.getAula(exam);
                sede = JsonTransformHelper.getSede(exam);
                if( sede == null || aula == -1 ) return null;
                if( JsonUtils.isInt(exam, JsonKeys.ID) )
                {
                    id = JsonUtils.getInt(exam, JsonKeys.ID);
                } else return null;
                if( JsonUtils.isString(exam, JsonKeys.DATE_TIME) )
                {
                    try {
                        finalDate = JsonTransformHelper.getDate(JsonUtils.getString(exam, JsonKeys.DATE_TIME));
                    } catch (ParseException e) {
                        return null;
                    }
                }
                if( finalDate == null ) return null;
            } else return null;
            //TODO SUBJECT AND CATEDRA
            return new Final(id, true, supportsLibre,
                    new Subject(1, 75, "Algoritmos y programacion II", 6,
                            new Department(5,"", 75)),
                    "Wachenchauzer",
                    finalDate, sede, aula);
        }
        return null;
    }


}
