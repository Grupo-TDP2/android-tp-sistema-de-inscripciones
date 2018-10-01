package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.EnrolmentResponse;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EnrolmentJsonTest
{
    String subjectJson = "{\"id\":7," +
            "\"type\":\"normal\"," +
            "\"created_at\":\"2018-10-01T00:10:00.651Z\"," +
            "\"student\":{\"id\":1," +
                "\"first_name\":\"Leandro\"," +
                "\"last_name\":\"Masello\"," +
                "\"personal_document_number\":\"35000000\"," +
                "\"school_document_number\":\"93106\"," +
                "\"birthdate\":\"1991-08-06\"," +
                "\"phone_number\":\"12345678\"," +
                "\"address\":\"Abbey Road 123\"," +
                "\"email\":\"leandro.masello@example.com\"," +
                "\"created_at\":\"2018-09-29T21:47:55.747Z\"," +
                "\"updated_at\":\"2018-09-29T21:47:55.747Z\"}," +
            "\"course\":" +
                "{\"id\":11," +
                "\"name\":\"005\"," +
                "\"vacancies\":1}}";

    @Test
    public void EnrolDeserialize()
    {
        EnrolmentResponse enrolmentResponse = new JsonEnrolmentTransformer().transform(new Gson().fromJson(subjectJson, JsonElement.class ));
        assertNotNull(enrolmentResponse);
        assertEquals(11, enrolmentResponse.getCourseId());
        assertEquals(EnrolmentResponse.EnrolmentType.REGULAR, enrolmentResponse.getEnrolmentType());
    }
}
