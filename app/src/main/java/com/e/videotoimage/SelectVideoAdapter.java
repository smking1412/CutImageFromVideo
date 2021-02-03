package com.e.videotoimage;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.stream.MediaStoreImageThumbLoader;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SelectVideoAdapter extends RecyclerView.Adapter<SelectVideoAdapter.ViewHolder> {
    private ArrayList<VideoFiles> videoList;
    private Context mContext;

    public SelectVideoAdapter(ArrayList<VideoFiles> videoList, Context mContext) {
        this.videoList = videoList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.fileName.setText(videoList.get(position).getFileName());
        Uri uri = Uri.fromFile(new File(videoList.get(position).getPath()));
        Glide.with(mContext)
                .load(uri)
                .centerCrop()
                .into(holder.thumbnail);
        holder.videoDuration.setText(convertTime(videoList.get(position).getDuration()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentPlayVideo = new Intent(mContext,PlayVideoActivity.class);
                intentPlayVideo.putExtra("videopath",videoList.get(position).getPath());
                intentPlayVideo.putExtra("videoname",videoList.get(position).getTittle());
                mContext.startActivity(intentPlayVideo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView thumbnail;
        ImageView menuMore;
        TextView fileName;
        TextView videoDuration;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.img_video_thumbnail);
            menuMore = itemView.findViewById(R.id.img_video_more);
            fileName = itemView.findViewById(R.id.tv_video_name);
            videoDuration = itemView.findViewById(R.id.tv_video_duration);
        }
    }

    public String convertTime(String milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        long time = Long.parseLong(milliseconds);
        // Convert total duration into time
        int hours = (int) (time / (1000 * 60 * 60));
        int minutes = (int) (time % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((time % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }
}
