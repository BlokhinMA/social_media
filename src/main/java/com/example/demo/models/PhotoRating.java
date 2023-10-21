package com.example.demo.models;

public class PhotoRating {

    private boolean rating;
    private String ratingUserLogin;
    private int photoId;

    public boolean isRating() {
        return rating;
    }

    public void setRating(boolean rating) {
        this.rating = rating;
    }

    public String getRatingUserLogin() {
        return ratingUserLogin;
    }

    public void setRatingUserLogin(String ratingUserLogin) {
        this.ratingUserLogin = ratingUserLogin;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
}
