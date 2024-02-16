package com.example.foodorderapp.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.foodorderapp.R;
import com.example.foodorderapp.adapter.AdminViewPagerAdapter;
import com.example.foodorderapp.databinding.ActivityAdminMainBinding;

public class AdminMainActivity extends AppCompatActivity {
    private ActivityAdminMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       // binding.viewPagerAdmin.setUserInputEnabled(false);
        AdminViewPagerAdapter adapter = new AdminViewPagerAdapter(this);
        binding.viewPagerAdmin.setAdapter(adapter);
        binding.viewPagerAdmin.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        binding.bottomNavigation.getMenu().findItem(R.id.nav_home).setChecked(true);
                        break;

                    case 1:
                        binding.bottomNavigation.getMenu().findItem(R.id.nav_feedback).setChecked(true);
                        break;

                    case 2:
                        binding.bottomNavigation.getMenu().findItem(R.id.nav_order).setChecked(true);
                        break;

                    case 3:
                        binding.bottomNavigation.getMenu().findItem(R.id.nav_account).setChecked(true);
                        break;

                }
            }
        });
        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                binding.viewPagerAdmin.setCurrentItem(0);
            } else if (id == R.id.nav_feedback) {
                binding.viewPagerAdmin.setCurrentItem(1);
            } else if (id == R.id.nav_order) {
                binding.viewPagerAdmin.setCurrentItem(2);
            }  else if (id == R.id.nav_account) {
                binding.viewPagerAdmin.setCurrentItem(3);
            }
            return true;
        });

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        showConfirmExitApp();

    }
    private void showConfirmExitApp() {
        new MaterialDialog.Builder(this)
                .title(getString(R.string.app_name))
                .content("Bạn có muốn thoát ứng dụng không")
                .positiveText("Đồng ý")
                .onPositive((dialog, which) -> finishAffinity())
                .negativeText("Huỷ bỏ")
                .cancelable(false)
                .show();
    }

}