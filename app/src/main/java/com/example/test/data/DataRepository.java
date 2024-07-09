package com.example.test.data;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.test.R;
import com.example.test.ui_models.LabeledImage;
import com.example.test.ui_models.PromoDiscountModel;
import com.example.test.ui_models.ReceiverUserModel;
import com.example.test.ui_models.ReceiptModel;
import com.example.test.ui_models.UserDetailModel;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Single;

public class DataRepository {
    private static final String TAG = DataRepository.class.getSimpleName();
    private static DataRepository mInstance;

    public static synchronized DataRepository getInstance() {
        if(mInstance == null) {
            mInstance = new DataRepository();
        }
        return mInstance;
    }

    //private constructor
    private DataRepository(){}

    public LiveData<ArrayList<LabeledImage>> getPaymentListDetails() {
        //can call from server on background thread
        ArrayList<LabeledImage> list = new ArrayList<>();
        list.add(new LabeledImage("Internet", R.drawable.ic_wifi));
        list.add(new LabeledImage("Electricity", R.drawable.ic_electricity));
        list.add(new LabeledImage("Voucher", R.drawable.ic_voucher));
        list.add(new LabeledImage("Assurance", R.drawable.ic_assurance));
        list.add(new LabeledImage("Credit", R.drawable.ic_credit));
        list.add(new LabeledImage("Bill", R.drawable.ic_bill));
        list.add(new LabeledImage("Merchant", R.drawable.ic_merchant));
        list.add(new LabeledImage("More", R.drawable.ic_more));
        return new MutableLiveData<>(list);
    }

    public LiveData<ArrayList<PromoDiscountModel>> getPromoDiscountBannerList() {
        //can call from server on background thread
        ArrayList<PromoDiscountModel> list = new ArrayList<>();
        list.add(new PromoDiscountModel(1,"InviteFriends", R.drawable.promo_1));
        list.add(new PromoDiscountModel(2, "UberRefferal", R.drawable.promo_2));
        list.add(new PromoDiscountModel(3, "OlaRefferal", R.drawable.promo_1));
        list.add(new PromoDiscountModel(4, "M2P Discount", R.drawable.promo_2));
        return new MutableLiveData<>(list);
    }

    public Single<UserDetailModel> getLoggedInUserDetail(int userId) {
        //ToDo: implent api call to fetch loggedin user detail and save to DB then fetch from db by live data, using dummy fo UI
        return Single.fromCallable(() -> {
            UserDetailModel userDetailModel = new UserDetailModel(userId, "Samuel", R.drawable.user_pic, 11547.54, '$');
            return userDetailModel;
        });
    }

    public LiveData<ReceiverUserModel> ReceipentDetail(int userId) {
        //ToDo: implent api call to fetch loggedin user detail and save to DB then fetch from db by live data, using dummy fo UI
        ReceiverUserModel receiverUserModel = new ReceiverUserModel(userId, "Rakesh Kumar Shaw", R.drawable.pic_receipent, "0070 8822 1102 2255", "Bank");
        return new MutableLiveData<>(receiverUserModel);
    }

    public Single<ReceiptModel> makeTransaction(ReceiverUserModel receiverUserModel, Context context, String amount) {
        return Single.fromCallable(() -> {
            /*for(int i = 0; i<1000000; i++) {
                // ToDo:this is just for a dummy waiting for api call for making transaction
            }*/
            Log.d(TAG, "here reached");
            return new ReceiptModel.Builder()
                    .setTransId(101)
                    .setTransTitle(context.getString(R.string.transfer_success))
                    .setSuccess(true)
                    .setTransDesc(context.getString(R.string.transfer_success_desc))
                    .setDateTime("1 Jan 2023, 10:30PM")
                    .setTransRef("11288889058722")
                    .setReceiptModel(receiverUserModel)
                    .setTransAmt(amount)
                    .build();
        });
    }
}
