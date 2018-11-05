package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import android.content.Context;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.LoadingView;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.ApprovedSubject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Department;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Subject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.GetApprovedSubject;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.ServiceAsyncTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ApprovedSubjectPresenter implements ServiceAsyncTask.ForeGroundListener, Comparator<ApprovedSubject> {

    public interface View extends LoadingView
    {
        void dataSetChanged();
        void showNoSubjects();
        void showError(int resourceId);
        void setSpinnerOptions(List<String> options);
        Context getContext();
    }

    private View view;
    private List<ApprovedSubject> subjects;
    private List<Department> departments;
    private List<ApprovedSubject> filtered;

    public ApprovedSubjectPresenter(View view)
    {
        this.view = view;
        subjects = new ArrayList<>();
        departments = new ArrayList<>();
        filtered = new ArrayList<>();
    }

    public List<ApprovedSubject> getSubjects() {
        return filtered;
    }

    public void loadSubjects()
    {
        /*List<ApprovedSubject> approvedSubjects = new ArrayList<>();
        approvedSubjects.add(new ApprovedSubject(new Subject(1,12,"Organizacion de datos", 6,
                new Department(1,"Infromatica",75)), new Date(), 5));

        approvedSubjects.add(new ApprovedSubject(new Subject(1,15,"Organizacion del Computador", 6,
                new Department(1,"Infromatica",75)), new Date(), 8));
        onSuccess(null, approvedSubjects);*/
        new GetApprovedSubject(this).execute(AppModel.getInstance().getStudent());
    }

    @Override
    public void onError(ServiceAsyncTask serviceAsyncTask, ServiceResponse.ServiceStatusCode error)
    {
        view.stopLoading();
        view.showError(error == ServiceResponse.ServiceStatusCode.NO_CONNECTION ? R.string.connectivityFailed : R.string.error_approved_subjects);
    }

    @Override
    public void onSuccess(ServiceAsyncTask serviceAsyncTask, Object data)
    {
        view.stopLoading();
        subjects.clear();
        subjects.addAll((List<ApprovedSubject>)data);
        Collections.sort(subjects, this);
        createDepartments(subjects);
        filtered.addAll(subjects);
        view.dataSetChanged();
        view.setSpinnerOptions(getDepartmentFilters());
        if( subjects.size() == 0 )
        {
            view.showNoSubjects();
        }
    }

    public List<String> getDepartmentFilters()
    {
        List<String> strings = new ArrayList<>();
        strings.add(view.getContext().getString(R.string.all_departments));
        for( Department department : departments )
        {
            strings.add(String.format(Locale.getDefault(),"%02d - %s", department.getCode(), department.getName()));
        }
        return strings;
    }

    public void onFilterSelected(int position)
    {
        filtered.clear();
        if( position > 0 )
        {
            position -= 1;
            Department selected = departments.get(position);
            for( ApprovedSubject subject : subjects )
            {
                if( subject.getSubject().getDepartment().getId() == selected.getId() )
                {
                    filtered.add(subject);
                }
            }
        } else
        {
            filtered.addAll(subjects);
        }
        view.dataSetChanged();
    }

    private void createDepartments(List<ApprovedSubject> subjects)
    {
        departments.clear();
        for(ApprovedSubject subject : subjects )
        {
            Department approvedDepartment = subject.getSubject().getDepartment();
            boolean add = true;
            for( Department department : departments )
            {
                if( department.getId() == approvedDepartment.getId() )
                {
                    add = false;
                    break;
                }
            }
            if( add )
            {
                departments.add(approvedDepartment);
            }
        }
        Collections.sort(departments, new Comparator<Department>() {
            @Override
            public int compare(Department o1, Department o2) {
                return o1.getCode() - o2.getCode();
            }
        });
    }

    @Override
    public void onStartingAsyncTask(ServiceAsyncTask serviceAsyncTask) {
        view.startLoading();
    }

    @Override
    public int compare(ApprovedSubject o1, ApprovedSubject o2)
    {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(o1.getApprovedDate());
        cal2.setTime(o2.getApprovedDate());
        int year1 = cal1.get(Calendar.YEAR),
        year2 = cal2.get(Calendar.YEAR),
        day1 = cal1.get(Calendar.DAY_OF_YEAR),
        day2 = cal2.get(Calendar.DAY_OF_YEAR);
        if( year1 == year2 )
        {
            if( day1 == day2 )
            {
                if( o1.getSubject().getDepartmentCode() == o2.getSubject().getDepartmentCode() )
                {
                    return Integer.compare(o1.getSubject().getCode(), o2.getSubject().getCode());
                } else if( o1.getSubject().getDepartmentCode() < o2.getSubject().getDepartmentCode() )
                {
                    return -1;
                }
            } else if( day1 < day2)
            {
                return -1;
            }
        } else if( year1 < year2 )
        {
            return -1;
        }
        return 1;
    }
}
