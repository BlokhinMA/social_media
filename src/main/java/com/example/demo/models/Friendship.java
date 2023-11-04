package com.example.demo.models;

public class Friendship {

    private int id;
    private String firstUserLogin;
    private String secondUserLogin;
    private boolean accepted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstUserLogin() {
        return firstUserLogin;
    }

    public void setFirstUserLogin(String firstUserLogin) {
        this.firstUserLogin = firstUserLogin;
    }

    public String getSecondUserLogin() {
        return secondUserLogin;
    }

    public void setSecondUserLogin(String secondUserLogin) {
        this.secondUserLogin = secondUserLogin;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
