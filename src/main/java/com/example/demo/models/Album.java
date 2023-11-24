package com.example.demo.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class Album {

    private int id;
    @Size(min = 1, max = 255, message = "Количество символов должно быть больше 0 и меньше 256")
    @NotBlank(message = "Поле не должно состоять только из пробелов")
    private String name;
    @NotNull
    @Pattern(regexp = "\\b(all|friends)\\b", message = "Значение должно быть равным \"all\" или \"friends\"")
    private String accessType;
    private String userLogin;
    private List<Photo> photos;

}
