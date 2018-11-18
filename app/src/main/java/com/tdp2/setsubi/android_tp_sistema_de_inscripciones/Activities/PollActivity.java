package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.CoursePeriod;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.PollPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.LoadingSnackbar;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.ToolBarHelper;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class PollActivity extends AppCompatActivity implements PollPresenter.View
{
    private RadioGroup intersting, dificulty, calification, yesNo;
    private EditText multiline;
    private TextView subjectName, peridod, subjectTeacher, pollQuestion, pollNumber;
    private Button pollAction;
    private Snackbar snackbar;
    private PollPresenter presenter;

    @Override
    protected void onResume()
    {
        super.onResume();
        AppModel.getInstance().setVisibility(true);
        presenter.loadPoll();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        AppModel.getInstance().setVisibility(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);

        ToolBarHelper.onCreate(this);
        ToolBarHelper.setTitle(this, R.string.poll);
        intersting = findViewById(R.id.poll_interesting_group);
        dificulty = findViewById(R.id.poll_dificulty_group);
        calification = findViewById(R.id.poll_calification_group);
        yesNo = findViewById(R.id.poll_yes_no_group);
        multiline = findViewById(R.id.multilneText);
        subjectName = findViewById(R.id.subject_name);
        peridod = findViewById(R.id.subject_period);
        subjectTeacher = findViewById(R.id.subject_teacher);
        pollAction = findViewById(R.id.poll_action_button);
        pollNumber = findViewById(R.id.question_number);
        pollQuestion = findViewById(R.id.question_text);
        presenter = new PollPresenter(this);
        pollAction.setOnClickListener(v -> {
            presenter.onClickedAction();
        });
    }

    @Override
    public int getStep()
    {
        if( getIntent() == null )
        {
            return -1;
        }
        return getIntent().getIntExtra("STEP", 0);
    }

    public void setPollIntersting()
    {
        setPoll(intersting);
    }

    public void setPollDificulty()
    {
        setPoll(dificulty);
    }

    public void setPollCalification()
    {
        setPoll(calification);
    }

    public void setPollYesNo()
    {
        setPoll(yesNo);
    }

    public void setPollText()
    {
        setPoll(multiline);
    }

    public void setQuestion(String string)
    {
        pollQuestion.setText(string);
    }

    public void setQuestionNumber(int acutal, int total)
    {
        pollNumber.setText(String.format( Locale.getDefault(),"%d/%d",acutal,total));
    }

    public void setFinalAction()
    {
        pollAction.setText("Enviar");
    }

    public int getCalification()
    {
        switch (calification.getCheckedRadioButtonId())
        {
            case R.id.excellent:
                return 5;
            case R.id.very_good:
                return 4;
            case R.id.good:
                return 3;
            case R.id.normal:
                return 2;
            case R.id.bad:
                return 1;
            case R.id.very_bad:
                return 0;
        }
        return -1;
    }

    public String getText()
    {
        return multiline.getEditableText().toString();
    }

    @Override
    public void setSubjectName(String name) {
        subjectName.setText(name);
    }

    @Override
    public void setPeriod(CoursePeriod.Period period, int year) {
        this.peridod.setText(String.format(Locale.getDefault(), "%d - %s",year, getPeriodString(period)));
    }

    private String getPeriodString(CoursePeriod.Period period)
    {
        switch (period)
        {
            case SUMMER:
                return "Verano";
            case FIRST:
                return "1C";
        }
        return "2C";
    }

    @Override
    public void setCatedra(String catedra) {
        subjectTeacher.setText(catedra);
    }

    @Override
    public void setYesNo(boolean yes) {
        yesNo.check(yes ? R.id.yes_radio : R.id.no_radio);
    }

    @Override
    public void setDificulty(int dificulty)
    {
        switch (dificulty)
        {
            case 5:
                this.dificulty.check(R.id.very_hard);
                break;
            case 4:
                this.dificulty.check(R.id.hard);
                break;
            case 3:
                this.dificulty.check(R.id.normal_diff);
                break;
            case 2:
                this.dificulty.check(R.id.easy);
                break;
            case 1:
                this.dificulty.check(R.id.very_easy);
                break;
        }
    }

    @Override
    public void setText(String text) {
        multiline.setText(text);
    }

    @Override
    public void setIntersting(int interesting) {
        switch (interesting)
        {
            case 5:
                this.intersting.check(R.id.very_interesting);
                break;
            case 4:
                this.intersting.check(R.id.interesting);
                break;
            case 3:
                this.intersting.check(R.id.normal_interesting);
                break;
            case 2:
                this.intersting.check(R.id.boring);
                break;
            case 1:
                this.intersting.check(R.id.very_boring);
                break;
        }
    }

    @Override
    public void setCalification(int calification)
    {
        switch (calification)
        {
            case 5:
                this.calification.check(R.id.excellent);
                break;
            case 4:
                this.calification.check(R.id.very_good);
                break;
            case 3:
                this.calification.check(R.id.good);
                break;
            case 2:
                this.calification.check(R.id.normal);
                break;
            case 1:
                this.calification.check(R.id.bad);
                break;
            case 0:
                this.calification.check(R.id.very_bad);
                break;
        }
    }

    @Override
    public void goToNextWith(int i)
    {
        Intent intent = new Intent(this, PollActivity.class);
        intent.putExtra("STEP", i);
        startActivity(intent);
    }

    @Override
    public void goBackToMain()
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void showError(int stringId) {
        Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getInteresting()
    {
        switch (intersting.getCheckedRadioButtonId())
        {
            case R.id.very_interesting:
                return 5;
            case R.id.interesting:
                return 4;
            case R.id.normal_interesting:
                return 3;
            case R.id.boring:
                return 2;
            case R.id.very_boring:
                return 1;
        }
        return -1;
    }

    @Override
    public int getDificulty() {
        switch (dificulty.getCheckedRadioButtonId())
        {
            case R.id.very_hard:
                return 5;
            case R.id.hard:
                return 4;
            case R.id.normal_diff:
                return 3;
            case R.id.easy:
                return 2;
            case R.id.very_easy:
                return 1;
        }
        return -1;
    }

    @Override
    public boolean getYes() {
        return yesNo.getCheckedRadioButtonId() == R.id.yes_radio;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return ToolBarHelper.onClickedHome(this);
    }

    private void setPoll(View groupToEnable)
    {
        List<View> groups = Arrays.asList(intersting, dificulty, yesNo, calification, multiline);
        for( View view : groups )
        {
            if( view == groupToEnable )
            {
                view.setVisibility(View.VISIBLE);
            } else
            {
                view.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void startLoading() {
        if( snackbar == null )
        {
            snackbar = LoadingSnackbar.createLoadingSnackBar(intersting);
            snackbar.show();
        }
    }

    @Override
    public void stopLoading() {
        if( snackbar != null )
        {
            snackbar.dismiss();
            snackbar = null;
        }
    }
}
