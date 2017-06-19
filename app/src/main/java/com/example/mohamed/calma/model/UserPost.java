package com.example.mohamed.calma.model;

import java.util.List;

/**
 * Created by mohamed on 4/24/17.
 */

public class UserPost {
    private String name;
    private String imageUrl;
    private String contant;
    private List<CommentModel> comments;
    private List<String> likes;
    private List<String> shares;
    public UserPost(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }
    public UserPost (){

    }

    public String getContant() {
        return contant;
    }

    public void setContant(String contant) {
        this.contant = contant;
    }

    public List<CommentModel> getComments() {
        return comments;
    }

    public void setComments(List<CommentModel> comments) {
        this.comments = comments;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public List<String> getShares() {
        return shares;
    }

    public void setShares(List<String> shares) {
        this.shares = shares;
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
