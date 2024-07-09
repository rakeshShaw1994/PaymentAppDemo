package com.example.test.ui_models;


/*this object class is use to hanlde the UI data of all buttons having image and name*/
public class LabeledImage {
    private int imageRes;
    private String title;

    public LabeledImage(String title, int imageRes) {
        this.imageRes = imageRes;
        this.title = title;
    }

    public int getImageRes() {
        return imageRes;
    }

    public String getTitle() {
        return title;
    }
}
