package com.example.tprom.properties;

public class User {
    private int userId;
    private String username;
    private String email;
    private String password;
    private int avatarUser;

    public User() {
    }

    public User(String username, String email, String password, int avatarUser) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatarUser=avatarUser;
    }

    public User(int userId, String username, String email, String password, int avatarUser) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatarUser=avatarUser;
    }

    public void setUserId(int userId){
        this.userId=userId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAvatarUser(int avatarUser) {
        this.avatarUser = avatarUser;
    }

    public int getAvatarUser() {
        return avatarUser;
    }
}
