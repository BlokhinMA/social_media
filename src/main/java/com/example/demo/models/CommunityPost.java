package com.example.demo.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommunityPost {

    private int id;
    @Size(min = 1, message = "Количество символов должно быть больше 0")
    @NotBlank(message = "Поле не должно состоять только из пробелов")
    private String postText;
    private LocalDateTime creationTimeStamp;
    private String authorLogin;
    private int communityId;

}
