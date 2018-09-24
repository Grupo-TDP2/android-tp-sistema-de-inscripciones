package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Department;

import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonKeys.*;
import static com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonUtils.*;

public class JsonDepartmentTransformer extends JsonTransformer<Department> {

    @Override
    public Department transform(JsonElement object)
    {
        if( object.isJsonObject() )
        {
            JsonObject jsonObject = object.getAsJsonObject();
            int id, code;
            String name;
            if( isInt(jsonObject, ID) )
            {
                id = getInt(jsonObject, ID);
            } else return null;

            if( isString(jsonObject, NAME) )
            {
                name = getString(jsonObject, NAME);
            } else return null;

            if( isString(jsonObject, CODE) )
            {
                code = Integer.parseInt(getString(jsonObject, CODE));
            } else return null;
            return new Department(id, name, code);
        }
        return null;
    }
}
