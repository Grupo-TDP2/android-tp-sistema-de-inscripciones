package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.widget.ImageView;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.NotificationHelper;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.ToolBarHelper;


public class MyDataEditionActivity extends AppCompatActivity
{
    private static final int REQUEST_GALLERY_PERMISSON = 12;
    private static final int GALLERY_PICK = 123;
    private TextInputEditText nameText, surnameText, emailText;
    private TextInputLayout nameLayout, surnameLayout, emailLayout;
    private ImageView userImage;
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
        setContentView(R.layout.activity_my_data_edition);
        ToolBarHelper.onCreate(this);
        ToolBarHelper.setTitle(this, R.string.my_data);
        ImageView cancelEdit = findViewById(R.id.cancel_edit_button);
        ImageView saveEdit = findViewById(R.id.save_edition_button);
        CardView userImage = findViewById(R.id.user_image_edit);
        nameText = findViewById(R.id.name_value);
        surnameText = findViewById(R.id.surname_value);
        emailText = findViewById(R.id.user_value);
        this.userImage = findViewById(R.id.user_editable_image);
        nameLayout = findViewById(R.id.namr_layout);
        surnameLayout = findViewById(R.id.surname_layout);
        emailLayout = findViewById(R.id.username_layout);
        cancelEdit.setOnClickListener(v -> MyDataEditionActivity.this.onBackPressed());
        saveEdit.setOnClickListener(v -> {

        });
        userImage.setOnClickListener(v -> {
            changeImageInGallery();
        });
    }

    public String getName()
    {
        return nameText.getEditableText().toString();
    }

    public String getSurname()
    {
        return surnameText.getEditableText().toString();
    }

    public String getEmail()
    {
        return emailText.getEditableText().toString();
    }

    public void showEmailError()
    {
        emailLayout.setError(getString(R.string.empty_user_name));
    }

    public void showSurnameError()
    {
        surnameLayout.setError(getString(R.string.empty_surname_error));
    }

    public void showNameError()
    {
        nameText.setError(getString(R.string.empty_name_error));
    }

    @Override
    public boolean onSupportNavigateUp() {
        return ToolBarHelper.onClickedHome(this);
    }

    private void changeImageInGallery()
    {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_GALLERY_PERMISSON);
        } else
        {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_PICK);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_PICK && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            Uri uriPath = data.getData();
            this.userImage.setImageURI(uriPath);
        }
    }
}
