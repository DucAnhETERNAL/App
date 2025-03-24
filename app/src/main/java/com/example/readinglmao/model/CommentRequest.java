package com.example.readinglmao.model;

import java.util.Date;

public class CommentRequest {
    private int userId;
    private int chapterId;
    private String content;
    private String createdAt;

    public CommentRequest(int userId, int chapterId, String content, String createdAt) {
        this.userId = userId;
        this.chapterId = chapterId;
        this.content = content;
        this.createdAt = createdAt;
    }

    // Getter và Setter cho các thuộc tính
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}


