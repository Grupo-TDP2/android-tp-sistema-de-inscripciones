package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.JsonElement;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.ApprovedSubject;

public class JsonApprovedSubject extends JsonTransformer<ApprovedSubject> {

    /*"{
              \"subject\": {"+
    "            \"id\": 90,"+
    "            \"name\": \"Análisis matemático II A\","+
    "            \"code\": \"03\","+
    "            \"credits\": 6,"+
    "            \"department_id\": 91,"+
    "            \"created_at\": \"2018-11-04T16:57:05.689Z\","+
    "            \"updated_at\": \"2018-11-04T16:57:05.689Z\""+
    "        },"+
    "        \"department\": {"+
    "            \"id\": 91,"+
    "            \"name\": \"Departamento de Matemática\","+
    "            \"code\": \"61\","+
    "            \"created_at\": \"2018-11-04T16:57:05.608Z\","+
    "            \"updated_at\": \"2018-11-04T16:57:05.608Z\""+
    "        },"+
    "        \"enrolment\": {"+
    "            \"id\": 101,"+
    "            \"type\": \"normal\","+
    "            \"student_id\": 70,"+
    "            \"course_id\": 153,"+
    "            \"created_at\": \"2018-11-04T16:57:07.935Z\","+
    "            \"updated_at\": \"2018-11-04T16:57:07.935Z\","+
    "            \"status\": \"not_evaluated\","+
    "            \"final_qualification\": 6,"+
    "            \"partial_qualification\": null"+
    "        },"+
    "        \"school_term\": {"+
    "            \"id\": 73,"+
    "            \"term\": \"first_semester\","+
    "            \"year\": 2018,"+
    "            \"date_start\": \"2018-03-12\","+
    "            \"date_end\": \"2018-07-02\","+
    "            \"created_at\": \"2018-11-04T16:57:05.810Z\","+
    "            \"updated_at\": \"2018-11-04T16:57:05.810Z\""+
    "        }" + 
    "}"*/
    @Override
    public ApprovedSubject transform(JsonElement object)
    {
        if( object.isJsonObject() )
        {
            JsonGetter getter = new JsonGetter();
        }
        return null;
    }
}
