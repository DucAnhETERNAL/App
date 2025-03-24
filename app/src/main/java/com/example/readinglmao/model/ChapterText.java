package com.example.readinglmao.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChapterText {

    @SerializedName("id")
    private int id;

    @SerializedName("chapterId")
    private int chapterId;

    @SerializedName("content")
    private String content;

    @SerializedName("comments")
    private List<Comment> comments;  // List of comments

    // Default constructor
    public ChapterText() {}

    // Full constructor
    public ChapterText(int id, int chapterId, String content, List<Comment> comments) {
        this.id = id;
        this.chapterId = chapterId;
        this.content = content;
        this.comments = comments;
    }

    // Getter and Setter methods
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public int getChapterId() { return chapterId; }

    public void setChapterId(int chapterId) { this.chapterId = chapterId; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public List<Comment> getComments() { return comments; }

    public void setComments(List<Comment> comments) { this.comments = comments; }

    // Override toString() for easy debugging
    @Override
    public String toString() {
        return "ChapterText{" +
                "id=" + id +
                ", chapterId=" + chapterId +
                ", content='" + content + '\'' +
                ", comments=" + comments +
                '}';
    }
}
