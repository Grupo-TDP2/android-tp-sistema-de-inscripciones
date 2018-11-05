package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.JsonElement;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.ApprovedSubject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Department;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.AnyNullCheck;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonGetter;

import java.text.ParseException;
import java.util.Date;

public class JsonApprovedSubject extends JsonTransformer<ApprovedSubject>
{
    @Override
    public ApprovedSubject transform(JsonElement object)
    {
        if( object.isJsonObject() )
        {
            JsonGetter getter = new JsonGetter(object.getAsJsonObject());
            JsonGetter subjectJson = getter.atObject(JsonKeys.SUBJECT);
            JsonGetter departmentJson = getter.atObject(JsonKeys.DEPARTMENT);
            JsonGetter enrolmentJson = getter.atObject(JsonKeys.ENROLMENT);

            Subject subject = getSubject(subjectJson,departmentJson);
            Integer calification = enrolmentJson.asInt(JsonKeys.FINAL_QUALIFICATION);
            String dateString = enrolmentJson.asString(JsonKeys.UPDATED_AT);
            if(AnyNullCheck.any(subject, calification, dateString) ) return null;

            Date date;
            try {
                date = JsonTransformHelper.getDate(dateString);
            } catch (ParseException e) {
                return null;
            }

            return new ApprovedSubject(subject,date,calification);
        }
        return null;
    }

    private Subject getSubject(JsonGetter subjectJson, JsonGetter departmentJson) {

        Department department = getDepartment(departmentJson);
        if( department == null ) return null;

        Integer id = subjectJson.asInt(JsonKeys.ID),
                code,
                credits = subjectJson.asInt(JsonKeys.CREDITS);
        String name = subjectJson.asString(JsonKeys.NAME);
        String codeString = subjectJson.asString(JsonKeys.CODE);
        if( codeString == null ) return null;
        code = Integer.parseInt(codeString);

        if( AnyNullCheck.any(id, code, credits, name, codeString) ) return null;

        return new Subject(id, code, name, credits, department);
    }

    private Department getDepartment(JsonGetter departmentJson) {
        Integer id = departmentJson.asInt(JsonKeys.ID), code;
        String name = departmentJson.asString(JsonKeys.NAME);
        String codeString = departmentJson.asString(JsonKeys.CODE);
        if( codeString == null ) return null;
        code = Integer.parseInt(codeString);

        if( AnyNullCheck.any(id, code, name) ) return null;
        return new Department(id, name, code);
    }
}
