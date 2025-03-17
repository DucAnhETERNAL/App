package com.example.readinglmao.model;

public class ChapterText {
    private int id;
    private int chapterId;
    private String content;

    public ChapterText() {
    }

    public ChapterText(int id, int chapterId, String content) {
        this.id = id;
        this.chapterId = chapterId;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "ChapterText{" +
                "id=" + id +
                ", chapterId=" + chapterId +
                ", content='" + content + '\'' +
                '}';
    }
}
