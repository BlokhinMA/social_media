package com.example.demo.models;

public class PhotoComment {

    private int photoCommentId;
    private String comment;
    private String commentingUser;
    private int photoId;

    public int getPhotoCommentId() {
        return photoCommentId;
    }

    public void setPhotoCommentId(int photoCommentId) {
        this.photoCommentId = photoCommentId;
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
