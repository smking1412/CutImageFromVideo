package com.e.videotoimage;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_PERMISSION = 10;
    private ConstraintLayout btnSelectVideo;
    private ConstraintLayout btnGallery;
    private ConstraintLayout btnMakeSlideshow;
    private ConstraintLayout btnSetting;
    private ConstraintLayout btnShare;
    private ConstraintLayout btnRate;
    private ConstraintLayout btnShowInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        checkPermission();
        eventClick();
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
        } else {
            Toast.makeText(this, "Đã cấp quyền", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Đã cấp quyền", Toast.LENGTH_SHORT).show();
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
            }
        }
    }

    private void eventClick() {
        btnSelectVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selectFolderIntent = new Intent(MainActivity.this, SelectFolderActivity.class);
                startActivity(selectFolderIntent);
            }
        });

        //click btnShare
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Demo share");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "Link App");
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent, "Chọn phương thức chia sẻ"));
            }
        });

        //click btnShowInfo
        btnShowInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder showInfoDialog = new AlertDialog.Builder(MainActivity.this);

                LayoutInflater inflater = getLayoutInflater();
                RelativeLayout dialogLayout = (RelativeLayout) inflater.inflate(R.layout.dialog_about_us, null);
                showInfoDialog.setView(dialogLayout);
                showInfoDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                showInfoDialog.show();
            }
        });
    }

    private void initView() {
        btnSelectVideo = findViewById(R.id.constrain_select_video);
        btnGallery = findViewById(R.id.constrain_gallery);
        btnMakeSlideshow = findViewById(R.id.constrain_slideshow_maker);
        btnShare = findViewById(R.id.constrain_share);
        btnRate = findViewById(R.id.constrain_rate);
        btnShowInfo = findViewById(R.id.constrain_about_us);
    }
}