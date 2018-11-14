package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.CoursePeriod;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.MyCourse;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MyCourseJsontest
{
    public String jsonMyCourse =
            "{"+
            "        \"id\": 100,"+
            "        \"type\": \"normal\","+
            "        \"partial_qualification\": null,"+
            "        \"status\": \"not_evaluated\","+
            "        \"final_qualification\": null,"+
            "        \"created_at\": \"2018-11-04T16:57:07.926Z\","+
            "        \"course\": {"+
            "            \"id\": 149,"+
            "            \"name\": \"002\","+
            "            \"vacancies\": 2,"+
            "            \"accept_free_condition_exam\": false,"+
            "            \"inscribed?\": true,"+
            "            \"able_to_enrol?\": false,"+
            "            \"enrolment\": {"+
            "                \"id\": 100,"+
            "                \"type\": \"normal\","+
            "                \"student_id\": 70,"+
            "                \"course_id\": 149,"+
            "                \"created_at\": \"2018-11-04T16:57:07.926Z\","+
            "                \"updated_at\": \"2018-11-04T16:57:07.926Z\","+
            "                \"status\": \"not_evaluated\","+
            "                \"final_qualification\": null,"+
            "                \"partial_qualification\": null"+
            "            },"+
            "            \"subject\": {"+
            "                \"id\": 91,"+
            "                \"name\": \"Física II A\","+
            "                \"code\": \"03\","+
            "                \"credits\": 6"+
            "            },"+
            "            \"school_term\": {"+
            "                \"id\": 72,"+
            "                \"year\": 2018,"+
            "                \"term\": \"second_semester\","+
            "                \"date_start\": \"2018-08-13\","+
            "                \"date_end\": \"2018-12-03\""+
            "            }"+
            "        }"+
            "    }";

    @Test
    public void correctSerialization()
    {
        MyCourse myCourse = new MyCourseTransformer().transform(new Gson().fromJson(jsonMyCourse, JsonElement.class));
        assertNotNull(myCourse);
        assertEquals(149, myCourse.getId());
        assertEquals(CoursePeriod.Period.SECOND, myCourse.getPeriod());
        assertEquals(2018, myCourse.getYear());
    }
}
