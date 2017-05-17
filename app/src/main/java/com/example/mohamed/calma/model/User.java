package com.example.mohamed.calma.model;

/**
 * Created by mohamed on 30/04/2017.
 */

public class User {
    private String userName;
    private String userPhone;
    private String userImageUrl;
    private String type;
    private String pass;
    private String country;
    private String date;

    public User() {
    }

    public User(String userName, String userPhone, String userImageUrl, String type, String pass, String country, String date) {
        this.userName = userName;
        this.userPhone = userPhone;
        this.userImageUrl = userImageUrl;
        this.type = type;
        this.pass = pass;
        this.country = country;
        this.date = date;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
