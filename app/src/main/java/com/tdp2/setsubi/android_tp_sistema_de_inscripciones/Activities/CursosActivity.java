package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.CursoAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.EnrolmentResponse;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.CursosPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.LoadingSnackbar;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.ToolBarHelper;

public class CursosActivity extends AppCompatActivity implements LoadingView
{
    private CursosLogic presenter;
    private Snackbar snackbar;
    private TextView noHay;

    public void showNoCoursesAvailable()
    {
        noHay.setVisibility(View.VISIBLE);
    }

    public interface CursosLogic
    {
        CursoAdapter getAdapter();
        String getCourseName();
        void loadData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursos);
        noHay = findViewById(R.id.noHayDisponibles);
        ToolBarHelper.onCreate(this);
        presenter = new CursosPresenter(this);
        ToolBarHelper.setTitle(this, R.string.cursos);
        TextView title = findViewById(R.id.bottomTitle);


        title.setText(presenter.getCourseName());
        RecyclerView view = findViewById(R.id.cursos_list);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.setAdapter(presenter.getAdapter());
        presenter.loadData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return ToolBarHelper.onClickedHome(this);
    }

    public void onFailedtoConnectivity() {
        Toast.makeText(this, R.string.connectivityFailed, Toast.LENGTH_SHORT).show();
    }

    public void showSuccessResponse(EnrolmentResponse response)
    {
        String condition = getString(response.getEnrolmentType());
        Toast.makeText(this, this.getString(R.string.success_enrolment,condition), Toast.LENGTH_SHORT).show();
    }

    private String getString(EnrolmentResponse.EnrolmentType enrolmentType) {
        switch (enrolmentType)
        {
            case REGULAR:
                return "regular";
        }
        return "condicional";
    }

    public void onFailedToEnroll() {
        Toast.makeText(this, R.string.failed_enrolment_error, Toast.LENGTH_SHORT).show();
    }

    public void showStillSubscribing() {
        Toast.makeText(this, R.string.wait_till_subscription_ends, Toast.LENGTH_SHORT).show();
    }

    public void showAlreadySubscribed() {
        Toast.makeText(this, R.string.failed_enrolment, Toast.LENGTH_SHORT).show();
    }

    public void showMessage(int stringResource) {
        Toast.makeText(this, stringResource, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startLoading() {
        stopLoading();
        snackbar = LoadingSnackbar.createLoadingSnackBar(findViewById(R.id.cursos_list));
        snackbar.show();
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
