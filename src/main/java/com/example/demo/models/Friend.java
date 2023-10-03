package com.example.demo.models;

public class Friend {

    private int firstUserId;
    private int secondUserId;
    private boolean accepted;

    public int getFirstUserId() {
        return firstUserId;
    }

    public void setFirstUserId(int firstUserId) {
        this.firstUserId = firstUserId;
    }

    public int getSecondUserId() {
        return secondUserId;
    }

    public void setSecondUserId(int secondUserId) {
        this.secondUserId = secondUserId;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
