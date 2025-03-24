package com.example.readinglmao.model;

import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("id")
    private int id;

    @SerializedName("chapterId")
    private int chapterId;

    @SerializedName("userId")
    private int userId;

    @SerializedName("userName")  // Add the userName field
    private String userName;

    @SerializedName("content")
    private String content;

    // Default constructor
    public Comment() {}

    // Full constructor
    public Comment(int id, int chapterId, int userId, String userName, String content) {
        this.id = id;
        this.chapterId = chapterId;
        this.userId = userId;
        this.userName = userName;  // Initialize userName
        this.content = content;
    }

    // Getter and Setter methods
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getChapterId() { return chapterId; }

    public void setChapterId(int chapterId) { this.chapterId = chapterId; }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public String getUserName() { return userName; }  // Add getter for userName

    public void setUserName(String userName) { this.userName = userName; }  // Add setter for userName

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    // Override toString() for easy debugging
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", chapterId=" + chapterId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
