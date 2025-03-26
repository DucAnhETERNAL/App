package com.example.readinglmao.model;

import java.io.Serializable;

public class ChapterListDTO implements Serializable {
    private int id;
    private String name;
    private int viewCount;
    private boolean status; // true: chưa đọc, false: đã đọc

    // Constructor
    public ChapterListDTO(int id, String name, int viewCount, boolean status) {
        this.id = id;
        this.name = name;
        this.viewCount = viewCount;
        this.status = status;
    }

    // Getters và Setters
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

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
