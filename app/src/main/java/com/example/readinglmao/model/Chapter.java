package com.example.readinglmao.model;

import java.util.Date;

public class Chapter {
    private int id;
    private int mangaId;
    private String name;
    private boolean status; // true: công khai, false: ẩn
    private int viewCount;
    private Date createdAt;

    public Chapter() {
    }

    public Chapter(int id, int mangaId, String name, boolean status, int viewCount, Date createdAt) {
        this.id = id;
        this.mangaId = mangaId;
        this.name = name;
        this.status = status;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMangaId() {
        return mangaId;
    }

    public void setMangaId(int mangaId) {
        this.mangaId = mangaId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "id=" + id +
                ", mangaId=" + mangaId +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", viewCount=" + viewCount +
                ", createdAt=" + createdAt +
                '}';
    }
}
