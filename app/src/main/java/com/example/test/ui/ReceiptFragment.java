package com.example.test.ui;

import android.os.Bundle;
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
import com.example.test.databinding.FragmentReceiptBinding;
import com.example.test.interfaces.DashboardInterface;
import com.example.test.viewmodels.DashboardViewModel;
import com.example.test.viewmodels.TransactionViewModel;

public class ReceiptFragment extends Fragment {
    private static final String TAG = ReceiptFragment.class.getSimpleName();

    private FragmentReceiptBinding mBinding;
    private DashboardViewModel mViewModel;
    private TransactionViewModel mTransactionViewModel;
    private DashboardInterface mInterface;

    private Toolbar toolbar;

    public static ReceiptFragment getInstance() {
        return new ReceiptFragment();
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
        mTransactionViewModel = new ViewModelProvider(requireActivity()).get(TransactionViewModel.class);

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_receipt, container, false);
        mInterface = mViewModel.getInterface();

        if(mTransactionViewModel.getReceiptModel() == null) {
            mInterface.navigateHome();
        }
        Log.d(TAG, "init done");
        makeFullScreenActivity();
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = mBinding.getRoot().findViewById(R.id.toolbar);
        getActivity().setActionBar(toolbar);

        initUI();
    }

    private void makeFullScreenActivity() {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getActivity().getWindow().setStatusBarColor(getContext().getColor(R.color.statusbar_dark));
    }

    private void initUI() {
        mBinding.toolbarLayout.ivBack.setOnClickListener(v -> mInterface.navigateHome());
        mBinding.loReceipt.setReceipt(mTransactionViewModel.getReceiptModel());
        mBinding.btnDone.setOnClickListener(v -> mInterface.navigateHome());
    }
}
