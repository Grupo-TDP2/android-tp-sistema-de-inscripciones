package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Adapters.FinalsAdapter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Models.Final;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.FinalsPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.LoadingSnackbar;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.NotificationHelper;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.ToolBarHelper;

import java.util.List;

public class FinalsActivity extends AppCompatActivity implements LoadingView
{
    public interface Presenter extends FinalsAdapter.Listener {
        void loadFinals();
        List<Final> getFinals();
        String getSubjectName();
    }

    private FinalsAdapter adapter;
    private Presenter presenter;
    private RecyclerView recyclerView;
    private Snackbar snackbar = null;
    private NotificationHelper helper;

    @Override
    protected void onResume() {
        super.onResume();
        helper = new NotificationHelper(ToolBarHelper.getNotificationView(this));
        AppModel.getInstance().setVisibility(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        helper.destroy();
        AppModel.getInstance().setVisibility(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finals_of_subject);
        ToolBarHelper.onCreate(this);
        ToolBarHelper.setTitle(this, R.string.finals_title);
        TextView title = findViewById(R.id.finals_subject);
        presenter = new FinalsPresenter(this);
        title.setText(presenter.getSubjectName());

        recyclerView = findViewById(R.id.finals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL));

        adapter = new FinalsAdapter(presenter.getFinals(), presenter);
        recyclerView.setAdapter(adapter);
        presenter.loadFinals();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return ToolBarHelper.onClickedHome(this);
    }

    @Override
    public void startLoading()
    {
        if( snackbar == null )
        {
            snackbar = LoadingSnackbar.createLoadingSnackBar(recyclerView);
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

    public void showToast(int connectivityFailed) {
        Toast.makeText(this, connectivityFailed, Toast.LENGTH_SHORT).show();
    }

    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
    }

    public void notifyFinalChange(int interactingPosition) {
        adapter.notifyItemChanged(interactingPosition);
    }

    public void showNoFinalsAvailable() {
        findViewById(R.id.noFinals).setVisibility(View.VISIBLE);
    }

    public void setSubscriptionEnabled(boolean subscriptionEnabled) {
        adapter.setInteractionEnabled(subscriptionEnabled);
    }
}
