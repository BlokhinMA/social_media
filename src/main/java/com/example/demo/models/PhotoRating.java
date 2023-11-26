package com.example.demo.models;

import lombok.Data;

@Data
public class PhotoRating {

    private int id;
    private boolean rating;
    private String ratingUserLogin;
    private int photoId;

}
