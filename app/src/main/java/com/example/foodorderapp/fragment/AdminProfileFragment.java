package com.example.foodorderapp.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodorderapp.activity.AdminReportActivity;
import com.example.foodorderapp.activity.ChangePasswordActivity;
import com.example.foodorderapp.activity.SignInActivity;
import com.example.foodorderapp.constant.GlobalFunction;
import com.example.foodorderapp.databinding.ProfilefragmentAdminBinding;
import com.example.foodorderapp.preference.DataStoreManager;
import com.google.firebase.auth.FirebaseAuth;

public class AdminProfileFragment extends Fragment {
    private ProfilefragmentAdminBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = ProfilefragmentAdminBinding.inflate(inflater, container, false);
        binding.tvEmail.setText(DataStoreManager.getUser().getEmail());

        binding.layoutReport.setOnClickListener(v -> onClickReport());
        binding.layoutSignOut.setOnClickListener(v -> onClickSignOut());
        binding.layoutChangePassword.setOnClickListener(v -> onClickChangePassword());
        return binding.getRoot();
    }
    private void onClickReport() {
        GlobalFunction.startActivity(getActivity(), AdminReportActivity.class);
    }

    private void onClickChangePassword() {
        GlobalFunction.startActivity(getActivity(), ChangePasswordActivity.class);
    }

    private void onClickSignOut() {
        if (getActivity() == null) {
            return;
        }
        FirebaseAuth.getInstance().signOut();
        DataStoreManager.setUser(null);
        GlobalFunction.startActivity(getActivity(), SignInActivity.class);
        getActivity().finishAffinity();
    }
}
