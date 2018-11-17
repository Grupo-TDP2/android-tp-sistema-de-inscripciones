package com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.AppModel;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Presenters.MyDataEditionPresenter;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.R;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.BitmapEncoder;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.LoadingSnackbar;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.NotificationHelper;
import com.tdp2.setsubi.android_tp_sistema_de_inscripciones.Utils.ToolBarHelper;

import java.io.IOException;


public class MyDataEditionActivity extends AppCompatActivity implements MyDataEditionPresenter.View
{
    private static final int REQUEST_GALLERY_PERMISSON = 12;
    private static final int GALLERY_PICK = 123;
    private TextInputEditText nameText, surnameText, emailText;
    private TextInputLayout nameLayout, surnameLayout, emailLayout;
    private ImageView userImage;
    private NotificationHelper helper;
    private MyDataEditionPresenter presenter;
    private Snackbar snackbar = null;

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
        presenter = new MyDataEditionPresenter(this);
        cancelEdit.setOnClickListener(v -> MyDataEditionActivity.this.onBackPressed());
        saveEdit.setOnClickListener(v -> {
            presenter.onSaveClicked();
        });
        userImage.setOnClickListener(v -> {
            changeImageInGallery();
        });
        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                nameLayout.setError(null);
            }
        });
        surnameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                surnameLayout.setError(null);
            }
        });
        emailText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                emailLayout.setError(null);
            }
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

    @Override
    public void setName(String name) {
        nameText.setText(name);
    }

    @Override
    public void setImage(String uriPath)
    {
        if( uriPath == null )
        {
            userImage.setImageResource(R.drawable.ic_user_silhouette);
        } else
        {
            userImage.setImageBitmap(BitmapEncoder.decodeBase64(uriPath));
            /*try {
                userImage.setImageBitmap( MediaStore.Images.Media
                        .getBitmap(getContentResolver(), uriPath));
            } catch (IOException e)
            {
                userImage.setImageResource(R.drawable.ic_user_silhouette);
            }*/
        }
    }

    @Override
    public void setSurname(String surname) {
        surnameText.setText(surname);
    }

    @Override
    public void setEmail(String email) {
        emailText.setText(email);
    }

    @Override
    public void showMessage(int stringId) {
        Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goBack() {
        onBackPressed();
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
        nameLayout.setError(getString(R.string.empty_name_error));
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
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
            startActivityForResult(Intent.createChooser(intent, "Seleccione una imagen"), GALLERY_PICK);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_GALLERY_PERMISSON: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Seleccione una imagen"), GALLERY_PICK);
                } else
                {
                    showMessage(R.string.gallery_disabled);
                }
            }
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
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uriPath);
                presenter.onImageChange(BitmapEncoder.encodeToBase64(bitmap,Bitmap.CompressFormat.JPEG, 100));
                this.userImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void startLoading() {
        if( snackbar == null )
        {
            snackbar = LoadingSnackbar.createLoadingSnackBar(userImage);
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

    private String getUriPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(projection[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }
}
