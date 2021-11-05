package com.example.landview.Topreview;

public class TopItem {
    private int background;
    private int icon;
    private String name;

    public TopItem() {
    }

    public TopItem(int background, int icon, String name) {
        this.background = background;
        this.icon = icon;
        this.name = name;
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
