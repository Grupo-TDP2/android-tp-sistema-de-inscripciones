package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Department;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.*;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils.*;

public class JsonSubjectTransformer extends JsonTransformer<Subject>
{
    private JsonDepartmentTransformer departmentTransformer = new JsonDepartmentTransformer();

    @Override
    public Subject transform(JsonElement object)
    {
        int id, code, credits;
        Department department;
        String name;
        if( object.isJsonObject() )
        {
            JsonObject jsonObject = object.getAsJsonObject();
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

            if( jsonObject.has(DEPARTMENT) )
            {
                department = departmentTransformer.transform(jsonObject.get(DEPARTMENT));
                if( department != null )
                {
                    Subject subject = new Subject(id, code, name, credits, department);
                    subject.setCourses(readCourses(jsonObject));
                    return subject;
                }
            }
        }
        return null;
    }

    private List<Integer> readCourses(JsonObject object)
    {
        List<Integer> courses = new ArrayList<>();
        if(object.has(JsonKeys.COURSES) && object.get(JsonKeys.COURSES).isJsonArray() )
        {
            JsonArray array = object.getAsJsonArray(JsonKeys.COURSES);
            for( int i = 0; i < array.size(); i++ )
            {
                JsonElement element = array.get(i);
                if( element.isJsonObject() && JsonUtils.isInt(element.getAsJsonObject(), JsonKeys.ID))
                {
                    courses.add(JsonUtils.getInt(element.getAsJsonObject(), JsonKeys.ID));
                }
            }
        }
        return courses;
    }
}
