package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Department;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DepartmentJsonSerialzierTest
{
    @Test
    public void deserializeCorrectSubject()
    {
        String subjectJson = "{\"id\":2,\"name\":\"Departamento de Matemática\",\"code\":\"61\"}";
        Department subject = new JsonDepartmentTransformer().transform(new Gson().fromJson(subjectJson, JsonElement.class));
        assertNotNull(subject);
        assertEquals(2, subject.getId());
        assertEquals("Departamento de Matemática", subject.getName());
        assertEquals(61, subject.getCode());
    }
}
