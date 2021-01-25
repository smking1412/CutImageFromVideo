package com.e.videotoimage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SelectFolderAdapter extends RecyclerView.Adapter<SelectFolderAdapter.ViewHolder> {
    private ArrayList<String> folderLists;
    private ArrayList<VideoFiles> videoFiles;
    private Context mContext;

    public SelectFolderAdapter(ArrayList<String> folderLists, ArrayList<VideoFiles> videoFiles, Context context) {
        this.folderLists = folderLists;
        this.videoFiles = videoFiles;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_folder,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.folderName.setText(folderLists.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,SelectVideoActivity.class);
                intent.putExtra("foldername",folderLists.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return folderLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView folderName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            folderName = itemView.findViewById(R.id.tv_folder_name);
        }
    }
}
