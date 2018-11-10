package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.LoadingView;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.MyCoursesAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.ActionPeriod;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.CoursePeriod;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.MyCourse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.GetFinalsPeriodAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.GetMyCoursesTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.ServiceAsyncTask;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.UnsubscribeToCourseAsyncTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class MyCoursesPresenter implements ServiceAsyncTask.ForeGroundListener, Comparator<MyCourse>,MyCoursesAdapter.Listener {

    public interface View extends LoadingView
    {
        void showNoCourses();
        void showErrorLoading(int stringResource);
        void goToFinals();
        void onRemoved(int position);
        void onLoadedCourses();
        void showSuccess(int unsubscribed_course_success);
        void setCanSeeFinals(boolean canSeeFinals);
    }

    private View view;
    private List<MyCourse> coursesList;
    private Integer index = null;
    private int loadingViews = 0;

    private void loadedOne()
    {
        loadingViews++;
        if( loadingViews == 2 )
        {
            view.stopLoading();
            loadingViews = 0;
        }
    }

    public MyCoursesPresenter(View view)
    {
        this.view = view;
        this.coursesList = new ArrayList<>();
    }
    public List<MyCourse> getCoursesList()
    {
        return coursesList;
    }

    public void loadData()
    {
        new GetFinalsPeriodAsyncTask(this).execute(AppModel.getInstance().getStudent());
        new GetMyCoursesTask(this).execute(AppModel.getInstance().getStudent());
    }

    @Override
    public void onError(ServiceAsyncTask serviceAsyncTask, ServiceResponse.ServiceStatusCode error)
    {
        view.stopLoading();
        int errorMessage = R.string.connectivityFailed;
        if( error == ServiceResponse.ServiceStatusCode.NO_CONNECTION )
        {
            if (serviceAsyncTask instanceof GetMyCoursesTask)
            {
                loadedOne();
                errorMessage = R.string.error_while_loading_my_courses;
            } else if( serviceAsyncTask instanceof UnsubscribeToCourseAsyncTask)
            {
                errorMessage = R.string.error_unsubscribe_course_out_of_period;
            } else
            {
                loadedOne();
            }
        }
        view.showErrorLoading(errorMessage);
    }

    @Override
    public void onSuccess(ServiceAsyncTask serviceAsyncTask, Object data)
    {
        if( serviceAsyncTask instanceof GetMyCoursesTask )
        {
            loadedOne();
            List<MyCourse> courses = (List<MyCourse>)data;
            coursesList.clear();
            coursesList.addAll(courses);
            Collections.sort(coursesList, this);
            view.onLoadedCourses();
            checkCoursesSize();
        } else if( serviceAsyncTask instanceof UnsubscribeToCourseAsyncTask && index != null )
        {
            view.stopLoading();
            coursesList.remove((int)index);
            view.onRemoved(index);
            checkCoursesSize();
            view.showSuccess(R.string.unsubscribed_course_success);
        } else if( serviceAsyncTask instanceof GetFinalsPeriodAsyncTask)
        {
            loadedOne();
            ActionPeriod period = (ActionPeriod) data;
            view.setCanSeeFinals(period.isDateInPeriod(new Date()));
        }
    }

    @Override
    public void onStartingAsyncTask(ServiceAsyncTask serviceAsyncTask)
    {
        view.startLoading();
    }

    @Override
    public void onUnsubscribe(int position)
    {
        MyCourse course = coursesList.get(position);
        new UnsubscribeToCourseAsyncTask(this).execute(AppModel.getInstance(),course.getCareer(), course.getSubject(), course.getCourse());
    }

    @Override
    public void onSeeFinals(int position)
    {
        AppModel.getInstance().selectedCareer(coursesList.get(position).getCareer());
        AppModel.getInstance().setSelectedSubject(coursesList.get(position).getSubject());
        AppModel.getInstance().setSelectedCourse(coursesList.get(position).getCourse());
        AppModel.getInstance().setRoute(AppModel.SubjectRoute.FINALS_OF_COURSE);
        view.goToFinals();
    }

    @Override
    public int compare(MyCourse o1, MyCourse o2)
    {
        int yearDifference = o1.getYear() - o2.getYear();
        if( yearDifference == 0 )
        {
            CoursePeriod.Period from1 = o1.getPeriod();
            CoursePeriod.Period from2 = o2.getPeriod();
            if( from1 == from2 )
            {
                int departmentDifference = o1.getSubject().getDepartmentCode() - o2.getSubject().getDepartmentCode();
                if( departmentDifference == 0 )
                {
                    return Integer.compare(o1.getSubject().getCode(), o2.getSubject().getCode());
                }
                return Integer.compare(departmentDifference, 0);
            } else
            {
                return comparePeriod(from1, from2);
            }
        } else if( yearDifference < 0 )
        {
            return -1;
        }
        return 1;
    }

    private int comparePeriod(CoursePeriod.Period period1, CoursePeriod.Period period2)
    {
        switch (period1)
        {
            case SUMMER:
                return -1;
            case SECOND:
                return 1;
            case FIRST: default:
                if(period2 == CoursePeriod.Period.SUMMER)
                {
                    return 1;
                }
                return -1;
        }
    }

    private void checkCoursesSize()
    {
        if(coursesList.size() == 0)
        {
            view.showNoCourses();
        }
    }
}
