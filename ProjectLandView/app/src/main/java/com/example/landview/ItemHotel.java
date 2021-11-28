package com.example.landview;

public class ItemHotel {
    private int background;
    private int icontym;
    private String namehotel;
    private int imgrate;
    private String price;

    public ItemHotel() {
    }

    public ItemHotel(int background, int icontym, String namehotel, int imgrate, String price) {
        this.background = background;
        this.icontym = icontym;
        this.namehotel = namehotel;
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

    public String getNamehotel() {
        return namehotel;
    }

    public void setNamehotel(String namehotel) {
        this.namehotel = namehotel;
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
