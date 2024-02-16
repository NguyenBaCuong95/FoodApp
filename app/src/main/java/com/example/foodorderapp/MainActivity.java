package com.example.foodorderapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.foodorderapp.adapter.UserViewPagerAdapter;
import com.example.foodorderapp.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private UserViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //binding.viewPager.setUserInputEnabled(false);
        adapter = new UserViewPagerAdapter(this);
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        binding.bottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
                        break;
                    case 1:
                        binding.bottomNavigationView.getMenu().findItem(R.id.nav_cart).setChecked(true);
                        break;
                    case 2:
                        binding.bottomNavigationView.getMenu().findItem(R.id.nav_feedback).setChecked(true);
                        break;
                    case 3:
                        binding.bottomNavigationView.getMenu().findItem(R.id.nav_contact).setChecked(true);
                        break;
                    case 4:
                        binding.bottomNavigationView.getMenu().findItem(R.id.nav_account).setChecked(true);
                        break;
                }
            }
        });
        binding.bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.nav_home){
                    binding.viewPager.setCurrentItem(0);
                    return  true;
                }  if(id == R.id.nav_cart){
                    binding.viewPager.setCurrentItem(1);
                    return  true;
                }  if(id == R.id.nav_feedback){
                    binding.viewPager.setCurrentItem(2);
                    return  true;
                }  if(id == R.id.nav_contact){
                    binding.viewPager.setCurrentItem(3);
                    return  true;
                }  if(id == R.id.nav_account){
                    binding.viewPager.setCurrentItem(4);
                    return  true;
                }
                return false;
            }
        });

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        new MaterialDialog.Builder(this)
                .title(getString(R.string.app_name))
                .content("Bạn có muốn thoát ứng dụng không")
                .positiveText("Đồng ý")
                .onPositive((dialog, which) -> finishAffinity())
                .negativeText("Huỷ bỏ")
                .cancelable(false)
                .show();
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//binding.viewPager.unregisterOnPageChangeCallback();
//    }
}