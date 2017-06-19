package com.example.mohamed.calma.model;

/**
 * Created by mohamed on 14/06/2017.
 */

public class CommentModel {
    private String image;
    private String owner;
    private String comment;



    public String getOwner() {
        return owner;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
