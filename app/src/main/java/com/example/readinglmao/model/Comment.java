package com.example.readinglmao.model;

import java.util.Date;

public class Comment {
    private int id;
    private int chapterId;
    private int userId;
    private String content;
    private Date createdAt;

    // Constructor mặc định
    public Comment() {}

    // Constructor đầy đủ
    public Comment(int id, int chapterId, int userId, String content, Date createdAt) {
        this.id = id;
        this.chapterId = chapterId;
        this.userId = userId;
        this.content = content;
        this.createdAt = createdAt;
    }

    // Getter và Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getChapterId() { return chapterId; }
    public void setChapterId(int chapterId) { this.chapterId = chapterId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    // Phương thức toString()
    @Override
    public String toString() {
        return "Comment{id=" + id + ", chapterId=" + chapterId + ", userId=" + userId +
                ", content='" + content + "', createdAt='" + createdAt + "'}";
    }
}

