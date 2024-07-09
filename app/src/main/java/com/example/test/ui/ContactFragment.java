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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.test.R;
import com.example.test.Utility;
import com.example.test.adapters.ContactListAdapter;
import com.example.test.databinding.FragmentContactListBinding;
import com.example.test.databinding.FragmentReceiptBinding;
import com.example.test.interfaces.DashboardInterface;
import com.example.test.ui_models.ContactDataModel;
import com.example.test.viewmodels.ContactsViewModel;
import com.example.test.viewmodels.DashboardViewModel;
import com.example.test.viewmodels.TransactionViewModel;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ContactFragment extends Fragment implements ContactListAdapter.ContactItemClick {
    private static final String TAG = ContactFragment.class.getSimpleName();

    private FragmentContactListBinding mBinding;
    private DashboardViewModel mViewModel;
    private ContactsViewModel mContactViewModel;
    private DashboardInterface mInterface;
    private ContactListAdapter mAdapter;

    private Toolbar toolbar;

    public static ContactFragment getInstance() {
        return new ContactFragment();
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
        mContactViewModel = new ViewModelProvider(this).get(ContactsViewModel.class);

        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact_list, container, false);
        mInterface = mViewModel.getInterface();
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
        getActivity().getWindow().setStatusBarColor(getContext().getColor(R.color.off_white_1));
    }

    private void initUI() {
        mBinding.rvList.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mBinding.rvList.setLayoutManager(mLayoutManager);
        getContacts();

        mBinding.toolbarLayout.ivBack.setOnClickListener(v -> mInterface.navigateHome());
    }

    public void getContacts() {
        Log.d(TAG, "started");
        mAdapter = new ContactListAdapter(mContactViewModel.getContactList(), this);
        mBinding.rvList.setAdapter(mAdapter);
        mBinding.pbLoading.setVisibility(View.GONE);
        if(mContactViewModel.getContactList() == null || mContactViewModel.getContactList().size() == 0) {
            mViewModel.getmCompositeDisposable().add(mContactViewModel.observePhoneContacts(getActivity())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(contact -> {
                        Log.d(TAG, "contact got name =  " + contact.getName());
                        if(isVisible())
                            mAdapter.add(contact);
                    }, error -> Log.e(TAG, "RxError =" + error)));
        }

    }

    @Override
    public void onItemClick(ContactDataModel model) {
        mInterface.onTransferClicked(model);
    }
}
