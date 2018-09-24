package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Cursos;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Career;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Course;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Department;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

import java.util.List;

public interface ServiceCourses
{
    ServiceResponse<List<Career>> getCareers(Student student);
    ServiceResponse<List<Subject>> getSubjects(Student student, Career career);
    ServiceResponse<List<Course>> getCourses(Student student, Career career, Subject subject);
    ServiceResponse<Boolean> subscribeTo(Student student, Career career, Subject subject, Course course);
}
