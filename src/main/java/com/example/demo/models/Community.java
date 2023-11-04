package com.example.demo.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public class Community {

    private int id;
    @Size(min = 1, max = 255, message = "Количество символов должно быть больше 0 и меньше 256")
    @NotBlank(message = "Поле не должно состоять только из пробелов")
    private String name;
    private String creatorLogin;
    private List<CommunityMember> members;
    private List<CommunityPost> posts;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatorLogin() {
        return creatorLogin;
    }

    public void setCreatorLogin(String creatorLogin) {
        this.creatorLogin = creatorLogin;
    }

    public List<CommunityMember> getMembers() {
        return members;
    }

    public void setMembers(List<CommunityMember> members) {
        this.members = members;
    }

    public List<CommunityPost> getPosts() {
        return posts;
    }

    public void setPosts(List<CommunityPost> posts) {
        this.posts = posts;
    }
}
