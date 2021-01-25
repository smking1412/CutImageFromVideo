package com.e.videotoimage;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SelectVideoActivity extends AppCompatActivity {
    private RecyclerView mRecyclerViewVideo;
    private SelectVideoAdapter mSelectVideoAdapter;
    private String folderName;
    private ArrayList<VideoFiles> videoFilesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_video);
        mRecyclerViewVideo = findViewById(R.id.recycler_video);
        folderName = getIntent().getStringExtra("foldername");
        if (folderName != null) {
            videoFilesList = getAllVideo(this, folderName);
        }

        if (videoFilesList != null && videoFilesList.size() > 0){
            checkPermission();
            mSelectVideoAdapter = new SelectVideoAdapter(videoFilesList,this);
            mRecyclerViewVideo.setAdapter(mSelectVideoAdapter);
            mRecyclerViewVideo.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SelectVideoActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 10);
        } else {
            Toast.makeText(this, "Đã được cấp quyền", Toast.LENGTH_SHORT).show();
        }
    }

    public ArrayList<VideoFiles> getAllVideo(Context context, String folderName) {
        ArrayList<VideoFiles> tempVideoFiles = new ArrayList<>();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.TITLE,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DATE_ADDED,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DURATION
        };

        String selection = MediaStore.Video.Media.DATA + " like?";
        String[] selectionArgs = new String[]{"%" + folderName + "%"};
        Cursor cursor = context.getContentResolver()
                .query(uri, projection, selection, selectionArgs, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(0);
                String path = cursor.getString(1);
                String title = cursor.getString(2);
                String size = cursor.getString(3);
                String dateAdded = cursor.getString(4);
                String fileName = cursor.getString(5);
                String duration = cursor.getString(6);
                VideoFiles videoFiles = new VideoFiles(id, path, title, size, dateAdded, fileName, duration);

                // /storage/sd_card/VideoDir/Abc/video.mp4
                int slashFirstIndex = path.lastIndexOf("/");
                String subString = path.substring(0, slashFirstIndex);
                // /storage/sd_card/VideoDir/Abc because last index includeed so slash(vì chỉ mục cuối cùng bao gồm dấu gạch chéo)
                //excluded
                int index = subString.lastIndexOf("/");
                String folerName = subString.substring(index + 1, slashFirstIndex);
                //sau khi thực hiện nó sẽ trả về folderName là Abc
                tempVideoFiles.add(videoFiles);
                Log.e("pmtan", "getAllVideo: " +videoFiles.getFileName());
            }
            cursor.close();
        }
        return tempVideoFiles;
    }

}