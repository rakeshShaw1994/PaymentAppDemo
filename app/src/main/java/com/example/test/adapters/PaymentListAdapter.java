package com.example.test.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.example.test.R;
import com.example.test.databinding.ItemPaymentListBinding;
import com.example.test.ui_models.LabeledImage;

import java.util.ArrayList;

public class PaymentListAdapter extends ArrayAdapter<LabeledImage> {
    public PaymentListAdapter(@NonNull Context context, ArrayList<LabeledImage> paymentList) {
        super(context, 0, paymentList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ItemPaymentListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.item_payment_list, parent, false);

        LabeledImage uiButtonData = getItem(position);
        binding.setButton(uiButtonData);
        return binding.getRoot();
    }
}
