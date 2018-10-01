package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.Cursos;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Career;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.EnrolmentResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Course;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.CourseTime;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.CursoTimeBand;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Department;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Sede;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.DayOfWeek;

import java.util.Arrays;
import java.util.List;

public class ServiceCourserMock implements ServiceCourses
{
    private static final int CAREER_INFO = 1, CAREER_ELEC = 2, CAREER_ALIM = 3;
    private static final int DEPARTMENT_COMP = 75, DEPARTMENT_PHYSICS = 72, DEPARTMENT_MATH = 71;
    private static final int ANALISIS_2 = 7, ANALISIS_3 = 32, ANANLISIS_NUMERICO = 17, FISICA_1 = 1,
            FISICA_2 = 2, FISICA_3 = 3, ALG_Y_PROG_2 = 4;

    private List<Career> studentCareers = Arrays.asList(
            new Career(CAREER_INFO, "Ingenieria en Informatica"),
            new Career(CAREER_ELEC, "Ingenieria Electronica"),
            new Career(CAREER_ALIM, "Ingenieria Industrial"));

    private Department Computacion = new Department(DEPARTMENT_COMP, "Departamento de Computacion",DEPARTMENT_COMP);
    private Department Fisica = new Department(DEPARTMENT_PHYSICS, "Departamento de Fisica",DEPARTMENT_COMP);
    private Department Matematica = new Department(DEPARTMENT_MATH, "Departamento de Matematica",DEPARTMENT_COMP);

    private List<Department> infoDepartments = Arrays.asList(Computacion, Fisica);
    private List<Department> elecDepartments = Arrays.asList(Fisica, Matematica);
    private List<Department> alimDepartments = Arrays.asList(Computacion, Matematica);

    private Subject analisisNumerico = new Subject(1, ANANLISIS_NUMERICO,  "Analisis Numerico I B", 6, Computacion );
    private Subject analisis3 = new Subject(2, ANALISIS_2, "Analisis Matematico III", 6, Matematica );
    private Subject analisis2 = new Subject(3, ANALISIS_3, "Analisis Matematico II", 6, Matematica );
    private Subject fisica1 = new Subject(4, FISICA_1,"Fisica I", 6, Fisica );
    private Subject fisica2 = new Subject(5, FISICA_2,"Fisica II A", 6,Fisica );
    private Subject fisica3 = new Subject(6, FISICA_3,"Fisica III D", 6,Fisica );
    private Subject algoritmosYProgramacion2 = new Subject(7, ALG_Y_PROG_2, "Algoritmos y Programacion III", 6, Computacion );

    private List<Subject> compClasses = Arrays.asList(analisisNumerico, algoritmosYProgramacion2);
    private List<Subject> physicsClasses = Arrays.asList(fisica1, fisica2, fisica3);
    private List<Subject> mathClasses = Arrays.asList(analisis3, analisis2);

    private List<CursoTimeBand> times = Arrays.asList(new CursoTimeBand(DayOfWeek.MONDAY, 203,
            new CourseTime(17,0),
            new CourseTime(20,0),
            CursoTimeBand.CursoTimeType.TEORICO, false),
            new CursoTimeBand(DayOfWeek.TUESDAY, 221,
                    new CourseTime(17,0),
                    new CourseTime(20,0),
                    CursoTimeBand.CursoTimeType.PRACTIO, true
            ));
    private Course numerico1 = new Course(1,"Griggo", Sede.PASEO_COLON, times,10);
    private Course numerico2 = new Course(2,"Opatowski", Sede.PASEO_COLON, times,15);
    private List<Course> numericoCourses = Arrays.asList(numerico1, numerico2);
    private Course alg21 = new Course(1,"Wachenchauzer", Sede.PASEO_COLON, times,10);
    private Course alg22 = new Course(2,"Mendez", Sede.PASEO_COLON, times,15);
    private List<Course> algoritmosCourses = Arrays.asList(alg21, alg22);
    private Course an11 = new Course(1,"Prelat", Sede.PASEO_COLON, times,10);
    private Course an12 = new Course(2,"Cachile", Sede.PASEO_COLON, times,15);
    private List<Course> analisis1Courses = Arrays.asList(an11, an12);
    private Course an31 = new Course(1,"Acero", Sede.PASEO_COLON, times,10);
    private Course an32 = new Course(2,"Prelat", Sede.PASEO_COLON, times,15);
    private List<Course> analis3Courses = Arrays.asList(an31, an32);
    private Course f11 = new Course(1,"Sirne", Sede.PASEO_COLON, times,10);
    private Course f12 = new Course(2,"Lopez", Sede.PASEO_COLON, times,15);
    private List<Course> fisica1Courses = Arrays.asList(f11, f12);
    private Course f21 = new Course(1,"Sirne", Sede.PASEO_COLON, times,10);
    private Course f22 = new Course(2,"Guitierrez", Sede.PASEO_COLON, times,15);
    private List<Course> fisica2Courses = Arrays.asList(f21, f22);
    private Course f31 = new Course(1,"Madero", Sede.PASEO_COLON, times,10);
    private Course f32 = new Course(2,"Sirne", Sede.PASEO_COLON, times,15);
    private List<Course> fisica3Courses = Arrays.asList(f31, f32);

    @Override
    public ServiceResponse<List<Career>> getCareers(Student student)
    {
        return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.SUCCESS, studentCareers);
    }


    @Override
    public ServiceResponse<List<Subject>> getSubjects(Student student, Career career)
    {
        List<Subject> classes = Arrays.asList(analisis2, analisis3, analisisNumerico,
                fisica3, fisica2, fisica1, algoritmosYProgramacion2);
        return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.SUCCESS, classes);
    }

    @Override
    public ServiceResponse<List<Course>> getCourses(Student student, Career career, Subject subject)
    {
        List<Course> courses = null;
        switch (subject.getDepartmentCode())
        {
            case DEPARTMENT_COMP:
                switch (subject.getCode())
                {
                    case ALG_Y_PROG_2:
                        courses = algoritmosCourses;
                        break;
                    case ANANLISIS_NUMERICO:
                        courses = numericoCourses;
                        break;
                }
                break;
            case DEPARTMENT_MATH:
                switch (subject.getCode())
                {
                    case ANALISIS_2:
                        courses = analisis1Courses;
                        break;
                    case ANALISIS_3:
                        courses = analis3Courses;
                        break;
                }
                break;
            case DEPARTMENT_PHYSICS:
                switch (subject.getCode())
                {
                    case FISICA_1:
                        courses = fisica1Courses;
                        break;
                    case FISICA_2:
                        courses = fisica2Courses;
                        break;
                    case FISICA_3:
                        courses = fisica3Courses;
                        break;
                }
                break;
        }
        if( courses == null )
        {
            return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.ERROR);
        } else return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.SUCCESS, courses);
    }

    @Override
    public ServiceResponse<EnrolmentResponse> subscribeTo(Student student, Career career, Subject subject, Course course)
    {
        return new ServiceResponse<>(ServiceResponse.ServiceStatusCode.SUCCESS, new EnrolmentResponse(course.getId(), EnrolmentResponse.EnrolmentType.REGULAR));
    }
}
