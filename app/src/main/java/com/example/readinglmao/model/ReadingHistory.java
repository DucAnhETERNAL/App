package com.example.readinglmao.model;

import java.util.Date;

public class ReadingHistory {
    private int id;
    private int userId;
    private int mangaId;
    private int chapterId;
    private Date readDate;
    private String status; // "Unread", "Reading", "Finished"

    public ReadingHistory() {
    }

    public ReadingHistory(int id, int userId, int mangaId, int chapterId, Date readDate, String status) {
        this.id = id;
        this.userId = userId;
        this.mangaId = mangaId;
        this.chapterId = chapterId;
        this.readDate = readDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMangaId() {
        return mangaId;
    }

    public void setMangaId(int mangaId) {
        this.mangaId = mangaId;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public Date getReadDate() {
        return readDate;
    }

    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ReadingHistory{" +
                "id=" + id +
                ", userId=" + userId +
                ", mangaId=" + mangaId +
                ", chapterId=" + chapterId +
                ", readDate=" + readDate +
                ", status='" + status + '\'' +
                '}';
    }
}
