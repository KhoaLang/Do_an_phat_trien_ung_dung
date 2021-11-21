package com.example.landview;

public class ItemRes {
    private int background;
    private int icontym;
    private String nameRes;
    private int imgrate;
    private String price;

    public ItemRes(int background, int icontym, String nameRes, int imgrate, String price) {
        this.background = background;
        this.icontym = icontym;
        this.nameRes = nameRes;
        this.imgrate = imgrate;
        this.price = price;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int getIcontym() {
        return icontym;
    }

    public void setIcontym(int icontym) {
        this.icontym = icontym;
    }

    public String getNameRes() {
        return nameRes;
    }

    public void setNameRes(String nameRes) {
        this.nameRes = nameRes;
    }

    public int getImgrate() {
        return imgrate;
    }

    public void setImgrate(int imgrate) {
        this.imgrate = imgrate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
