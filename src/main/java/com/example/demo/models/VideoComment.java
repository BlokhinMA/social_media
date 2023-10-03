package com.example.demo.models;

public class VideoComment {

    private int videoCommentId;
    private String comment;
    private String commentingUser;
    private int videoId;

    public int getVideoCommentId() {
        return videoCommentId;
    }

    public void setVideoCommentId(int videoCommentId) {
        this.videoCommentId = videoCommentId;
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

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }
}
