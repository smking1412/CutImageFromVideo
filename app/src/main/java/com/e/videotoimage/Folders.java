package com.e.videotoimage;

public class Folders {
    private String folderName;
    private int folderVideo;

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int getFolderVideo() {
        return folderVideo;
    }

    public void setFolderVideo(int folderVideo) {
        this.folderVideo = folderVideo;
    }

    public Folders(String folderName, int folderVideo) {
        this.folderName = folderName;
        this.folderVideo = folderVideo;
    }
}
