package com.example.mohamed.calma.model;

import android.net.Uri;

/**
 * Created by mohamed on 08/05/2017.
 */

public class TestDoctor {
    private Uri image;
    private String name;
    private boolean state;

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
