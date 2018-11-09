package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.ApprovedSubject;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ApprovedSubjectJson
{
    String JSON =
            "{" +
            "          \"subject\": {"+
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
            "}";

    @Test
    public void correctTransform()
    {
        ApprovedSubject subject = new JsonApprovedSubject().transform(new Gson().fromJson(JSON, JsonElement.class));
        assertNotNull(subject);
        assertEquals(6, subject.getCalification());
        assertEquals("04/11/2018",  new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(subject.getApprovedDate()));
        assertEquals("Análisis matemático II A", subject.getSubject().getName());
        assertEquals(3, subject.getSubject().getCode());
        assertEquals(6, subject.getSubject().getCredits());
        assertEquals(61, subject.getSubject().getDepartmentCode());
        assertEquals("Departamento de Matemática", subject.getSubject().getDepartment().getName());
    }
}
