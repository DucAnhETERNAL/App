package com.example.readinglmao.model;

import java.util.Date;

public class Rate {
    private int id;
    private int mangaId;
    private int userId;
    private int rating;
    private String comment;
    private Date createdAt;

    // Constructor mặc định
    public Rate() {}

    // Constructor đầy đủ
    public Rate(int id, int mangaId, int userId, int rating, String comment, Date createdAt) {
        this.id = id;
        this.mangaId = mangaId;
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    // Getter và Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMangaId() { return mangaId; }
    public void setMangaId(int mangaId) { this.mangaId = mangaId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    // Phương thức toString()
    @Override
    public String toString() {
        return "Rate{id=" + id + ", mangaId=" + mangaId + ", userId=" + userId +
                ", rating=" + rating + ", comment='" + comment + "', createdAt='" + createdAt + "'}";
    }
}

