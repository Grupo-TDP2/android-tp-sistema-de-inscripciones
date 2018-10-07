package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.LoginPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.LoadingSnackbar;

public class LoginActivity extends AppCompatActivity implements LoadingView
{
     public interface Presenter
    {
        void onClickedLogin();
    }
    private Presenter presenter;
    private TextInputEditText password;
    private TextInputLayout passwordLayout;
    private TextInputEditText userName;
    private TextInputLayout usernameLayout;
    private Snackbar snackbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_login_layout);
        presenter = new LoginPresenter(this);
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        userName = findViewById(R.id.user_value);
        password = findViewById(R.id.password_value);
        usernameLayout = findViewById(R.id.username_layout);
        passwordLayout = findViewById(R.id.password_layout);
        findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickedLogin();
            }
        });
    }

    public String getUserName()
    {
        return userName.getEditableText().toString().trim();
    }

    public String getPassword()
    {
        return password.getEditableText().toString();
    }

    public void showUserNameEmpty()
    {
        usernameLayout.setError(getString(R.string.empty_user_name));
    }

    public void showPasswordempty()
    {
        passwordLayout.setError(getString(R.string.valid_password));
    }

    public void showIsLoginIn()
    {
        Toast.makeText(this, R.string.wait_for_login, Toast.LENGTH_SHORT).show();
    }

    public void showFailedLogin(int message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.create().show();
    }

    public void goToMainScreen()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void startLoading() {
        snackbar = LoadingSnackbar.createLoadingSnackBar(password);
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
