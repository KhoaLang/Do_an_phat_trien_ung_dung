package com.example.landview.Topreview;

import java.io.Serializable;

public class TopItem implements Serializable {
    private int background;
    private int icon;
    private String name,textDescription;

    public TopItem() {
    }

    public String getTextDescription() {
        return textDescription;
    }

    public void setTextDescription(String textDescription) {
        this.textDescription = textDescription;
    }

    public TopItem(int background, int icon, String name, String des) {
        this.background = background;
        this.icon = icon;
        this.name = name;
        this.textDescription = des;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
