package com.monet.bbc.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.monet.bbc.R;
import com.monet.bbc.utils.AppPreference;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.monet.bbc.utils.AppPreference.getImageBase64;
import static com.monet.bbc.utils.AppPreference.setImageBase64;
import static com.monet.bbc.utils.AppUtils.convertBase64ToBitmap;
import static com.monet.bbc.utils.AppUtils.convertCameraImageToBase64;
import static com.monet.bbc.utils.AppUtils.convertGalleryImageToBase64;
import static com.monet.bbc.utils.AppUtils.openUtilityDialog;

public class EditProfileScreen extends AppCompatActivity {

    public static final int PICK_FROM_CAMERA = 1001, LOAD_FROM_GALLERY = 1002;
    private String photoBase64 = "";
    private Uri mImageCaptureUri;
    private CircleImageView userImage;
    private TextView tv_done;
    private RelativeLayout rl_editImage;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_screen);

        userImage = findViewById(R.id.img_editProfile);
        tv_done = findViewById(R.id.tv_done);
        rl_editImage = findViewById(R.id.rl_editImage);
        toolbar = findViewById(R.id.toolbar_editProfile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        setDataUi();

        tv_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(photoBase64 != ""){
                    setImageBase64(EditProfileScreen.this, photoBase64);
                }
                Toast.makeText(EditProfileScreen.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

        rl_editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkExternalStoragePermission()) {
                    openIntent();
                } else {
                    requestExternalStoragePermission();
                }
            }
        });
    }

    private void setDataUi() {


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }


    private void openIntent() {
        final android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(this,
                R.style.CustomAlertDialogStyle);
        dialog.setMessage("Take Profile Activity Picture");
        dialog.setPositiveButton("Gallery",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, LOAD_FROM_GALLERY);
                        dialog.dismiss();
                    }
                });

        dialog.setNeutralButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        dialog.setNegativeButton("Camera",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (checkCameraPermission()) {
                            openCamera();
                        } else {
                            requestCameraPermission();
                        }
                        dialog.dismiss();
                    }
                });
        dialog.show();
    }

    private void requestExternalStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            openUtilityDialog(this, "External Storage permission is required. Please allow this permission in App Settings.");
        } else {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1011);
        }
    }

    private boolean checkExternalStoragePermission() {
        int result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) + ContextCompat
                .checkSelfPermission(this,
                        Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkCameraPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            openUtilityDialog(this, "Camera permission is required. Please allow this permission in App Settings.");
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1012);
        }
    }

    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, PICK_FROM_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.DialogTheme);
            alertDialog.setMessage("You Have To Give Permission From Your Device Setting To go in Setting Please Click on Settings Button");
            alertDialog.setCancelable(false);
            alertDialog.setPositiveButton("Go To Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            });
            alertDialog.show();
        } else {
            openIntent();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        userImage.setImageBitmap(null);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case PICK_FROM_CAMERA:
                    try {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        photoBase64 = convertCameraImageToBase64(photo);
                        setImageBase64(this, photoBase64);
                        userImage.setImageBitmap(convertBase64ToBitmap(getImageBase64(this)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case LOAD_FROM_GALLERY:
                    if (data != null) {
                        mImageCaptureUri = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(mImageCaptureUri, filePathColumn,
                                null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();
                        photoBase64 = convertGalleryImageToBase64(picturePath);
                        setImageBase64(this, photoBase64);
                        userImage.setImageBitmap(convertBase64ToBitmap(getImageBase64(this)));
                    }
                    break;
            }
        }
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return super.onNavigateUp();
    }

    @Override
    protected void onResume() {
        if (getImageBase64(this).isEmpty()) {
            Glide.with(this).load("https://www.serveit.com/media/1207/alan-mac-kenna-1-small.jpg").into(userImage);
        } else {
            userImage.setImageBitmap(convertBase64ToBitmap(getImageBase64(this)));
        }
        super.onResume();
    }
}
