package com.example.foodorderapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.foodorderapp.R;
import com.example.foodorderapp.databinding.ActivityForgotPasswordBinding;
import com.example.foodorderapp.util.StringUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {
private ActivityForgotPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.btnGuilaimatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
guiLaiMatKhau();
            }
        });
    }

    private void guiLaiMatKhau() {
        String email = binding.editEmail.getText().toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(ForgotPasswordActivity.this, "Bạn chưa nhập email", Toast.LENGTH_SHORT).show();
        } else if (!StringUtils.isValidEmail(email)) {
            Toast.makeText(ForgotPasswordActivity.this, "Email không hợp lệ\nVui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
        } else {
            resetPassword(email);
        }
    }

    private void resetPassword(String email) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgotPasswordActivity.this,
                                    "Cập nhật mật khẩu thành công\n Vui lòng kiểm tra email",
                                    Toast.LENGTH_SHORT).show();
                            binding.editEmail.setText("");
                        }
                    }
                });
    }
}