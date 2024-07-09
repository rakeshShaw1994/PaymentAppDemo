package com.example.test.ui_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.test.R;

public class UserDetailModel extends UserBaseModel {

    private double accBalance;
    private char currencySymbol;

    public static MutableLiveData<UserDetailModel> dummyUser() {
        return new MutableLiveData<>(
                new UserDetailModel(0, "Loading..",
                        R.drawable.ic_launcher_foreground, 0.00, '?'));
    }

    public UserDetailModel(int userId, String name, int profilePhoto, double accBalance, char currencySymbol) {
        super(userId, name, profilePhoto);
        this.accBalance = accBalance;
        this.currencySymbol = currencySymbol;
    }

    public double getAccBalance() {
        return accBalance;
    }

    public void setAccBalance(double accBalance) {
        this.accBalance = accBalance;
    }

    public char getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(char currencySymbol) {
        this.currencySymbol = currencySymbol;
    }
}
