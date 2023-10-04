package com.example.demo.models;

import java.util.List;

public class Community {

    private int id;
    private String name;
    private int creator_id;
    private List<User> members;
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

    public int getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(int creator_id) {
        this.creator_id = creator_id;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public List<CommunityPost> getPosts() {
        return posts;
    }

    public void setPosts(List<CommunityPost> posts) {
        this.posts = posts;
    }
}
