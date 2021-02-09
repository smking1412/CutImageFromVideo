package com.e.videotoimage;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private ImageView captureImage;
    private TextView quickCapture;
    private TextView timeCapture;
    private TextView timeSet;
    private TextView finishCapture;
    private LinearLayout linearSetTime;

    private MediaMetadataRetriever mediaMetadataRetriever;
    private boolean defaultMode = true;
    private float duration = 0;
    private float timeChoose = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        initView();
        prepare();
        evenClick();
    }

    private void prepare() {
        VideoFiles videoFiles = (VideoFiles) getIntent().getSerializableExtra("video");
//        String videoName = getIntent().getStringExtra("videoname");
        String videoName = videoFiles.getFileName();
        nameVideo.setText(videoName);
        String localPath = videoFiles.getPath();
//        String localPath = getIntent().getStringExtra("videopath");
        Uri uri = Uri.parse(localPath);
        mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(localPath);
        videoView.setVideoURI(uri);
        videoView.start();
        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                defaultMode = true;
                finish();
            }
        });

        duration = Float.parseFloat(videoFiles.getDuration()) / 1000;
        float text = duration / 10;
        String setTime = String.valueOf(Math.round(text * 10.0) / 10.0);
        SpannableString underlineText = new SpannableString(setTime);
        underlineText.setSpan(new UnderlineSpan(), 0, setTime.length(), 0);
        timeSet.setText(underlineText);

    }

    private void evenClick() {
        //quick capture
        quickCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultMode = true;
                updateUI();
            }
        });

        timeCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                defaultMode = false;
                updateUI();
                timeSet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PlayVideoActivity.this);
                        View dialogView = getLayoutInflater().inflate(R.layout.dialog_settime_capture, null);
                        EditText edtTime = dialogView.findViewById(R.id.edt_settime);
                        builder.setView(dialogView);
                        builder.setTitle("Enter Time (In Seconds)!");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (TextUtils.isEmpty(edtTime.getText().toString())) {
                                    Toast.makeText(PlayVideoActivity.this, "Please enter value first!", Toast.LENGTH_SHORT).show();
                                    return;
                                } else {
                                    timeChoose = Float.parseFloat(edtTime.getText().toString());
                                }
                                if (timeChoose > duration) {
                                    Toast.makeText(PlayVideoActivity.this, "Vui lòng chọn thời gian chụp <= thời lượng của video!", Toast.LENGTH_SHORT).show();
                                } else {
                                    timeSet.setText(String.valueOf(timeChoose));
                                }
                            }
                        });

                        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                timeSet.setText("2.0");
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        Button neutralButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
                        positiveButton.setTextColor(Color.parseColor("#FF0000"));
                        neutralButton.setTextColor(Color.parseColor("#FF0000"));
                        positiveButton.setTextSize(20);
                        neutralButton.setTextSize(20);
                    }
                });
            }
        });

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
                    videoView.pause();
                }
            }
        });


    }

    private void updateUI() {
        if (defaultMode == true) {
            quickCapture.setBackgroundResource(R.drawable.bg_btn_selected);
            timeCapture.setBackgroundResource(R.drawable.bg_btn_unselect);
            linearSetTime.setVisibility(View.GONE);
        } else {
            quickCapture.setBackgroundResource(R.drawable.bg_btn_unselect);
            timeCapture.setBackgroundResource(R.drawable.bg_btn_selected);
            linearSetTime.setVisibility(View.VISIBLE);
        }
    }

    private void initView() {
        videoView = findViewById(R.id.videoView);
        toolbar = findViewById(R.id.toolbarPlay);
        nameVideo = findViewById(R.id.tv_playvd_name);
        captureImage = findViewById(R.id.btn_capture_video);
        quickCapture = findViewById(R.id.btn_quick_capture);
        timeCapture = findViewById(R.id.btn_time_capture);
        timeSet = findViewById(R.id.tv_time_setting);
        finishCapture = findViewById(R.id.btn_finish_capture);
        linearSetTime = findViewById(R.id.linear_settime);
    }
}