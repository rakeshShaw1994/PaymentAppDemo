package com.example.test;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.text.TextUtils;

import com.example.test.ui_models.ContactDataModel;

import java.util.Random;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;

public class Utility {

    //it will always comes with prefix currency symbol
    public static String removeLastCharacter(String input) {
        if(input.length() <= 3) {
            return "";
        }
        return input.substring(0, input.length() - 1);
    }

    public static String addToLast(String input, String newInput, char currencySymbol) {
        if(TextUtils.isEmpty(input)) {
            return currencySymbol + " " + newInput;
        }
        return input + newInput;
    }

    /*public static void getContactList(Context context) {
        // create cursor and query the data
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        startManagingCursor(cursor);

        // data is a array of String type which is
        // used to store Number ,Names and id.
        String[] data = {ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone._ID};
        int[] to = {android.R.id.text1, android.R.id.text2};

        // creation of adapter using SimpleCursorAdapter class
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, data, to);

        // Calling setAdaptor() method to set created adapter
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }*/

    public static Observable<ContactDataModel> getContacts(Context context) {
        return Observable.create(new ObservableOnSubscribe<ContactDataModel>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<ContactDataModel> emitter) throws Throwable {
                String contactId = "";
                String displayName = "";
                Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                        if (hasPhoneNumber > 0) {
                            contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                            displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                            Cursor phoneCursor = context.getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                                    new String[]{contactId},
                                    null);
                            if (phoneCursor.moveToNext()) {
                                String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                emitter.onNext(new ContactDataModel(1, displayName, getRandomAvatar(), "0x87" + String.valueOf(phoneNumber) + "ABC" + String.valueOf(phoneNumber), "Bank"));
                            }
                            phoneCursor.close();
                        }
                    }
                }
                cursor.close();
            }
        });
    }

    private static final int[] avatarList = new int[] {
            R.drawable.avatar1,
            R.drawable.avatar2,
            R.drawable.avatar3,
            R.drawable.avatar4,
            R.drawable.avatar5,
            R.drawable.avatar6,
            R.drawable.avatar7,
    };

    private static int getRandomAvatar() {
        return avatarList[new Random().nextInt(avatarList.length)];
    }
}
