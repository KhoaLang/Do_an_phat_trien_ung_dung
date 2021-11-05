package com.example.landview.HomeFragment.News;

public class NewItem {
    private int imageNews;
    private String textNews;
    public NewItem() {
    }

    public NewItem(int imageNews, String textNews) {
        this.imageNews = imageNews;
        this.textNews = textNews;
    }

    public int getImageNews() {
        return imageNews;
    }

    public void setImageNews(int imageNews) {
        this.imageNews = imageNews;
    }

    public String getTextNews() {
        return textNews;
    }

    public void setTextNews(String textNews) {
        this.textNews = textNews;
    }
}
