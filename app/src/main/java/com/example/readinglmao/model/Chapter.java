package com.example.readinglmao.model;

import java.io.Serializable;

public class Chapter implements Serializable {
    private int id;
    private String name;
    private boolean status; // true: công khai, false: ẩn
    private int viewCount;

    // Constructors
    public Chapter() {
    }

    public Chapter(int id, String name, boolean status, int viewCount) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.viewCount = viewCount;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", viewCount=" + viewCount +
                '}';
    }
}
