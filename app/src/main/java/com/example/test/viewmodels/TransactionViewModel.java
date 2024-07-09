package com.example.test.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.test.Utility;
import com.example.test.data.DataRepository;
import com.example.test.ui_models.ReceiverUserModel;
import com.example.test.ui_models.ReceiptModel;

import io.reactivex.rxjava3.core.Single;

public class TransactionViewModel extends ViewModel {
    private static final String TAG = TransactionViewModel.class.getSimpleName();

    private int receipentId;
    private MutableLiveData<String> amountVal = new MutableLiveData<>("");
    private ReceiverUserModel mReceiverUserModel;
    private ReceiptModel mReceiptModel = null;

    public ReceiptModel getReceiptModel() {
        return mReceiptModel;
    }

    public void setReceiptModel(ReceiptModel receiptModel) {
        this.mReceiptModel = receiptModel;
    }

    public int getReceipentId() {
        return receipentId;
    }

    public void setReceipentId(int receipentId) {
        this.receipentId = receipentId;
    }

    public void setmReceipentModel(ReceiverUserModel mReceiverUserModel) {
        this.mReceiverUserModel = mReceiverUserModel;
    }

    public LiveData<ReceiverUserModel> fetchReceipentDetailFromDB() {
        if(mReceiverUserModel == null) {
            return DataRepository.getInstance().ReceipentDetail(receipentId);
        }
        return new MutableLiveData<>(mReceiverUserModel);
    }

    public MutableLiveData<String> getAmountVal() {
        return amountVal;
    }

    public void onNumberInput(int num, char currency) {
        String amt = Utility.addToLast(getAmountVal().getValue(), String.valueOf(num), currency);
        amountVal.postValue(amt);
    }

    public void onEraseClicked() {
        String amt = Utility.removeLastCharacter(getAmountVal().getValue());
        amountVal.postValue(amt);
    }

    public Single<ReceiptModel> onNextClicked(String amount, Context context) {
        Log.d(TAG, "onNextClicked with " + amount);
        return DataRepository.getInstance().makeTransaction(mReceiverUserModel, context, amount);
    }

    public void clearAll() {
        this.receipentId = 0;
        this.amountVal = new MutableLiveData<>("");
        this.mReceiverUserModel = null;
        this.mReceiptModel = null;
    }
}
