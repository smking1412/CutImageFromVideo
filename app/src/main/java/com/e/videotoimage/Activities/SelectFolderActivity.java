package com.e.videotoimage.Activities;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.e.videotoimage.Models.VideoFiles;
import com.e.videotoimage.R;
import com.e.videotoimage.Adapters.SelectFolderAdapter;
import com.e.videotoimage.Adapters.SelectVideoAdapter;

import java.util.ArrayList;

public class SelectFolderActivity extends AppCompatActivity {
    ArrayList<VideoFiles> videoFiles = new ArrayList<>();
    private ArrayList<String> mFolderLists = new ArrayList<>();
    private RecyclerView mRecyclerViewFolder;
    private SelectVideoAdapter mSelectVideoAdapter;
    private SelectFolderAdapter mSelectFolderAdapter;
    private static final int REQUEST_CODE_PERMISSION = 10;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_folder);
        initView();

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        videoFiles = getAllVideo(this);
        if (mFolderLists != null && mFolderLists.size() > 0 && videoFiles != null){
            mSelectFolderAdapter = new SelectFolderAdapter(mFolderLists,videoFiles,this);
            mRecyclerViewFolder.setAdapter(mSelectFolderAdapter);
            mRecyclerViewFolder.setLayoutManager(new GridLayoutManager(this,2));
        }
    }

    private void initView() {
        mRecyclerViewFolder = findViewById(R.id.recycler_foler);
        toolbar = findViewById(R.id.toolbarfolder);
    }

    public ArrayList<VideoFiles> getAllVideo(Context context) {
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
        Cursor cursor = context.getContentResolver()
                .query(uri, projection, null, null, null);
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
                Log.e("pmtan", fileName + "getAllVideo: " + title);

                // /storage/sd_card/VideoDir/Abc/video.mp4
                int slashFirstIndex = path.lastIndexOf("/");
                String subString = path.substring(0, slashFirstIndex);
                // /storage/sd_card/VideoDir/Abc because last index includeed so slash(vì chỉ mục cuối cùng bao gồm dấu gạch chéo)
                //excluded
                int index = subString.lastIndexOf("/");
                String folerName = subString.substring(index + 1, slashFirstIndex);
                //sau khi thực hiện nó sẽ trả về folderName là Abc
                if (!mFolderLists.contains(folerName)) {
                    mFolderLists.add(folerName);
                }
                tempVideoFiles.add(videoFiles);
            }
            cursor.close();
        }
        return tempVideoFiles;
    }

}