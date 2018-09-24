package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Course;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CourseJsonSerialzier {
    @Test
    public void deserializeCorrectSubject()
    {
        String subjectJson = "{\"id\":1,\"name\":\"001\",\"vacancies\":2,\"subject\":{\"id\":1,\"name\":\"Análisis matemático II A\",\"code\":\"03\",\"credits\":6},\"school_term\":{\"id\":1,\"year\":2018,\"term\":\"second_semester\",\"date_start\":\"2018-08-18\",\"date_end\":\"2018-12-01\"},\"teachers\":[],\"students\":[]}";
        Course subject = new JsonCourseTransformer().transform(new Gson().fromJson(subjectJson, JsonElement.class));
        assertNotNull(subject);
        assertEquals(1, subject.getId());
        assertEquals("001", subject.getCatedra());
        assertEquals(2, subject.getCupos());
    }
}
