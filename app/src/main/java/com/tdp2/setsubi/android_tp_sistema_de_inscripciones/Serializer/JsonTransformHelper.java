package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.JsonObject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Sede;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.BUILDING;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.CLASSROOM;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.NAME;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils.getString;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils.isString;

public class JsonTransformHelper
{
    public static Sede getSede(JsonObject objectWithClassroom)
    {
        if( objectWithClassroom.has(JsonKeys.CLASSROOM) && objectWithClassroom.get(JsonKeys.CLASSROOM).isJsonObject() )
        {
            JsonObject classroom = objectWithClassroom.getAsJsonObject(CLASSROOM);
            if( classroom.has(JsonKeys.BUILDING) && classroom.get(BUILDING).isJsonObject() )
            {
                JsonObject building = classroom.getAsJsonObject(BUILDING);
                if( building.has(NAME) && isString(building, NAME) )
                    return parseSede(getString(building, NAME));
            }
        }
        return null;
    }

    private static Sede parseSede(String string)
    {
        switch (string.toLowerCase())
        {
            case "LH":
                return Sede.LAS_HERAS;
        }
        return Sede.PASEO_COLON;
    }

    public static int getAula(JsonObject objectWithClassroom)
    {
        int aula = -1;
        if( objectWithClassroom.has(JsonKeys.CLASSROOM) && objectWithClassroom.get(JsonKeys.CLASSROOM).isJsonObject() )
        {
            JsonObject classroom = objectWithClassroom.get(JsonKeys.CLASSROOM).getAsJsonObject();
            if( JsonUtils.isString(classroom, JsonKeys.FLOOR) && JsonUtils.isString(classroom, JsonKeys.NUMBER) )
            {
                aula = Integer.parseInt(JsonUtils.getString(classroom, JsonKeys.FLOOR))*100
                        + Integer.parseInt(JsonUtils.getString(classroom,JsonKeys.NUMBER));
            }
        }
        return aula;
    }

    public static Date getDate(String jsonDate) throws ParseException
    {
        ///2018-12-12T17:00:00.000Z
        String toParse = jsonDate.replace('T',' ').replace("Z","");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
        return formatter.parse(toParse);
    }
}
