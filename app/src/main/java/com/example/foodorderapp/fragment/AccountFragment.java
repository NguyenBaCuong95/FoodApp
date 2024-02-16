package com.example.foodorderapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodorderapp.activity.ChangePasswordActivity;
import com.example.foodorderapp.activity.OrderHistoryActivity;
import com.example.foodorderapp.activity.SignInActivity;
import com.example.foodorderapp.constant.GlobalFunction;
import com.example.foodorderapp.databinding.FragmentAccountBinding;
import com.example.foodorderapp.preference.DataStoreManager;
import com.google.firebase.auth.FirebaseAuth;


public class AccountFragment extends Fragment {
    private FragmentAccountBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        setData();
        setListener();

        return binding.getRoot();
    }

    private void setData() {
        binding.txtEmail.setText(DataStoreManager.getUser().getEmail());
    }

    private void setListener() {
        binding.txtDangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangXuat();
            }
        });
        binding.txtLichsudonhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lichSuDonHang();
            }
        });
        binding.txtDoimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doiMatKhau();
            }
        });
    }

    private void doiMatKhau() {
        GlobalFunction.startActivity(getActivity(), ChangePasswordActivity.class);
    }

    private void lichSuDonHang() {
        GlobalFunction.startActivity(getActivity(), OrderHistoryActivity.class);
    }

    private void dangXuat() {
        FirebaseAuth.getInstance().signOut();
        DataStoreManager.setUser(null);
        GlobalFunction.startActivity(getActivity(), SignInActivity.class);
        getActivity().finishAffinity();
    }


}
