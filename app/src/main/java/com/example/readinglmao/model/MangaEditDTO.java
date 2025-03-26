package com.example.readinglmao.model;

public class MangaEditDTO {
    private String title;
    private String description;
    private int genreId;
    private String status;

    public MangaEditDTO(String title, String description, int genreId, String status) {
        this.title = title;
        this.description = description;
        this.genreId = genreId;
        this.status = status;
    }

    // Getter và Setter
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

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}