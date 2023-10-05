package com.example.demo.models;

import java.util.List;

public class Photo {

    private int id;
    private String name;
    private String originalFileName;
    private long size;
    private String contentType;
    private byte[] bytes;
    private String creationDate;
    private int albumId;
    private List<PhotoTag> tags;
    private List<PhotoRating> ratings;
    private List<PhotoComment> comments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public List<PhotoTag> getTags() {
        return tags;
    }

    public void setTags(List<PhotoTag> tags) {
        this.tags = tags;
    }

    public List<PhotoRating> getRatings() {
        return ratings;
    }

    public void setRatings(List<PhotoRating> ratings) {
        this.ratings = ratings;
    }

    public List<PhotoComment> getComments() {
        return comments;
    }

    public void setComments(List<PhotoComment> comments) {
        this.comments = comments;
    }
}
