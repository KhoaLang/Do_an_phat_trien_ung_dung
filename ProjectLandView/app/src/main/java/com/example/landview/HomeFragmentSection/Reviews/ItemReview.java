package com.example.landview.HomeFragmentSection.Reviews;

import java.io.Serializable;

public class ItemReview implements Serializable {
    private String Img;
    private String name;
    private int rateImg;
    private String address;

    public ItemReview() {
    }

    public ItemReview(String img, String name, int rateImg, String address) {
        Img = img;
        this.name = name;
        this.rateImg = rateImg;
        this.address = address;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRateImg() {
        return rateImg;
    }

    public void setRateImg(int rateImg) {
        this.rateImg = rateImg;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
