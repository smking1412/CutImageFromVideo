package com.e.videotoimage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class SelectFolderActivity extends AppCompatActivity {
    private ArrayList<Folders> mFolderLists;
    private RecyclerView mRecyclerViewFolder;
    private SelectFolderAdapter mSelectFolderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_video);
        initView();
        mFolderLists = new ArrayList<>();
    }

    private void initView() {
        mRecyclerViewFolder = findViewById(R.id.recycler_foler);
    }
}