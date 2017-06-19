package com.example.mohamed.calma.model;

import java.util.List;

/**
 * Created by mohamed on 02/05/2017.
 */

public class Doctor {
       private  String name;
       private String  image;
       private List<String> specialty;
       private int priceBerHour;
       private int Rate;

    public List<String> getSpecialty() {
        return specialty;
    }

    public void setSpecialty(List<String> specialty) {
        this.specialty = specialty;
    }

    public int getPriceBerHour() {
        return priceBerHour;
    }

    public void setPriceBerHour(int priceBerHour) {
        this.priceBerHour = priceBerHour;
    }

    public int getRate() {
        return Rate;
    }

    public void setRate(int rate) {
        Rate = rate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
