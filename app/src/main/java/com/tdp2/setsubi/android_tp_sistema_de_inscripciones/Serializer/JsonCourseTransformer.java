package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Course;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.CourseTime;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.CursoTimeBand;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Sede;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.DayOfWeek;

import java.util.Arrays;
import java.util.List;

import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.ID;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.NAME;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.VACANCIES;

import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils.getInt;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils.getString;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils.isInt;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils.isString;

public class JsonCourseTransformer extends JsonTransformer<Course>
{
    private List<CursoTimeBand> times = Arrays.asList(new CursoTimeBand(DayOfWeek.MONDAY, 203,
                    new CourseTime(17,0),
                    new CourseTime(20,0),
                    CursoTimeBand.CursoTimeType.TEORICO, false),
            new CursoTimeBand(DayOfWeek.TUESDAY, 221,
                    new CourseTime(17,0),
                    new CourseTime(20,0),
                    CursoTimeBand.CursoTimeType.PRACTIO, true
            ));
    private Sede sede = Sede.PASEO_COLON;

    @Override
    public Course transform(JsonElement object)
    {
        if( object.isJsonObject() )
        {
            JsonObject jsonObject = object.getAsJsonObject();
            int id, vacancies;
            String name;
            if( isInt(jsonObject, ID) )
            {
                id = getInt(jsonObject, ID);
            } else return null;

            if( isString(jsonObject, NAME) )
            {
                name = getString(jsonObject, NAME);
            } else return null;

            if( isInt(jsonObject, VACANCIES) )
            {
                vacancies = Integer.parseInt(getString(jsonObject, VACANCIES));
            } else return null;
            return new Course(id, name, sede, times, vacancies);
        }
        return null;
    }
}
