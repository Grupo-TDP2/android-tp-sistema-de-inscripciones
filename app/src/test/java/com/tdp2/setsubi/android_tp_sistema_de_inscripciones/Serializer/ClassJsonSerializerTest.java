package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;

import org.junit.Test;

import static org.junit.Assert.*;

public class ClassJsonSerializerTest
{
    @Test
    public void deserializeCorrectSubject()
    {
        String subjectJson = "{\"id\":1,\"name\":\"Análisis matemático II A\",\"code\":\"03\",\"credits\":6,\"department\":{\"id\":2,\"name\":\"Departamento de Matemática\",\"code\":\"61\"}}";
        Subject subject = new JsonSubjectTransformer().transform(new Gson().fromJson(subjectJson, JsonElement.class));
        assertNotNull(subject);
        assertEquals(1,subject.getId());
        assertEquals("Análisis matemático II A", subject.getName());
        assertEquals(3, subject.getCode());
        assertEquals(6, subject.getCredits());
        assertEquals(61, subject.getDepartmentCode());
    }
}
