package com.e.videotoimage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
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
        eventClick();
    }

    private void eventClick() {
        btnSelectVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent selectVideoIntent = new Intent(MainActivity.this, SelectFolderActivity.class);
            startActivity(selectVideoIntent);
            }
        });

        //click btnShare
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent =new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Demo share");
                shareIntent.putExtra(Intent.EXTRA_TEXT,"Link App");
                shareIntent.setType("text/plain");
                startActivity(Intent.createChooser(shareIntent,"Chọn phương thức chia sẻ"));
            }
        });

        //click btnShowInfo
        btnShowInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder showInfoDialog = new AlertDialog.Builder(MainActivity.this);

                LayoutInflater inflater = getLayoutInflater();
                RelativeLayout dialogLayout = (RelativeLayout) inflater.inflate(R.layout.dialog_about_us,null);
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