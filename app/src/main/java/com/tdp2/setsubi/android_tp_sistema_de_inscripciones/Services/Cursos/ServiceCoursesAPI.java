package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Cursos;

import android.net.Uri;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Career;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.ClassModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Course;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Department;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;

import java.util.List;

public class ServiceCoursesAPI implements ServiceCourses
{
    @Override
    public ServiceResponse<List<Career>> getCareers(Student student)
    {
        return null;
    }

    @Override
    public ServiceResponse<List<ClassModel>> getClasses(Student student, Career career, Department department) {
        return null;
    }

    @Override
    public ServiceResponse<List<Department>> getDepartments(Student student, Career career) {
        return null;
    }

    @Override
    public ServiceResponse<List<Course>> getCourses(Student student, ClassModel classModel) {
        return null;
    }

    @Override
    public ServiceResponse<Boolean> subscribeTo(Student student, Course course) {
        return null;
    }
}
