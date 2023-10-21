package com.example.demo.models;

public class PhotoComment {

    private int id;
    private String comment;
    private String commentingUserLogin;
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

    public String getCommentingUserLogin() {
        return commentingUserLogin;
    }

    public void setCommentingUserLogin(String commentingUserLogin) {
        this.commentingUserLogin = commentingUserLogin;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
}
