package com.example.test.ui_models;

public class ContactDataModel extends ReceiverUserModel {
    public ContactDataModel(int userId, String userName, int profilePic, String accountNumber, String accountType) {
        super(userId, userName, profilePic, accountNumber, accountType);
    }

    public ReceiverUserModel toReceiverUserModel() {
        return this;
    }
}
