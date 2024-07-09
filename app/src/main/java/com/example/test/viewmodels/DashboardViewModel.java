package com.example.test.viewmodels;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.test.enums.DashboardTabs;
import com.example.test.ui.HomeFragment;
import com.example.test.data.DataRepository;
import com.example.test.interfaces.DashboardInterface;
import com.example.test.ui_models.LabeledImage;
import com.example.test.ui_models.PromoDiscountModel;
import com.example.test.ui_models.UserDetailModel;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public class DashboardViewModel extends ViewModel {
    public static final String TAG = DashboardViewModel.class.getSimpleName();

    private Fragment mVisibleFragment = null;
    private DashboardInterface mInterface = null;
    private MutableLiveData<UserDetailModel> userEntity = UserDetailModel.dummyUser();

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private PublishSubject<DashboardTabs> tabChangePublisher = PublishSubject.create();
    private DashboardTabs selectedTab = DashboardTabs.HOME ;    //default

    public Fragment getVisibleFragment() {
        if(mVisibleFragment == null) {
            setVisibleFragment(HomeFragment.getInstance());
        }
        return mVisibleFragment;
    }

    public void setVisibleFragment(Fragment visibleFragment) {
        this.mVisibleFragment = visibleFragment;
    }

    public DashboardInterface getInterface() {
        return mInterface;
    }

    public void setInterface(DashboardInterface dashboardInterface) {
        this.mInterface = dashboardInterface;
    }

    public PublishSubject<DashboardTabs> getTabChangePublisher() {
        return tabChangePublisher;
    }

    public DashboardTabs getSelectedTab() {
        return selectedTab;
    }

    public void setSelectedTab(DashboardTabs selectedTab) {
        this.selectedTab = selectedTab;
    }

    public LiveData<ArrayList<LabeledImage>> observePaymentList() {
        return DataRepository.getInstance().getPaymentListDetails();
    }

    public LiveData<ArrayList<PromoDiscountModel>> observePromoDiscountBannerList() {
        return DataRepository.getInstance().getPromoDiscountBannerList();
    }

    public Single<UserDetailModel> fetchUserDetailFromDB(int userId) {
        return DataRepository.getInstance().getLoggedInUserDetail(userId);
    }

    public LiveData<UserDetailModel> observeUserEntity() {
        Log.d(TAG, "user details feched to view model");
        return userEntity;
    }

    public void setUserEntity(UserDetailModel userEntity) {
        Log.d(TAG, "user details saved to view model");
        this.userEntity.postValue(userEntity);
    }

    public CompositeDisposable getmCompositeDisposable() {
        if(mCompositeDisposable == null){
            mCompositeDisposable = new CompositeDisposable();
        }
        return mCompositeDisposable;
    }

    public void clearDisposables() {
        if(mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
        mCompositeDisposable = null;
    }

    public void changeTab(DashboardTabs newTab) {
        if(tabChangePublisher == null) {
            tabChangePublisher = PublishSubject.create();
        }
        tabChangePublisher.onNext(newTab);
    }
}
