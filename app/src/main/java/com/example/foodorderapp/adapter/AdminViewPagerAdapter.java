package com.example.foodorderapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.foodorderapp.fragment.AdminCartFragment;
import com.example.foodorderapp.fragment.AdminFeedBackFragment;
import com.example.foodorderapp.fragment.AdminHomeFragment;
import com.example.foodorderapp.fragment.AdminProfileFragment;

public class AdminViewPagerAdapter extends FragmentStateAdapter {
    public AdminViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new AdminHomeFragment();
            case 1:
                return new AdminFeedBackFragment();
            case 2:
                return new AdminCartFragment();
            case 3:
                return new AdminProfileFragment();
            default:   return new AdminHomeFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
