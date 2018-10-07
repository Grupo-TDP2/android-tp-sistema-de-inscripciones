package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.CourseTime;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.CursoTimeBand;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.DayOfWeek;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils;

public class JsonCourseTimeBandTransformer extends JsonTransformer<CursoTimeBand>
{
    @Override
    public CursoTimeBand transform(JsonElement object)
    {
        if( object.isJsonObject() )
        {
            DayOfWeek dayOfWeek = null;
            int aula;
            CourseTime start, end;
            CursoTimeBand.CursoTimeType type = null;
            JsonObject jsonObject = object.getAsJsonObject();
            if(JsonUtils.isString(jsonObject, JsonKeys.DAY))
            {
                dayOfWeek = transform(JsonUtils.getString(jsonObject, JsonKeys.DAY));
            }

            if( JsonUtils.isString(jsonObject, JsonKeys.TYPE) )
            {
                type = transformType(JsonUtils.getString(jsonObject, JsonKeys.TYPE));
            }

            start = getTime(jsonObject, JsonKeys.HOUR_START);
            end = getTime(jsonObject, JsonKeys.HOUR_END);
            if( jsonObject.has(JsonKeys.CLASSROOM) && jsonObject.get(JsonKeys.CLASSROOM).isJsonObject() )
            {
                JsonObject classroom = jsonObject.get(JsonKeys.CLASSROOM).getAsJsonObject();
                if( JsonUtils.isString(classroom, JsonKeys.FLOOR) && JsonUtils.isString(classroom, JsonKeys.NUMBER) )
                {
                    aula = Integer.parseInt(JsonUtils.getString(classroom, JsonKeys.FLOOR))*100
                            + Integer.parseInt(JsonUtils.getString(classroom,JsonKeys.NUMBER));
                } else
                {
                    aula = -1;
                }
            } else {
                aula = -1;
            }

            if( dayOfWeek != null && start != null && end != null && type != null )
            {
                return new CursoTimeBand(dayOfWeek, aula, start,end, type, false);
            }
        }
        return null;
    }

    private CourseTime getTime(JsonObject jsonObject, String hourStart) {
        if( JsonUtils.isString(jsonObject, hourStart) )
        {
            String value = JsonUtils.getString(jsonObject, hourStart);
            return parseTime(value);
        }
        return null;
    }

    /**
     * Parses 19 and 00 from '2000-01-01T19:00:12.200Z'
     * @param value String in format '2000-01-01T19:00:12.200Z'
     * @return parsed course time if the string had a valid value, null otherwise
     */
    private CourseTime parseTime(String value)
    {
        String firstSplit = "T";
        String[] dateSplit = value.split(firstSplit);
        if( dateSplit.length != 2 ) return null;
        String timeSplit = ":";
        String[] splitted = dateSplit[1].split(timeSplit);
        if( splitted.length == 3 )
        {
            String part1 = splitted[0];
            String part2 = splitted[1];
            try{
                return new CourseTime(Integer.parseInt(part1), Integer.parseInt(part2));
            } catch (NumberFormatException ignored){}
        }
        return null;
    }

    private  CursoTimeBand.CursoTimeType transformType(String value)
    {
        switch (value)
        {
            case "theory": case "teoría":
                return CursoTimeBand.CursoTimeType.TEORICO;
            case "practice": case "práctica":
                return CursoTimeBand.CursoTimeType.PRACTIO;
        }
        return null;
    }

    private DayOfWeek transform(String value)
    {
        switch (value)
        {
            case "Monday": case "Lunes":
                return DayOfWeek.MONDAY;
            case "Tuesday": case "Martes":
                return DayOfWeek.TUESDAY;
            case "Wednesday": case "Miércoles":
                return DayOfWeek.WEDNESDAY;
            case "Thursday": case "Jueves":
                return DayOfWeek.THURSDAY;
            case "Friday": case "Viernes":
                return DayOfWeek.FRIDAY;
            case "Saturday": case "Sábado":
                return DayOfWeek.SATURDAY;
        }
        return null;
    }
}
