package com.example.demo.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class Community {

    private int id;
    @Size(min = 1, max = 255, message = "Количество символов должно быть больше 0 и меньше 256")
    @NotBlank(message = "Поле не должно состоять только из пробелов")
    private String name;
    private String creatorLogin;
    private List<CommunityMember> members;
    private List<CommunityPost> posts;

}
