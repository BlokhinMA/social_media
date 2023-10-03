package com.example.demo.models;

public class PhotoRating {

    private boolean rating;
    private String ratingUser;
    private int photoId;

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

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
}
