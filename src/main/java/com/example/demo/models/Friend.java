package com.example.demo.models;

public class Friend {

    private String firstUserLogin;
    private String secondUserLogin;
    private boolean accepted;

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
