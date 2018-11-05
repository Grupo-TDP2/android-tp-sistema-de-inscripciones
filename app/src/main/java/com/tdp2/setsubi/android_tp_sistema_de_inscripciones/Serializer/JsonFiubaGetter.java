package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.JsonObject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.CoursePeriod;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Sede;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonGetter;

public class JsonFiubaGetter extends JsonGetter
{
    public JsonFiubaGetter(JsonObject object) {
        super(object);
    }

    @Override
    public JsonGetter atObject(String key)
    {
        JsonGetter newGetter = super.atObject(key);
        if( newGetter instanceof NullGetter )
        {
            return newGetter;
        }
        return new JsonFiubaGetter(newGetter.getActualObject());
    }

    public Sede getSede(String key)
    {
        String sede = asString(key);
        if( sede != null )
        {
            switch (sede.toLowerCase())
            {
                case "lh":
                    return Sede.LAS_HERAS;
            }
            return Sede.PASEO_COLON;
        }
        return null;
    }

    public CoursePeriod.Period getPeriod(String key)
    {
        String period = asString(key);
        if( period != null )
        {
            switch (period)
            {
                case "second_semester":
                    return CoursePeriod.Period.SECOND;
                case "first_semester":
                    return CoursePeriod.Period.FIRST;
                case "summer_school":
                    return CoursePeriod.Period.SUMMER;
            }
        }
        return null;
    }
}
