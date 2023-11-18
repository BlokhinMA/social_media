package com.example.demo.models;

import lombok.Data;

@Data
public class Friendship {

    private int id;
    private String firstUserLogin;
    private String secondUserLogin;
    private boolean accepted;

}
