package com.example.readinglmao.model;

import java.util.List;

public class MangaListDTO {
    private int id;
    private String title;
    private String genreName;
    private float averageRating;
    private List<ChapterListDTO> chapters;
    private boolean isExpanded; // Trạng thái mở rộng danh sách chương

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenreName() {
        return genreName;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public List<ChapterListDTO> getChapters() {
        return chapters;
    }

    public void setChapters(List<ChapterListDTO> chapters) {
        this.chapters = chapters;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
