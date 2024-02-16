package com.example.foodorderapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderapp.constant.Constant;
import com.example.foodorderapp.constant.GlobalFunction;
import com.example.foodorderapp.databinding.ActivitySignInBinding;
import com.example.foodorderapp.model.User;
import com.example.foodorderapp.preference.DataStoreManager;
import com.example.foodorderapp.util.StringUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {
    private ActivitySignInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.txtDangKyDN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalFunction.startActivity(SignInActivity.this, SignUpActivity.class);
            }
        });
        binding.txtQuenmatkhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalFunction.startActivity(SignInActivity.this, ForgotPasswordActivity.class);
            }
        });
        binding.btnDangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangnhap();
            }
        });
    }

    private void dangnhap() {

        String email = binding.txtEmail.getText().toString().trim();
        String passWord = binding.txtPassword.getText().toString().trim();
        if(email.isEmpty()){
            Toast.makeText(SignInActivity.this,"Vui lòng nhập địa chỉ Email", Toast.LENGTH_SHORT).show();
        } else  if(passWord.isEmpty()){
            Toast.makeText(SignInActivity.this,"Vui lòng nhập Password", Toast.LENGTH_SHORT).show();
        } else if (!StringUtils.isValidEmail(email)){
            Toast.makeText(SignInActivity.this,"Email không hợp lệ.\nVui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
        } else {
            if (binding.rbQuantrivien.isChecked()) {
                if (!email.contains(Constant.ADMIN_EMAIL_FORMAT)) {
                    Toast.makeText(SignInActivity.this, "Email dành cho quản trị viên cần có định dạng \"...@admin.com\"", Toast.LENGTH_SHORT).show();
                } else {
                    signInUser(email, passWord);
                }
                return;
            }
            if (binding.rbNguoidung.isChecked()) {
                if (email.contains(Constant.ADMIN_EMAIL_FORMAT)) {
                    Toast.makeText(SignInActivity.this, "Email sai định dạng dành cho người dùng", Toast.LENGTH_SHORT).show();
                } else {
                    signInUser(email, passWord);
                }

            }


        }
    }

    private void signInUser(String email, String passWord) {
        binding.progressBar2.setVisibility(View.VISIBLE);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email,passWord)
                .addOnCompleteListener(this, task -> {
                    binding.progressBar2.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            User userObject = new User(user.getEmail(), passWord);
                            if (user.getEmail() != null && user.getEmail().contains(Constant.ADMIN_EMAIL_FORMAT)) {
                                userObject.setAdmin(true);
                            }
                            DataStoreManager.setUser(userObject);
                            GlobalFunction.gotoMainActivity(SignInActivity.this);
                            finishAffinity();
                        }
                    } else {
                        Toast.makeText(SignInActivity.this, "Đăng nhập không thành công\n Vui lòng thử lại",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}