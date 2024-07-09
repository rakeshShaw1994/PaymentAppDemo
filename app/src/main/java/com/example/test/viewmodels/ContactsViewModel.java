package com.example.test.viewmodels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.test.Utility;
import com.example.test.ui_models.ContactDataModel;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Observable;

public class ContactsViewModel extends ViewModel {

    private static final String TAG = ContactsViewModel.class.getSimpleName();
    private ArrayList<ContactDataModel> mContactList = new ArrayList<>();

    public ArrayList<ContactDataModel> getContactList() {
        return mContactList;
    }

    public void setContactList(ArrayList<ContactDataModel> contactList) {
        this.mContactList = contactList;
    }

    public Observable<ContactDataModel> observePhoneContacts (Context context) {
        return Utility.getContacts(context);
    }
}
