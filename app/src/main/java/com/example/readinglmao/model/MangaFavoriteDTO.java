package com.example.readinglmao.model;

public class MangaFavoriteDTO {

    private int userId;
    private int mangaId;
    private boolean isFavorite;

    // Constructor
    public MangaFavoriteDTO(int userId, int mangaId, boolean isFavorite) {
        this.userId = userId;
        this.mangaId = mangaId;
        this.isFavorite = isFavorite;
    }

    // Getters and Setters
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

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public String toString() {
        return "MangaFavoriteDTO{" +
                "userId=" + userId +
                ", mangaId=" + mangaId +
                ", isFavorite=" + isFavorite +
                '}';
    }
}
