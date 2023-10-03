package com.example.demo.models;

public class VideoRating {

    private boolean rating;
    private String ratingUser;
    private int videoId;

    public boolean isRating() {
        return rating;
    }

    public void setRating(boolean rating) {
        this.rating = rating;
    }

    public String getRatingUser() {
        return ratingUser;
    }

    public void setRatingUser(String ratingUser) {
        this.ratingUser = ratingUser;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }
}
