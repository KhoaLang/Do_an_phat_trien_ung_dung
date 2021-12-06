package com.example.landview.LandScape;

import java.io.Serializable;

public class ItemSuggest implements Serializable {
    private int background;
    private int icon;
    private String name,description;

    public ItemSuggest() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ItemSuggest(int background, int icon, String name, String description) {
        this.background = background;
        this.icon = icon;
        this.name = name;
        this.description = description;
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
