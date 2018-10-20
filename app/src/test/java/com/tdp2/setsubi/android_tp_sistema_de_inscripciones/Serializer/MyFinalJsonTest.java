package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Final;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Sede;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MyFinalJsonTest
{
    private static final String EXAM_LIST =
            "    {" +
            "        \"id\": 1," +
            "        \"condition\": \"regular\"," +
            "        \"student\": {" +
            "            \"id\": 16," +
            "            \"first_name\": \"Leandro\"," +
            "            \"last_name\": \"Masello\"," +
            "            \"personal_document_number\": \"35000000\"," +
            "            \"school_document_number\": \"93106\"," +
            "            \"birthdate\": \"1991-08-06\"," +
            "            \"phone_number\": \"12345678\"," +
            "            \"address\": \"Abbey Road 123\"," +
            "            \"email\": \"leandro.masello@example.com\"," +
            "            \"created_at\": \"2018-10-15T22:11:41.061Z\"," +
            "            \"updated_at\": \"2018-10-15T22:11:41.061Z\"" +
            "        }," +
            "        \"exam\": {" +
            "            \"id\": 3," +
            "            \"exam_type\": \"final\"," +
            "            \"date_time\": \"2018-12-13T17:00:00.000Z\"," +
            "            \"classroom\": {" +
            "                \"id\": 29," +
            "                \"floor\": \"3\"," +
            "                \"number\": \"22\"," +
            "                \"building\": {" +
            "                    \"id\": 15," +
            "                    \"name\": \"PC\"," +
            "                    \"address\": \"Av. Paseo Colón 850\"," +
            "                    \"postal_code\": \"1063\"," +
            "                    \"city\": \"CABA\"" +
            "                }" +
            "            }" +
            "        }" +
            "    }";

    private String JsonResponse = "{\"id\":1,\"condition\":\"regular\",\"student\":{\"id\":16,\"first_name\":\"Leandro\",\"last_name\":\"Masello\",\"personal_document_number\":\"35000000\",\"school_document_number\":\"93106\",\"birthdate\":\"1991-08-06\",\"phone_number\":\"12345678\",\"address\":\"Abbey Road 123\",\"email\":\"leandro.masello@example.com\",\"created_at\":\"2018-10-15T22:11:41.061Z\",\"updated_at\":\"2018-10-15T22:11:41.061Z\"},\"exam\":{\"id\":3,\"exam_type\":\"final\",\"date_time\":\"2018-12-12T17:00:00.000Z\",\"inscribed?\":true,\"course\":{\"id\":39,\"name\":\"003\",\"vacancies\":0,\"accept_free_condition_exam\":false,\"inscribed?\":true,\"enrolment\":{\"id\":34,\"type\":\"normal\",\"student_id\":16,\"course_id\":39,\"created_at\":\"2018-10-15T22:11:42.210Z\",\"updated_at\":\"2018-10-20T03:45:48.003Z\",\"status\":\"approved\",\"final_qualification\":null,\"partial_qualification\":8},\"subject\":{\"id\":31,\"name\":\"Taller de desarrollo de proyectos informáticos II\",\"code\":\"48\",\"credits\":6}},\"classroom\":{\"id\":29,\"floor\":\"3\",\"number\":\"22\",\"building\":{\"id\":15,\"name\":\"PC\",\"address\":\"Av. Paseo Colón 850\",\"postal_code\":\"1063\",\"city\":\"CABA\"}}}}";

    @Test
    public void correctDeserialization()
    {
        Final finals = new JsonMyFinalTransformer().transform(new Gson().fromJson(EXAM_LIST, JsonElement.class));
        assertNotNull(finals);
        assertEquals(3, finals.getId());
        assertEquals(322, finals.getAula());
        assertEquals(Sede.PASEO_COLON, finals.getSede());
        assertEquals("17:00", finals.getTime());
        assertEquals("13/12/2018", finals.getDate());
    }

    @Test
    public void correctDeserialization2()
    {
        new Gson().fromJson("",JsonElement.class);
        Final finals = new JsonMyFinalTransformer().transform(new Gson().fromJson(JsonResponse, JsonElement.class));
    }
}
