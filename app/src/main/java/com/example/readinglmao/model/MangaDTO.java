package com.example.readinglmao.model;

public class MangaDTO {
    private int id;
    private String title;
    private String genreName; // Dữ liệu từ API trả về có thể sử dụng 'genreName' thay vì 'genreId'
    private float averageRating; // Dữ liệu trả về có thể có đánh giá trung bình (averageRating)

    // Getters and setters
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

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(float averageRating) {
        this.averageRating = averageRating;
    }
}
