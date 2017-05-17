package com.example.mohamed.calma.model;

/**
 * Created by mohamed on 4/24/17.
 */

public class UserPost {
    private String name;
    private String imageUrl;

    public UserPost(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


}
