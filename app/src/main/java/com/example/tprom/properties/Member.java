package com.example.tprom.properties;

public class Member {
    private String name;
    private String role;

    public Member() {
        // Empty constructor required for Firebase
    }

    public Member(String name, String role) {
        this.name = name;
        this.role = role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.name = username;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
