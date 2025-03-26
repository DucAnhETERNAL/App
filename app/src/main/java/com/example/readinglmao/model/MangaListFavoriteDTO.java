package com.example.readinglmao.model;

import java.util.Date;

public class MangaListFavoriteDTO {

    private int id;
    private int userId;
    private int mangaId;
    private String addedAt;
    private boolean isFavorite;

    // Constructor
    public MangaListFavoriteDTO(int id, int userId, int mangaId, String addedAt, boolean isFavorite) {
        this.id = id;
        this.userId = userId;
        this.mangaId = mangaId;
        this.addedAt = addedAt;
        this.isFavorite = isFavorite;
    }

    // Getters and Setters
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

    public String getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(String addedAt) {
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
        return "MangaListFavoriteDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", mangaId=" + mangaId +
                ", addedAt=" + addedAt +
                ", isFavorite=" + isFavorite +
                '}';
    }
}