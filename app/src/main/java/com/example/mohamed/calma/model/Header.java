package com.example.mohamed.calma.model;

/**
 * Created by mohamed on 4/22/17.
 */

public class Header {
    private String ImageUrl;
    private String userName;
    private String followers;
    private String articles;
    private String videos;

    public String getImageUrl() {
        return ImageUrl;
    }

    public String getArticles() {
        return articles;
    }

    public void setArticles(String articles) {
        this.articles = articles;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }


    public String getVideos() {
        return videos;
    }

    public void setVideos(String videos) {
        this.videos = videos;
    }
}
