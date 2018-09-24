package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Cursos;

import android.net.Uri;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Career;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Course;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonArrayTransformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonCourseTransformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonSubjectTransformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.APIUriBuilder;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.RequestPerformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

import java.util.List;

public class ServiceCoursesAPI implements ServiceCourses
{
    private Uri.Builder getCareersPath()
    {
        return  APIUriBuilder.getURIBuiled().appendPath("course_of_studies"); //TODO
    }

    private Uri.Builder getSubjetsPath(int careerId)
    {
        return getCareersPath().appendPath(String.valueOf(careerId)).appendPath("subjects");
    }

    private Uri.Builder getCoursesPath(int careerId, int subjectId)
    {
        return getSubjetsPath(careerId).appendPath(String.valueOf(subjectId)).appendPath("courses");
    }

    private Uri.Builder getEnrolmetsPath(int careerId, int subjectId, int courseId)
    {
        return getCoursesPath(careerId,subjectId).appendPath(String.valueOf(courseId)).appendPath("enrolments");
    }

    private ServiceCourses mock = new ServiceCourserMock();

    @Override
    public ServiceResponse<List<Career>> getCareers(Student student)
    {
        return mock.getCareers(student);
    }

    @Override
    public ServiceResponse<List<Subject>> getSubjects(Student student, Career career)
    {
        String path = getSubjetsPath(career.getId()).build().toString();
        return new RequestPerformer<>(path, RequestPerformer.Method.GET,
                new JsonArrayTransformer<>(new JsonSubjectTransformer()))
                .perform();
    }

    @Override
    public ServiceResponse<List<Course>> getCourses(Student student, Career career, Subject subject)
    {
        String path = getCoursesPath(career.getId(), subject.getId()).build().toString();
        return new RequestPerformer<>(path, RequestPerformer.Method.GET,
                new JsonArrayTransformer<>(new JsonCourseTransformer()))
                .perform();
    }

    @Override
    public ServiceResponse<Boolean> subscribeTo(Student student,  Career career, Subject subject, Course course) {
        return mock.subscribeTo(student, career, subject, course);
    }
}
