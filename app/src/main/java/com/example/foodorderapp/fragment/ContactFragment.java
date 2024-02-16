package com.example.foodorderapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.foodorderapp.R;
import com.example.foodorderapp.adapter.ContactAdapter;
import com.example.foodorderapp.constant.AboutUsConfig;
import com.example.foodorderapp.constant.GlobalFunction;
import com.example.foodorderapp.databinding.FragmentContactBinding;
import com.example.foodorderapp.model.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactFragment extends Fragment {
    private FragmentContactBinding mFragmentContactBinding;
    private ContactAdapter mContactAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentContactBinding = FragmentContactBinding.inflate(inflater, container, false);

        initUi();


        return mFragmentContactBinding.getRoot();
    }

    private void initUi() {
        mFragmentContactBinding.tvTitle.setText(AboutUsConfig.ABOUT_US_TITLE);
        mFragmentContactBinding.tvContent.setText(AboutUsConfig.ABOUT_US_CONTENT);


        mContactAdapter = new ContactAdapter(getActivity(), getListContact(), new ContactAdapter.ICallPhone() {
            @Override
            public void onClickCallPhone() {
                GlobalFunction.callPhoneNumber(getActivity());
            }
        });

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        mFragmentContactBinding.rcvData.setNestedScrollingEnabled(false);
        mFragmentContactBinding.rcvData.setFocusable(false);
        mFragmentContactBinding.rcvData.setLayoutManager(layoutManager);
        mFragmentContactBinding.rcvData.setAdapter(mContactAdapter);
    }


    public List<Contact> getListContact() {
        List<Contact> contactArrayList = new ArrayList<>();
        contactArrayList.add(new Contact(Contact.FACEBOOK, R.drawable.fb));
        contactArrayList.add(new Contact(Contact.HOTLINE, R.drawable.ic_phone));
        contactArrayList.add(new Contact(Contact.GMAIL, R.drawable.ic_gmail));


        return contactArrayList;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mContactAdapter.release();
    }


}
