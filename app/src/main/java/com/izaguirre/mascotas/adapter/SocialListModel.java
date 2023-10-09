package com.izaguirre.mascotas.adapter;

public class SocialListModel {
    private String text;
    private String title;
    private int resId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPicture() {
        return resId;
    }

    public void setPicture(int resId) {
        this.resId = resId;
    }
}
