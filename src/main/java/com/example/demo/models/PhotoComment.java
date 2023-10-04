package com.example.demo.models;

public class PhotoComment {

    private int id;
    private String comment;
    private String commentingUser;
    private int photoId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentingUser() {
        return commentingUser;
    }

    public void setCommentingUser(String commentingUser) {
        this.commentingUser = commentingUser;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
}
