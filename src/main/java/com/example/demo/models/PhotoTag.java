package com.example.demo.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PhotoTag {

    private int id;
    @Size(min = 1, max = 255, message = "Количество символов должно быть больше 0 и меньше 256")
    @Pattern(regexp="\\S+", message= "Поле не должно содержать пробелы")
    private String tag;
    private int photoId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
}
