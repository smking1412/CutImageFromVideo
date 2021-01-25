package com.e.videotoimage;

public class VideoFiles {
    private String id;
    private String path;
    private String tittle;
    private String fileName;
    private String size;
    private String dateAdded;
    private String duration;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public VideoFiles(String id, String path, String tittle, String fileName, String size, String dateAdded, String duration) {
        this.id = id;
        this.path = path;
        this.tittle = tittle;
        this.fileName = fileName;
        this.size = size;
        this.dateAdded = dateAdded;
        this.duration = duration;
    }
}
