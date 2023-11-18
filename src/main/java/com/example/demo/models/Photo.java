package com.example.demo.models;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Photo {

    private int id;
    private String name;
    private String originalFileName;
    private long size;
    private String contentType;
    private byte[] bytes;
    private LocalDateTime creationTimeStamp;
    private int albumId;
    private List<PhotoTag> tags;
    private PhotoRating userRating;
    private Double rating;
    private List<PhotoComment> comments;
    private Album album;

}
