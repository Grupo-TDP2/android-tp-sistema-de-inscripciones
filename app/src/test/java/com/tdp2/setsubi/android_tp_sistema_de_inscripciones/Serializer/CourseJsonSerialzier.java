package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Course;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.CourseTime;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.CursoTimeBand;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.DayOfWeek;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CourseJsonSerialzier {
    @Test
    public void deserializeCorrectSubject()
    {
        String subjectJson =
                "{\"id\":9,\"name\":\"003\",\"vacancies\":8,\"subject\":{\"id\":7,\"name\":\"Taller de desarrollo de proyectos informáticos II\",\"code\":\"48\",\"credits\":6},\"lesson_schedules\":[{\"id\":1,\"type\":\"theory\",\"day\":\"Monday\",\"hour_start\":\"2000-01-01T17:00:00.000Z\",\"hour_end\":\"2000-01-01T19:00:00.000Z\",\"classroom\":{\"id\":5,\"floor\":\"3\",\"number\":\"22\",\"building\":{\"id\":3,\"name\":\"PC\",\"address\":\"Av. Paseo Colón 850\",\"postal_code\":\"1063\",\"city\":\"CABA\"}}},{\"id\":2,\"type\":\"practice\",\"day\":\"Monday\",\"hour_start\":\"2000-01-01T19:00:00.000Z\",\"hour_end\":\"2000-01-01T23:00:00.000Z\",\"classroom\":{\"id\":6,\"floor\":\"3\",\"number\":\"03\",\"building\":{\"id\":3,\"name\":\"PC\",\"address\":\"Av. Paseo Colón 850\",\"postal_code\":\"1063\",\"city\":\"CABA\"}}}]}";
        Course subject = new JsonCourseTransformer().transform(new Gson().fromJson(subjectJson, JsonElement.class));
        assertNotNull(subject);
        assertEquals(9, subject.getId());
        assertEquals("003", subject.getCatedra());
        assertEquals(8, subject.getCupos());
        assertEquals(2,subject.getCursoTimeBands().size());
        equalTimBand(new CursoTimeBand(DayOfWeek.MONDAY, 322,
                new CourseTime(17,00),
                new CourseTime(19,00),
                CursoTimeBand.CursoTimeType.TEORICO, false), subject.getCursoTimeBands().get(0));
        equalTimBand(new CursoTimeBand(DayOfWeek.MONDAY, 303,
                new CourseTime(19,00),
                new CourseTime(23,00),
                CursoTimeBand.CursoTimeType.PRACTIO, false), subject.getCursoTimeBands().get(1));
    }

    private void equalTimBand(CursoTimeBand cursoTimeBand, CursoTimeBand other) {
        assertEquals(cursoTimeBand.getAula(), other.getAula());
        assertTime(cursoTimeBand.getStarTime(), other.getStarTime());
        assertTime(cursoTimeBand.getEndTime(), other.getEndTime());
        assertEquals(cursoTimeBand.getDayOfWeek(), other.getDayOfWeek());
        assertEquals(cursoTimeBand.getType(), other.getType());
    }

    private void assertTime(CourseTime time, CourseTime other)
    {
        assertEquals(time.getHours(), other.getHours());
        assertEquals(time.getMinutes(), other.getMinutes());
    }
}
