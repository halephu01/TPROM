package com.example.tprom.properties;

public class Member {
    private String name;
    private String role;
    private int avatar;

    public Member() {
        // Empty constructor required for Firebase
    }
    public Member(String name, String role, int avatar) {
        this.name = name;
        this.role = role;
        this.avatar =avatar;
    }

    public Member(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
