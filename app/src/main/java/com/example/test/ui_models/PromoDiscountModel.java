package com.example.test.ui_models;

public class PromoDiscountModel {
    private int id;
    private String name;
    private int imageBannerRes;     //ToDo:can be used bitmap or image url if fetched from server

    public PromoDiscountModel(int id, String name, int imageBannerRes) {
        this.id = id;
        this.name = name;
        this.imageBannerRes = imageBannerRes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getImageBannerRes() {
        return imageBannerRes;
    }
}
