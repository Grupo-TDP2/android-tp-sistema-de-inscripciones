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

import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.CODE;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.CREDITS;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.ID;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.NAME;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils.getInt;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils.getString;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils.isInt;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils.isString;

public class JsonMyFinalTransformer extends JsonTransformer<Final>
{
    /*"enrolment": {
                    "id": 34,
                    "type": "normal",
                    "student_id": 16,
                    "course_id": 39,
                    "created_at": "2018-10-15T22:11:42.210Z",
                    "updated_at": "2018-10-20T03:45:48.003Z",
                    "status": "approved",
                    "final_qualification": null,
                    "partial_qualification": 8
                },
                "subject": {
                    "id": 31,
                    "name": "Taller de desarrollo de proyectos informáticos II",
                    "code": "48",
                    "credits": 6
                }
     */
    @Override
    public Final transform(JsonElement object)
    {
        if( object.isJsonObject() )
        {
            int id, aula;
            boolean supportsLibre = false;
            String catedra = null;
            Subject subject = null;
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
            if( JsonUtils.isInt(finalObject, JsonKeys.ID) )
            {
                id = JsonUtils.getInt(finalObject, JsonKeys.ID);
            } else return null;
            if( finalObject.has(JsonKeys.EXAM) && finalObject.get(JsonKeys.EXAM).isJsonObject() )
            {
                JsonObject exam = finalObject.getAsJsonObject(JsonKeys.EXAM);
                aula = JsonTransformHelper.getAula(exam);
                sede = JsonTransformHelper.getSede(exam);
                if( sede == null || aula == -1 ) return null;
                /*if( JsonUtils.isInt(exam, JsonKeys.ID) )
                {
                    id = JsonUtils.getInt(exam, JsonKeys.ID);
                } else return null;*/
                if( JsonUtils.isString(exam, JsonKeys.DATE_TIME) )
                {
                    try {
                        finalDate = JsonTransformHelper.getDate(JsonUtils.getString(exam, JsonKeys.DATE_TIME));
                    } catch (ParseException e) {
                        return null;
                    }
                }
                if( exam.has(JsonKeys.COURSE) && exam.get(JsonKeys.COURSE).isJsonObject() )
                {
                    JsonObject course = exam.getAsJsonObject(JsonKeys.COURSE);
                    if( JsonUtils.isString(course, JsonKeys.NAME))
                    {
                        catedra = JsonUtils.getString(course, JsonKeys.NAME);
                    }

                    if(JsonUtils.isObject(course, JsonKeys.SUBJECT))
                    {
                        JsonObject jsonSubject = course.getAsJsonObject(JsonKeys.SUBJECT);
                        subject = getSubject(jsonSubject);
                    }
                }
                if( finalDate == null || catedra == null || subject == null  ) return null;
            } else return null;

            return new Final(id, true, supportsLibre,
                    subject,
                    catedra,
                    finalDate, sede, aula);
        }
        return null;
    }

    private Subject getSubject(JsonObject jsonObject)
    {
        //TODO DEPARTMENT
        int id, code, credits;
        Department department;
        String name;
        if( isInt(jsonObject, ID) ){
            id = getInt(jsonObject, ID);
        } else return null;

        if( isInt(jsonObject, CREDITS) ){
            credits = getInt(jsonObject, CREDITS);
        } else return null;

        if( isString(jsonObject, CODE) )
        {
            code = Integer.parseInt(getString(jsonObject,CODE));
        } else return null;

        if( isString(jsonObject, NAME) )
        {
            name = getString(jsonObject, NAME);
        } else return null;
        return new Subject(id, code, name, credits, new Department(5,"", 75));
    }


}
