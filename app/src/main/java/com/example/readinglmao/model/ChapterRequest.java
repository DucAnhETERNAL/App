package com.example.readinglmao.model;

public class ChapterRequest {
    private int mangaId;
    private String name;
    private boolean status;
    private String content;

    public ChapterRequest(int mangaId, String name, boolean status, String content) {
        this.mangaId = mangaId;
        this.name = name;
        this.status = status;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getMangaId() {
        return mangaId;
    }

    public void setMangaId(int mangaId) {
        this.mangaId = mangaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

