package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Course;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.CourseTime;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.CursoTimeBand;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Sede;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.DayOfWeek;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils;

import java.util.Arrays;
import java.util.List;

import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.BUILDING;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.CLASSROOM;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.ID;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.INSCRIBED;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.LAST_NAME;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.LESSON_SCHEDULES;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.NAME;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.TEACHER;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.TEACHERS;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.VACANCIES;

import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils.getInt;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils.getString;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils.isInt;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils.isString;

public class JsonCourseTransformer extends JsonTransformer<Course>
{
    private JsonArrayTransformer<CursoTimeBand> timesSerializer = new JsonArrayTransformer<>(new JsonCourseTimeBandTransformer());

    @Override
    public Course transform(JsonElement object)
    {
        if( object.isJsonObject() )
        {
            JsonObject jsonObject = object.getAsJsonObject();
            int id, vacancies;
            Sede sede;
            String name;
            List<CursoTimeBand> times;
            if( isInt(jsonObject, ID) )
            {
                id = getInt(jsonObject, ID);
            } else return null;

            if( isString(jsonObject, NAME) )
            {
                String teacherName = null;
                if( jsonObject.has(TEACHERS) && jsonObject.get(TEACHERS).isJsonArray() )
                {
                    JsonArray array = jsonObject.get(TEACHERS).getAsJsonArray();

                    if( array.size() != 0 )
                    {
                        teacherName = getTeacherName(array.get(0));
                    }
                }
                if( teacherName != null )
                {
                    name = teacherName + " - " + getString(jsonObject, NAME);
                } else {
                    name = getString(jsonObject, NAME);
                }
            } else return null;

            if( isInt(jsonObject, VACANCIES) )
            {
                vacancies = Integer.parseInt(getString(jsonObject, VACANCIES));
            } else return null;

            if( jsonObject.has(LESSON_SCHEDULES) )
            {
                times = timesSerializer.transform(jsonObject.get(LESSON_SCHEDULES));
                sede = getSede(jsonObject.get(LESSON_SCHEDULES));
            } else return null;

            boolean isSubscribed = false, enabledToEnroll = false;
            if( jsonObject.has(INSCRIBED) && jsonObject.get(INSCRIBED).isJsonPrimitive() )
            {
                JsonPrimitive primitive = jsonObject.getAsJsonPrimitive(INSCRIBED);
                if( primitive.isBoolean() )
                {
                    isSubscribed = primitive.getAsBoolean();
                }
            }

            if( JsonUtils.isBool(jsonObject, JsonKeys.ENABLED_TO_ENROL) )
            {
                enabledToEnroll = JsonUtils.getBool(jsonObject, JsonKeys.ENABLED_TO_ENROL);
            }
            return new Course(id, name, sede, times, vacancies, isSubscribed, enabledToEnroll);
        }
        return null;
    }

    private String getTeacherName(JsonElement jsonElement) {
        if(jsonElement.isJsonObject())
        {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if( jsonObject.has(TEACHER) && jsonObject.get(TEACHER).isJsonObject() )
            {
                JsonObject teacher = jsonObject.getAsJsonObject(TEACHER);
                if( isString(teacher, LAST_NAME) )
                {
                    return getString(teacher, LAST_NAME);
                }
            }
        }
        return null;
    }

    private Sede getSede(JsonElement jsonElement)
    {
        if( jsonElement.isJsonArray() )
        {
            JsonArray array = jsonElement.getAsJsonArray();
            if(array.size() != 0 )
            {
                JsonElement first = array.get(0);
                if( first.isJsonObject() )
                {
                    JsonObject lesson = first.getAsJsonObject();
                    if( lesson.has(JsonKeys.CLASSROOM) && lesson.get(JsonKeys.CLASSROOM).isJsonObject() )
                    {
                        JsonObject classroom = lesson.getAsJsonObject(CLASSROOM);
                        if( classroom.has(JsonKeys.BUILDING) && classroom.get(BUILDING).isJsonObject() )
                        {
                            JsonObject building = classroom.getAsJsonObject(BUILDING);
                            if( building.has(NAME) && isString(building, NAME) )
                            return parseSede(getString(building, NAME));
                        }
                    }
                }
            }
        }
        return Sede.PASEO_COLON;
    }

    private Sede parseSede(String string)
    {
        switch (string.toLowerCase())
        {
            case "LH":
                return Sede.LAS_HERAS;
        }
        return Sede.PASEO_COLON;
    }
}
