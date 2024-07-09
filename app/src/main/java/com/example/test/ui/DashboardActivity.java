package com.example.test.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.test.R;
import com.example.test.databinding.ActivityDashboardBinding;
import com.example.test.enums.DashboardTabs;
import com.example.test.interfaces.DashboardInterface;
import com.example.test.ui_models.ReceiverUserModel;
import com.example.test.viewmodels.DashboardViewModel;
import com.example.test.viewmodels.TransactionViewModel;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DashboardActivity extends AppCompatActivity implements DashboardInterface {

    public static final String TAG = DashboardActivity.class.getSimpleName();
    private ActivityDashboardBinding mBinding;
    private DashboardViewModel mViewModel;
    private TransactionViewModel mTransactionViewModel;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        mTransactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        mViewModel.setInterface(this);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        mBinding.loTabs.setViewmodel(mViewModel);
        fetchUserDetails();
        observeTabChange();
        initView();
    }

    private void fetchUserDetails() {
        //take user id from persistence or wherever stored after login to fetch details from db
        mViewModel.getmCompositeDisposable().add(mViewModel.fetchUserDetailFromDB(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(userEntity -> {
                    mViewModel.setUserEntity(userEntity);
                }));
    }

    private void observeTabChange() {
        mViewModel.getmCompositeDisposable().add(mViewModel.getTabChangePublisher()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onTabChange));
    }

    private void onTabChange(DashboardTabs tab) {
        Log.d(TAG, "onTabChange == "+ tab.toString());
        mViewModel.setSelectedTab(tab);
        mBinding.loTabs.setSelectedTab(tab);

        if(tab == DashboardTabs.MORE) {
            navigateCollapsingHome();
        } else if(tab == DashboardTabs.HOME) {
            navigateHome();
        }
        //ToDo : perform any fragment transaction if required
    }

    private void initView() {
        mBinding.loTabs.setSelectedTab(mViewModel.getSelectedTab());
        fragmentManager = getSupportFragmentManager();
        loadFragment();
    }

    private void loadFragment() {
        Fragment mFragment = mViewModel.getVisibleFragment();
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, mFragment).commit();
    }

    @Override
    public void navigateHome() {
        Fragment mFragment = HomeFragment.getInstance();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.entry_from_right, R.anim.exit_from_left, R.anim.entry_from_left, R.anim.exit_from_right)
                .replace(R.id.frameLayout, mFragment)
                .commit();
        mViewModel.setVisibleFragment(mFragment);
        mTransactionViewModel.clearAll();
    }

    @Override
    public void onTransferClicked(ReceiverUserModel receiverUserModel) {
        mTransactionViewModel.setReceipentId(receiverUserModel.getUserId());
        mTransactionViewModel.setmReceipentModel(receiverUserModel);
        Fragment mFragment = SendMoneyFragment.getInstance();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.entry_from_right, R.anim.exit_from_left, R.anim.entry_from_left, R.anim.exit_from_right)
                .replace(R.id.frameLayout, mFragment)
                .commit();
        mViewModel.setVisibleFragment(mFragment);
    }

    @Override
    public void onChooseReceipentClicked() {
        Fragment mFragment = ContactFragment.getInstance();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.entry_from_right, R.anim.exit_from_left, R.anim.entry_from_left, R.anim.exit_from_right)
                .replace(R.id.frameLayout, mFragment)
                .commit();
        mViewModel.setVisibleFragment(mFragment);
    }

    @Override
    public void onTransactionDone() {
        Fragment mFragment = ReceiptFragment.getInstance();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.entry_from_right, R.anim.exit_from_left, R.anim.entry_from_left, R.anim.exit_from_right)
                .replace(R.id.frameLayout, mFragment)
                .commit();
        mViewModel.setVisibleFragment(mFragment);
    }

    //to visualise collapsing toolbar
    @Override
    public void navigateCollapsingHome() {
        Fragment mFragment = CollapsingHomeFragment.getInstance();
        fragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.entry_from_right, R.anim.exit_from_left, R.anim.entry_from_left, R.anim.exit_from_right)
                .replace(R.id.frameLayout, mFragment)
                .commit();
        mViewModel.setVisibleFragment(mFragment);
        mTransactionViewModel.clearAll();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewModel.clearDisposables();
    }
}