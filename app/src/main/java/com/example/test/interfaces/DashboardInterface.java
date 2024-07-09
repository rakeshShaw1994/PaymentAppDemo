package com.example.test.interfaces;

import com.example.test.ui_models.ReceiverUserModel;

public interface DashboardInterface {

    void navigateHome();
    void onTransferClicked(ReceiverUserModel receiverUserModel);
    void onChooseReceipentClicked();
    void onTransactionDone();

    void navigateCollapsingHome();

}
