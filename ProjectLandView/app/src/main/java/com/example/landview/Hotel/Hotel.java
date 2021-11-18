package com.example.landview.Hotel;

public class Hotel {
    private int mBackground,mIcon,mRate;
    private String mName,mPrice,numberRate,mIntro,mAddress;

    public Hotel() {
    }

    public Hotel(int mBackground, int mIcon, int mRate, String mName, String mPrice, String numberRate, String mIntro, String mAddress) {
        this.mBackground = mBackground;
        this.mIcon = mIcon;
        this.mRate = mRate;
        this.mName = mName;
        this.mPrice = mPrice;
        this.numberRate = numberRate;
        this.mIntro = mIntro;
        this.mAddress = mAddress;
    }

    public int getmBackground() {
        return mBackground;
    }

    public void setmBackground(int mBackground) {
        this.mBackground = mBackground;
    }

    public int getmIcon() {
        return mIcon;
    }

    public void setmIcon(int mIcon) {
        this.mIcon = mIcon;
    }

    public int getmRate() {
        return mRate;
    }

    public void setmRate(int mRate) {
        this.mRate = mRate;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getNumberRate() {
        return numberRate;
    }

    public void setNumberRate(String numberRate) {
        this.numberRate = numberRate;
    }

    public String getmIntro() {
        return mIntro;
    }

    public void setmIntro(String mIntro) {
        this.mIntro = mIntro;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }
}
