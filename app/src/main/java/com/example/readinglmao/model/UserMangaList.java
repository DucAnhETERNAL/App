package com.example.readinglmao.model;

import java.util.Date;

public class UserMangaList {
    private int id;
    private int userId;
    private int mangaId;
    private Date addedAt;
    private boolean isFavorite; // true: yêu thích, false: bình thường

    public UserMangaList() {
    }

    public UserMangaList(int id, int userId, int mangaId, Date addedAt, boolean isFavorite) {
        this.id = id;
        this.userId = userId;
        this.mangaId = mangaId;
        this.addedAt = addedAt;
        this.isFavorite = isFavorite;
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

    public Date getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Date addedAt) {
        this.addedAt = addedAt;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public String toString() {
        return "UserMangaList{" +
                "id=" + id +
                ", userId=" + userId +
                ", mangaId=" + mangaId +
                ", addedAt=" + addedAt +
                ", isFavorite=" + isFavorite +
                '}';
    }
}
