package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Department;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Final;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Sede;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FinalJsonTest
{
    private static final String EXAM =
        "{" +
            "\"id\":3," +
            "\"exam_type\":\"final\"," +
            "\"date_time\":\"2018-12-12T17:00:00.000Z\"," +
            "\"final_exam_week\":" +
                "{\"id\":13," +
                "\"date_start_week\":\"2018-12-10\"," +
                "\"year\":\"2018\"" +
                "}," +
            "\"course\":" +
                "{\"id\":39," +
                "\"name\":\"003\"," +
                "\"vacancies\":0," +
                "\"accept_free_condition_exam\":false," +
                "\"inscribed?\":true}," +
            "\"classroom\":" +
                "{\"id\":29," +
                "\"floor\":\"3\"," +
                "\"number\":\"22\"," +
                "\"building\":" +
                    "{\"id\":15," +
                    "\"name\":\"PC\"," +
                    "\"address\":\"Av. Paseo Colón 850\"," +
                    "\"postal_code\":\"1063\"," +
                    "\"city\":\"CABA\"}" +
                "}" +
        "}";

    @BeforeClass
    public static void initSubject()
    {
        AppModel.getInstance().setSelecteClass(new Subject(1,15,"Tets",20,
                new Department(1,"DTest", 45)));
    }

    @Test
    public void correctDeserialization()
    {
        Final finals = new JsonFinalTransformer().transform(new Gson().fromJson(EXAM, JsonElement.class));
        assertNotNull(finals);
        assertEquals(3, finals.getId());
        assertEquals(322, finals.getAula());
        assertEquals(Sede.PASEO_COLON, finals.getSede());
        assertEquals("17:00", finals.getTime());
        assertEquals("12/12/2018", finals.getDate());
    }
}
