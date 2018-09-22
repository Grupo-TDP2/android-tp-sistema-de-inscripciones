package com.tdp2.setsubi.android_tp_sistema_de_inscripciones;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Career;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.ClassModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Department;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Student;

import java.util.List;

public class AppModel
{
    private static AppModel instance = null;
    private Student student = null;
    private List<Career> studentCareers = null;
    private Career selectedCareer = null;
    private Department selectedDepartment = null;
    private ClassModel selecteClass;

    public static AppModel getInstance()
    {
        if( instance == null )
        {
            instance = new AppModel();
        }
        return instance;
    }

    private AppModel() {
        student = new Student(1,"Juan","Perez");
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

    public void setSelecteClass(ClassModel selecteClass) {
        this.selecteClass = selecteClass;
    }

    public ClassModel getSelecteClass()
    {
        return selecteClass;
    }
}
