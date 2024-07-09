package com.example.test.ui_models;

import android.content.Context;

import com.example.test.R;

public class ReceiverUserModel extends UserBaseModel {
    private String accountNumber;
    private String accountType = "Bank";    //as of now using only one type as Bank, can be UPI etc.
    private String transAmount = "";

    public ReceiverUserModel(int userId, String userName, int profilePic, String accountNumber, String accountType) {
        super(userId, userName, profilePic);
        this.accountNumber = accountNumber;
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getFormattedAccount(Context context) {
        return context.getString(R.string.type_with_account, getAccountType(), getAccountNumber());
    }

    public String getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(String transAmount) {
        this.transAmount = transAmount;
    }
}
