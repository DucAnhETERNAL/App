package com.example.readinglmao.model;

public class ChapterEditDTO {
    private String name;
    private boolean status;
    private String content;

    public ChapterEditDTO(String name, boolean status, String content) {
        this.name = name;
        this.status = status;
        this.content = content;
    }

    // Getters & Setters
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
