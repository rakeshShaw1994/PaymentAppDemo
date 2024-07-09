package com.example.test.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.test.R;
import com.example.test.databinding.FragmentSendMoneyBinding;
import com.example.test.interfaces.DashboardInterface;
import com.example.test.interfaces.NumberPadListener;
import com.example.test.viewmodels.DashboardViewModel;
import com.example.test.viewmodels.TransactionViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SendMoneyFragment extends Fragment implements NumberPadListener {
    public static final String TAG = SendMoneyFragment.class.getSimpleName();
    private FragmentSendMoneyBinding mBinding;
    private DashboardViewModel mViewModel;
    private TransactionViewModel mTransactionViewModel;
    private DashboardInterface mInterface;
    private Toolbar toolbar;
    public static SendMoneyFragment getInstance() {
        return new SendMoneyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mViewModel = new ViewModelProvider(requireActivity()).get(DashboardViewModel.class);
        //fragment oriented view model
        mTransactionViewModel = new ViewModelProvider(requireActivity()).get(TransactionViewModel.class);

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_send_money, container, false);
        mInterface = mViewModel.getInterface();
        if(mTransactionViewModel.getReceipentId() == 0) {
            mInterface.navigateHome();
        }
        mBinding.loNumPad.setListener(this);
        Log.d(TAG, "init done");
        makeFullScreenActivity();
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = mBinding.getRoot().findViewById(R.id.toolbar);
        getActivity().setActionBar(toolbar);
        fetchReceipentDetails();
        initUI();
    }

    private void makeFullScreenActivity() {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getActivity().getWindow().setStatusBarColor(getContext().getColor(R.color.statusbar_dark));
    }

    private void fetchReceipentDetails() {
        mTransactionViewModel.fetchReceipentDetailFromDB()
                .observe(getViewLifecycleOwner(), receipentEntity -> {
                    mBinding.loReceipent.setReceipent(receipentEntity);
                    mTransactionViewModel.setmReceipentModel(receipentEntity);
                });
    }

    private void initUI() {
        mBinding.toolbarLayout.ivBack.setOnClickListener(v -> mInterface.navigateHome());
        observeNumberChange();
    }

    private void observeNumberChange() {
        mTransactionViewModel.getAmountVal()
                .observe(getViewLifecycleOwner(), amt -> {
                    mBinding.tvAmt.setText(amt);
                });
    }

    @Override
    public void onNumberInput(int input) {
        if(input == 0 && TextUtils.isEmpty(mTransactionViewModel.getAmountVal().getValue())) {
            Log.d(TAG, "cannot start with 0");
            return;
        }
        mTransactionViewModel.onNumberInput(input, '$');
    }

    @Override
    public void onEraseClicked() {
        mTransactionViewModel.onEraseClicked();
    }

    @Override
    public void onNextClicked() {
        String amt = mBinding.tvAmt.getText() == null ? "" : mBinding.tvAmt.getText().toString();
        if(TextUtils.isEmpty(amt)) {
            return;
        }
        mViewModel.getmCompositeDisposable().add(mTransactionViewModel.onNextClicked(amt, getActivity())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(receiptModel -> {
                    mTransactionViewModel.setReceiptModel(receiptModel);
                    mInterface.onTransactionDone();

                }, error -> {
                    Log.e(TAG, "rxError " + error);
                }));
    }

}
