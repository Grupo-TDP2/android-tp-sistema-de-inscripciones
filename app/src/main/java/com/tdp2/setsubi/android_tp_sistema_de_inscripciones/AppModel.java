package com.tdp2.setsubi.android_tp_sistema_de_inscripciones;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Career;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Course;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Department;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;

import java.util.ArrayList;
import java.util.List;

public class AppModel
{
    private static AppModel instance = null;
    private Student student = null;
    private List<Career> studentCareers = null;
    private List<Subject> subjects = null;
    private Career selectedCareer = null;
    private Department selectedDepartment = null;
    private Subject selecteClass;
    private SubjectRoute route = SubjectRoute.COURSES;
    private Course selectedCourse = null;
    private boolean appVisible = false;

    public void setVisibility(boolean visibility)
    {
        this.appVisible = visibility;
    }

    public boolean isAppVisible()
    {
        return appVisible;
    }
    
    public Course getSelectedCourse() {
        return selectedCourse;
    }

    public enum SubjectRoute
    {
        COURSES, FINALS_OF_COURSE, FREE_FINALS
    }

    public static AppModel getInstance()
    {
        if( instance == null )
        {
            instance = new AppModel();
        }
        return instance;
    }

    private AppModel() {
        student = new Student(4,"Juan","Perez","");
    }

    public void login(Student student)
    {
        this.student = student;
    }

    public void logout()
    {
        this.student = null;
        this.studentCareers = null;
    }

    public List<Career> getStudentCareers() {
        return studentCareers;
    }

    public void setStudentCareers(List<Career> studentCareers) {
        this.studentCareers = studentCareers;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void selectedCareer(Career career)
    {
        selectedCareer = career;
    }

    public Career getSelectedCareer()
    {
        return selectedCareer;
    }

    public void setSelectedDepartment(Department selectedDepartment) {
        this.selectedDepartment = selectedDepartment;
    }

    public Department getSelectedDepartment() {
        return selectedDepartment;
    }

    public void setSelectedSubject(Subject selecteClass) {
        this.selecteClass = selecteClass;
    }

    public Subject getSelectedSubject()
    {
        return selecteClass;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public List<Department> getDepartments()
    {
        if( subjects != null )
        {
            List<Department> departments = new ArrayList<>();
            for( Subject subject : subjects )
            {
                if( !departments.contains(subject.getDepartment()) )
                {
                    departments.add(subject.getDepartment());
                }
            }
            return departments;
        } else {
            return null;
        }
    }

    public SubjectRoute getRoute() {
        return route;
    }

    public void setRoute(SubjectRoute route) {
        this.route = route;
    }

    public void setSelectedCourse(Course selectedCourse) {
        this.selectedCourse = selectedCourse;
    }
}
