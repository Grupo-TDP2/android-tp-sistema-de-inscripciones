package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Career;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Course;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.CoursePeriod;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.CursoTimeBand;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Department;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.MyCourse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Sede;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.AnyNullCheck;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.JsonGetter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyCourseTransformer extends JsonTransformer<MyCourse>
{
    private final String datePattern = "yyyy-MM-dd";
    @Override
    public MyCourse transform(JsonElement object)
    {
        if( object.isJsonObject() )
        {
            JsonFiubaGetter parentGetter = new JsonFiubaGetter(object.getAsJsonObject());
            JsonGetter courseGetter = parentGetter.atObject(JsonKeys.COURSE);
            JsonGetter enrolmentGetter = courseGetter.atObject(JsonKeys.ENROLMENT);
            JsonGetter subjectGetter = courseGetter.atObject(JsonKeys.SUBJECT);
            JsonGetter departmentGetter = subjectGetter.atObject(JsonKeys.DEPARTMENT);
            JsonGetter schoolTermGetter = courseGetter.atObject(JsonKeys.SCHOOL_TERM);

            Integer subjectId = subjectGetter.asInt(JsonKeys.ID),
                    subjectCredits = subjectGetter.asInt(JsonKeys.CREDITS),
                    subjectCode = null;
            String subjectCodeValue = subjectGetter.asString(JsonKeys.CODE);
            if( subjectCodeValue != null )
            {
                subjectCode = Integer.parseInt(subjectCodeValue);
            }
            String subjectName = subjectGetter.asString(JsonKeys.NAME);
            if( AnyNullCheck.any(subjectId, subjectCredits, subjectCode, subjectName) ) return null;

            Integer courseId = courseGetter.asInt(JsonKeys.ID), cupo = courseGetter.asInt(JsonKeys.VACANCIES);
            String catedra = JsonTransformHelper.getCatedra(courseGetter.getActualObject());
            Sede sede = Sede.PASEO_COLON;
            String status = parentGetter.asString(JsonKeys.STATUS);
            Course.Status courseStaus = getStatusFrom(status);
            List<CursoTimeBand> courseTimes = getCourses(courseGetter.getJsonArray(JsonKeys.LESSON_SCHEDULES));

            boolean enabledToEnrol = courseGetter.asBoolean(JsonKeys.ENABLED_TO_ENROL);
            if( AnyNullCheck.any(courseId, cupo, catedra, sede, enabledToEnrol, courseTimes, courseStaus) ) return null;

            Integer year = schoolTermGetter.asInt(JsonKeys.YEAR);
            CoursePeriod.Period period = ((JsonFiubaGetter)schoolTermGetter).getPeriod(JsonKeys.TERM);
            Date start = null;
            Date endDate = null;
            if( schoolTermGetter.asString(JsonKeys.DATE_START) != null
                    && schoolTermGetter.asString(JsonKeys.DATE_END) != null )
            {
                try {
                    start = JsonTransformHelper.getDate(schoolTermGetter.asString(JsonKeys.DATE_START),datePattern);
                    endDate = JsonTransformHelper.getDate(schoolTermGetter.asString(JsonKeys.DATE_END),datePattern);
                } catch (ParseException ingore) {}

            }
            if( AnyNullCheck.any(year, period, start, endDate) ) return null;

            Integer enrolmentId = enrolmentGetter.asInt(JsonKeys.ID);

            Integer departmentId = departmentGetter.asInt(JsonKeys.ID);
            String name = departmentGetter.asString(JsonKeys.NAME);
            String departmentCode = departmentGetter.asString(JsonKeys.CODE);
            if( AnyNullCheck.any(departmentCode, departmentId, name) ) return null;
            Department department = new Department(departmentId, name, Integer.parseInt(departmentCode));
            Course course = new Course(courseId,catedra,sede, courseTimes, cupo, enrolmentId, enabledToEnrol);
            course.setStatus(courseStaus);
            boolean answeredPoll = courseGetter.atObject(JsonKeys.POLL).getActualObject() != null;
            MyCourse myCourse =  new MyCourse(new Career(1,""),
                    new Subject(subjectId, subjectCode, subjectName,  subjectCredits,
                            department),
                    course,
                    new CoursePeriod(year, period, start, endDate));
            myCourse.setAlreadyAnsweredPoll(answeredPoll);
            return myCourse;
        }
        return null;
    }

    private Course.Status getStatusFrom(String status) {
        if(status == null) return null;
        switch (status.toLowerCase())
        {
            case "approved":
                return Course.Status.APPROVED;
            case "disapproved":
                return Course.Status.DISAPPROVED;
            case "not_evaluated":
                return Course.Status.NOT_EVALUATED;
        }
        return null;
    }

    private List<CursoTimeBand> getCourses(JsonArray jsonArray)
    {
        List<CursoTimeBand> times = new ArrayList<>();
        JsonCourseTimeBandTransformer timeBandTransformer = new JsonCourseTimeBandTransformer();
        if( jsonArray != null )
        {
            for( int i = 0; i < jsonArray.size(); i++ )
            {
                CursoTimeBand timeBand = timeBandTransformer.transform(jsonArray.get(i));
                if( timeBand != null ) times.add(timeBand);
                else return null;
            }
        }
        return times;
    }
}
