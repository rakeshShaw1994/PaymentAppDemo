package com.example.test.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.test.R;
import com.example.test.adapters.PaymentListAdapter;
import com.example.test.adapters.PromoListAdapter;
import com.example.test.databinding.FragmentHomeBinding;
import com.example.test.interfaces.DashboardInterface;
import com.example.test.ui_models.LabeledImage;
import com.example.test.ui_models.PromoDiscountModel;
import com.example.test.ui_models.UserDetailModel;
import com.example.test.viewmodels.DashboardViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements PromoListAdapter.PromoItemClick{
    public static final String TAG = HomeFragment.class.getSimpleName();
    private FragmentHomeBinding mBinding;
    private DashboardViewModel mViewModel;
    private DashboardInterface mInterface;
    private Toolbar toolbar;



    public static HomeFragment getInstance() {
        return new HomeFragment();
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
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        mInterface = mViewModel.getInterface();
        Log.d(TAG, "init done");
        makeFullScreenActivity();
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = mBinding.getRoot().findViewById(R.id.toolbar);
        getActivity().setActionBar(toolbar);
        observeUserEntity();
        initUI();
    }

    private void makeFullScreenActivity() {
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        getActivity().getWindow().setStatusBarColor(getContext().getColor(R.color.statusbar_dark));
    }

    private void observeUserEntity() {
        mViewModel.observeUserEntity()
                .observe(getViewLifecycleOwner(), userEntity -> {
                    setToolbarData(userEntity);
                    mBinding.setUserEntity(userEntity);
                });
    }

    private void initUI() {
        observePaymentList();
        observePromoDiscountList();
        setClickEvents();
    }

    private void setClickEvents () {
        mBinding.loActionPanel.actionTransfer.getRoot().setOnClickListener(v -> mInterface.onChooseReceipentClicked());
    }

    private void setToolbarData(UserDetailModel userDetailModel) {
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        ImageView image = toolbar.findViewById(R.id.iv_avatar);
        title.setText(getString(R.string.hello_user, userDetailModel.getName()));
        image.setImageResource(userDetailModel.getProfilePhoto());
    }

    private void observePaymentList() {
        mViewModel.observePaymentList()
                .observe(getViewLifecycleOwner(), paymentListUiData -> populatePaymentListView(paymentListUiData));
    }

    private void observePromoDiscountList() {
        mViewModel.observePromoDiscountBannerList()
                .observe(getViewLifecycleOwner(), promoDiscountModels -> populatePromoDiscountListView(promoDiscountModels));
    }

    private void populatePaymentListView(ArrayList<LabeledImage> paymentListUiData) {
        PaymentListAdapter adapter = new PaymentListAdapter(getActivity(), paymentListUiData);
        mBinding.gvPaymentList.setAdapter(adapter);
    }

    private void populatePromoDiscountListView(ArrayList<PromoDiscountModel> promoDiscountModels) {
        PromoListAdapter adapter = new PromoListAdapter(promoDiscountModels, this);
        mBinding.rvPromo.setAdapter(adapter);
        mBinding.rvPromo.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void onItemClicked(PromoDiscountModel model) {
        Log.d(TAG, "on Promo Item Clicked with id = " + model.getId() + " and name = " + model.getName());
    }
}