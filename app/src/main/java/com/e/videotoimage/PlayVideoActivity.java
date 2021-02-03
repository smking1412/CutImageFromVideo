package com.e.videotoimage;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class PlayVideoActivity extends AppCompatActivity {
    private VideoView videoView;
    private MediaController mediaController;
    private Toolbar toolbar;
    private TextView nameVideo;
    private Button captureImage;
    MediaMetadataRetriever mediaMetadataRetriever;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        videoView = findViewById(R.id.videoView);
        toolbar = findViewById(R.id.toolbarPlay);
        nameVideo = findViewById(R.id.tv_playvd_name);
        captureImage = findViewById(R.id.btn_screen_video);

        String videoName = getIntent().getStringExtra("videoname");
        nameVideo.setText(videoName);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String localPath = getIntent().getStringExtra("videopath");
        Uri uri = Uri.parse(localPath);

        mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(localPath);
        videoView.setVideoURI(uri);
        videoView.start();
        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        captureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = videoView.getCurrentPosition(); //in millisecond
                Toast.makeText(PlayVideoActivity.this,
                        "Current Position: " + currentPosition + " (ms)",
                        Toast.LENGTH_LONG).show();

                Bitmap bmFrame = mediaMetadataRetriever
                        .getFrameAtTime(currentPosition * 1000); //unit in microsecond

                if (bmFrame == null) {
                    Toast.makeText(PlayVideoActivity.this,
                            "bmFrame == null!",
                            Toast.LENGTH_LONG).show();
                } else {
                    AlertDialog.Builder myCaptureDialog =
                            new AlertDialog.Builder(PlayVideoActivity.this);
                    ImageView capturedImageView = new ImageView(PlayVideoActivity.this);
                    capturedImageView.setImageBitmap(bmFrame);
                    LinearLayout.LayoutParams capturedImageViewLayoutParams =
                            new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                    capturedImageView.setLayoutParams(capturedImageViewLayoutParams);

                    myCaptureDialog.setView(capturedImageView);
                    myCaptureDialog.show();
                }

            }
        });
    }
}