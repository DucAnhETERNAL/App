package com.example.readinglmao.model;

public class Manga {
    private int id;
    private String title;
    private String description;
    private String author;
    private String type; // "Novel" hoặc "Comic"
    private String imageUrls;
    private int genreId;
    private boolean status; // true: hiển thị, false: ẩn

    public Manga() {
    }

    public Manga(int id, String title, String description, String author, String type, String imageUrls, int genreId, boolean status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.type = type;
        this.imageUrls = imageUrls;
        this.genreId = genreId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String imageUrls) {
        this.imageUrls = imageUrls;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Manga{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", type='" + type + '\'' +
                ", imageUrls='" + imageUrls + '\'' +
                ", genreId=" + genreId +
                ", status=" + status +
                '}';
    }
}
