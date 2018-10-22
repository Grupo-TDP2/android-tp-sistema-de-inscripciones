package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Sede;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.BUILDING;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.CLASSROOM;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.LAST_NAME;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.NAME;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.TEACHER;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.TEACHERS;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.TEACHING_POSITION;
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

    public static String getCatedra(JsonObject course)
    {
        if( isString(course, NAME) )
        {
            String teacherName = null;
            if( course.has(TEACHERS) && course.get(TEACHERS).isJsonArray() )
            {
                JsonArray array = course.get(TEACHERS).getAsJsonArray();

                if( array.size() != 0 )
                {
                    for( int i = 0; i < array.size(); i++ )
                    {
                        JsonElement element = array.get(i);
                        if( element.isJsonObject() )
                        {
                            JsonObject teacher = element.getAsJsonObject();
                            if( isString(teacher, TEACHING_POSITION) &&
                                    getString(teacher, TEACHING_POSITION).equals("course_chief")
                                    || getString(teacher, TEACHING_POSITION).equals("practice_chief") )
                            {
                                teacherName = getTeacherName(teacher);
                            }
                        }
                    }
                }
            }
            if( teacherName != null )
            {
                return teacherName + " - " + getString(course, NAME);
            } else {
               return getString(course, NAME);
            }
        } else return null;
    }

    private static String getTeacherName(JsonObject jsonObject)
    {
        if( jsonObject.has(TEACHER) && jsonObject.get(TEACHER).isJsonObject() )
        {
            JsonObject teacher = jsonObject.getAsJsonObject(TEACHER);
            if( isString(teacher, LAST_NAME) )
            {
                return getString(teacher, LAST_NAME);
            }
        }
        return null;
    }

    public static boolean hasApprovedCourse(JsonObject jsonObject)
    {
        if( JsonUtils.isObject(jsonObject, JsonKeys.ENROLMENT))
        {
            JsonObject enrolmnet = jsonObject.getAsJsonObject(JsonKeys.ENROLMENT);
            if( JsonUtils.isString(enrolmnet, JsonKeys.STATUS) )
            {
                return isApproved(JsonUtils.getString(enrolmnet, JsonKeys.STATUS));
            }
        }
        return false;
    }

    private static boolean isApproved(String string) {
        return string.toLowerCase().equals("approved");
    }
}
