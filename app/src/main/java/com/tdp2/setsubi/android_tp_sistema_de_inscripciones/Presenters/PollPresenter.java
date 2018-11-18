package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities.LoadingView;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.CoursePeriod;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.MyCourse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Poll;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Services.ServiceResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Tasks.ServiceAsyncTask;

public class PollPresenter implements ServiceAsyncTask.ForeGroundListener
{
    public interface View extends LoadingView
    {
        int getStep();
        void setPollIntersting();
        void setPollDificulty();
        void setPollCalification();
        void setPollYesNo();
        void setPollText();
        void setQuestion(String string);
        void setQuestionNumber(int acutal, int total);
        void setFinalAction();
        int getCalification();
        String getText();
        void setSubjectName(String name);
        void setPeriod(CoursePeriod.Period period, int year);
        void setCatedra(String catedra);
        void setYesNo(boolean yes);
        void setDificulty(int dificulty);
        void setText(String text);
        void setIntersting(int interesting);
        void setCalification(int calification);
        void goToNextWith(int i);
        void goBackToMain();
        void showError(int stringId);
        int getInteresting();
        int getDificulty();
        boolean getYes();
    }

    private View view;
    private Poll poll;

    public PollPresenter(View view)
    {
        this.view = view;
        this.poll = AppModel.getInstance().getActualPoll().get(view.getStep());
    }

    public void loadPoll()
    {
        MyCourse course = AppModel.getInstance().getSelectedMyCourse();
        view.setSubjectName(course.getSubject().getName());
        view.setPeriod(course.getPeriod(), course.getYear());
        view.setCatedra(course.getCatedra());
        view.setQuestionNumber(view.getStep()+1,AppModel.getInstance().getActualPoll().size());
        view.setQuestion(poll.getQuestion());
        switch (poll.getType())
        {
            case YES_NO:
                view.setPollYesNo();
                view.setYesNo(((Poll.YesNoPoll)poll).isYes());
                break;
            case DIFICULTY:
                view.setPollDificulty();
                view.setDificulty(((Poll.DificultyPoll)poll).getDificulty());
                break;
            case FREE_TEXT:
                view.setPollText();
                view.setText(((Poll.FreeTextPoll)poll).getText());
                break;
            case INTERESTING:
                view.setPollIntersting();
                view.setIntersting(((Poll.InterstingPoll)poll).getInteresting());
                break;
            case CALIFICATION:
                view.setPollCalification();
                view.setCalification(((Poll.CalificationPoll)poll).getCalification());
                break;
        }
        if( view.getStep() + 1 == AppModel.getInstance().getActualPoll().size() )
        {
            view.setFinalAction();
        }
    }


    public void onClickedAction()
    {
        switch (poll.getType())
        {
            case CALIFICATION:
                ((Poll.CalificationPoll)poll).setCalification(view.getCalification());
                break;
            case INTERESTING:
                ((Poll.InterstingPoll)poll).setInteresting(view.getInteresting());
                break;
            case FREE_TEXT:
                ((Poll.FreeTextPoll)poll).setText(view.getText());
                break;
            case DIFICULTY:
                ((Poll.DificultyPoll)poll).setDificulty(view.getDificulty());
                break;
            case YES_NO:
                ((Poll.YesNoPoll)poll).setYes(view.getYes());
                break;
        }
        if( view.getStep() + 1 == AppModel.getInstance().getActualPoll().size() )
        {

            //TODO ASYNC TASK
        } else
        {
            view.goToNextWith(view.getStep()+1);
        }
    }

    @Override
    public void onError(ServiceAsyncTask serviceAsyncTask, ServiceResponse.ServiceStatusCode error) {
        view.stopLoading();
        if( error == ServiceResponse.ServiceStatusCode.NO_CONNECTION )
        {
            view.showError(R.string.connectivityFailed);
        } else
        {
            view.showError(R.string.poll_not_sent);
        }
    }

    @Override
    public void onSuccess(ServiceAsyncTask serviceAsyncTask, Object data) {
        view.stopLoading();
        view.goBackToMain();
    }

    @Override
    public void onStartingAsyncTask(ServiceAsyncTask serviceAsyncTask) {
        view.startLoading();
    }
}
