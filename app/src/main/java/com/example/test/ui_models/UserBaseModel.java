package com.example.test.ui_models;

public abstract class UserBaseModel {

    private int userId;
    private String name;
    private int profilePhoto;

    public UserBaseModel(int userId, String name, int profilePhoto) {
        this.userId = userId;
        this.name = name;
        this.profilePhoto = profilePhoto;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(int profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

}
