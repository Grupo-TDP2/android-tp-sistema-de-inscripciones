package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Cursos;

import android.net.Uri;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Career;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.EnrolmentResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.MyCourse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Course;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonArrayTransformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonCareerTransformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonCourseTransformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonEnrolmentTransformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.JsonSubjectTransformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Serializer.MyCourseTransformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.APIUriBuilder;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.ContentType;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestBuilder;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestMethod;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestPerformer;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Requests.RequestProperty;
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

    private RequestBuilder getBuilder =  new RequestBuilder(RequestMethod.GET);
    @Override
    public ServiceResponse<List<Career>> getCareers(Student student)
    {
        return new RequestPerformer<>(getCareersPath().toString(), new RequestBuilder(RequestMethod.GET)
                .addRequestProperty(RequestProperty.AUTHORIZATION.getKey(), student.getAuthorization()),
                new JsonArrayTransformer<>(new JsonCareerTransformer())).perform();
    }

    @Override
    public ServiceResponse<List<Subject>> getSubjects(Student student, Career career)
    {
        String path = getSubjetsPath(career.getId()).build().toString();
        return new RequestPerformer<>(path, new RequestBuilder(RequestMethod.GET)
                .addRequestProperty(RequestProperty.AUTHORIZATION.getKey(), student.getAuthorization()),
                new JsonArrayTransformer<>(new JsonSubjectTransformer()))
                .perform();
    }

    @Override
    public ServiceResponse<List<Course>> getCourses(Student student, Career career, Subject subject)
    {
        String path = getCoursesPath(career.getId(), subject.getId()).build().toString();
        return new RequestPerformer<>(path, new RequestBuilder(RequestMethod.GET)
                .addRequestProperty(RequestProperty.AUTHORIZATION.getKey(), student.getAuthorization()),
                new JsonArrayTransformer<>(new JsonCourseTransformer()))
                .perform();
    }

    @Override
    public ServiceResponse<EnrolmentResponse> subscribeTo(Student student, Career career, Subject subject, Course course)
    {
        String path = getEnrolmetsPath(career.getId(), subject.getId(), course.getId()).toString();
        return new RequestPerformer<>(path,
                new RequestBuilder(RequestMethod.POST)
                    .addRequestProperty(RequestProperty.AUTHORIZATION.getKey(), student.getAuthorization())
                    .addRequestProperty(RequestProperty.CONTENT_TYPE.getKey(), ContentType.JSON.getValue()),
                     new JsonEnrolmentTransformer())
                .perform();
    }

    @Override
    public ServiceResponse<List<MyCourse>> getCourses(Student student) {
        return new RequestPerformer<>(APIUriBuilder.getURIBuiled()
                .appendPath("students")
                .appendPath("me")
                .appendPath("pending_exam_courses").toString(),
                new RequestBuilder(RequestMethod.GET)
                .addRequestProperty(RequestProperty.AUTHORIZATION, student.getAuthorization()),
                new JsonArrayTransformer<>(new MyCourseTransformer())).perform();
    }
}
