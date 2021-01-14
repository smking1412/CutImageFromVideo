package com.e.videotoimage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SelectFolderAdapter extends RecyclerView.Adapter<SelectFolderAdapter.ViewHolder> {
    private ArrayList<Folders> folderLists;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_folder_select,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvFolderName.setText(folderLists.get(position).getFolderName());
        if (folderLists.get(position).getFolderVideo() > 0){
            holder.tvFolderVideo.setText(folderLists.get(position).getFolderVideo() + " videos");
        } else {
            holder.tvFolderVideo.setText( "0 video");
        }
    }

    @Override
    public int getItemCount() {
        return folderLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFolderName;
        private TextView tvFolderVideo;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFolderName = itemView.findViewById(R.id.tv_folder_name);
            tvFolderVideo = itemView.findViewById(R.id.tv_number_video);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
