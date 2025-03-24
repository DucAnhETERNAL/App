package com.example.readinglmao.model;

public class AddMangaRequestDTO {
    private String title;
    private String description;
    private String author;
    private String type;
    private int genreId;
    private String status;

    // Constructor
    public AddMangaRequestDTO(String title, String description, String author, String type, int genreId, String status) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.type = type;
        this.genreId = genreId;
        this.status = status;
    }

    // Getters & Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public int getGenreId() { return genreId; }
    public void setGenreId(int genreId) { this.genreId = genreId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

