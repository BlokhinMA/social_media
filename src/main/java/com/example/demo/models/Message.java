package com.example.demo.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Message {

    private int id;
    private String fromUserLogin;
    private String toUserLogin;
    @Size(min = 1, message = "Количество символов должно быть больше 0")
    @NotBlank(message = "Поле не должно состоять только из пробелов")
    private String message;
    private LocalDateTime writingTimeStamp;

}
