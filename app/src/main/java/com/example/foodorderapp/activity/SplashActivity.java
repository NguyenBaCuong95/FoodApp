package com.example.foodorderapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import com.example.foodorderapp.R;
import com.example.foodorderapp.constant.GlobalFunction;
import com.example.foodorderapp.databinding.ActivitySplashBinding;
import com.example.foodorderapp.preference.DataStoreManager;

public class SplashActivity extends AppCompatActivity {
private ActivitySplashBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(DataStoreManager.getUser()!= null  ) {
                    GlobalFunction.gotoMainActivity(SplashActivity.this);
                } else {
                    GlobalFunction.startActivity(SplashActivity.this, SignInActivity.class);
                }
                finish();
            }
        }, 2000);
    }
}