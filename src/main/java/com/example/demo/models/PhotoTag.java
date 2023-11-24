package com.example.demo.models;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PhotoTag {

    private int id;
    @Size(min = 1, max = 255, message = "Количество символов должно быть больше 0 и меньше 256")
    @Pattern(regexp="\\S+", message= "Поле не должно содержать пробелы")
    private String tag;
    private int photoId;
    private Photo photo;

}
