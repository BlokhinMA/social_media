package com.example.demo.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class Message {

    private int id;
    private String fromUserLogin;
    private String toUserLogin;
    @Size(min = 1, message = "Количество символов должно быть больше 0")
    @NotBlank(message = "Поле не должно состоять только из пробелов")
    private String message;
    private LocalDateTime writingTimeStamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFromUserLogin() {
        return fromUserLogin;
    }

    public void setFromUserLogin(String fromUserLogin) {
        this.fromUserLogin = fromUserLogin;
    }

    public String getToUserLogin() {
        return toUserLogin;
    }

    public void setToUserLogin(String toUserLogin) {
        this.toUserLogin = toUserLogin;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getWritingTimeStamp() {
        return writingTimeStamp;
    }

    public void setWritingTimeStamp(LocalDateTime writingTimeStamp) {
        this.writingTimeStamp = writingTimeStamp;
    }

}
