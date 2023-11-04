package com.example.demo.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class PhotoComment {

    private int id;
    @Size(min = 1, message = "Количество символов должно быть больше 0")
    @NotBlank(message = "Поле не должно состоять только из пробелов")
    private String comment;
    private LocalDateTime commentingTimeStamp;
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

    public LocalDateTime getCommentingTimeStamp() {
        return commentingTimeStamp;
    }

    public void setCommentingTimeStamp(LocalDateTime commentingTimeStamp) {
        this.commentingTimeStamp = commentingTimeStamp;
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
