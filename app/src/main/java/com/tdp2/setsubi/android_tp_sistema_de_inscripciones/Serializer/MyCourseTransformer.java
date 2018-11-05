package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer;

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

import java.util.ArrayList;
import java.util.List;

public class MyCourseTransformer extends JsonTransformer<MyCourse>
{
    @Override
    public MyCourse transform(JsonElement object)
    {
        if( object.isJsonObject() )
        {
            JsonFiubaGetter parentGetter = new JsonFiubaGetter(object.getAsJsonObject());
            JsonGetter courseGetter = parentGetter.atObject(JsonKeys.COURSE);
            JsonGetter enrolmentGetter = courseGetter.atObject(JsonKeys.ENROLMENT);
            JsonGetter subjectGetter = courseGetter.atObject(JsonKeys.SUBJECT);
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
            List<CursoTimeBand> courseTimes = new ArrayList<>();
            boolean enabledToEnrol = courseGetter.asBoolean(JsonKeys.ENABLED_TO_ENROL);
            if( AnyNullCheck.any(courseId, cupo, catedra, sede, enabledToEnrol, courseTimes) ) return null;

            Integer year = schoolTermGetter.asInt(JsonKeys.YEAR);
            CoursePeriod.Period period = ((JsonFiubaGetter)schoolTermGetter).getPeriod(JsonKeys.TERM);
            if( AnyNullCheck.any(year, period) ) return null;

            Integer enrolmentId = enrolmentGetter.asInt(JsonKeys.ID);
            boolean canSeeFinals = false;
            //TODO DEPARTMENT
            return new MyCourse(new Career(1,""),
                    new Subject(subjectId, subjectCode, subjectName,  subjectCredits, new Department(1,"",75)),
                    new Course(courseId,catedra,sede, courseTimes, cupo, enrolmentId, enabledToEnrol),
                    new CoursePeriod(year, period),
                    canSeeFinals);
        }
        return null;
    }
}
