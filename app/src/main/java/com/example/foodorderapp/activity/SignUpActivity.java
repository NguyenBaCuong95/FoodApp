package com.example.foodorderapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderapp.constant.Constant;
import com.example.foodorderapp.constant.GlobalFunction;
import com.example.foodorderapp.databinding.ActivitySignUpBinding;
import com.example.foodorderapp.model.User;
import com.example.foodorderapp.preference.DataStoreManager;
import com.example.foodorderapp.util.StringUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dangky();
            }
        });
        binding.txtDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalFunction.startActivity(SignUpActivity.this, SignInActivity.class);
            }
        });
    }

    private void dangky() {
        String email = binding.txtEmail.getText().toString().trim();
        String passWord = binding.txtPassword.getText().toString().trim();
        if (email.isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Vui lòng nhập địa chỉ Email", Toast.LENGTH_SHORT).show();
        } else if (passWord.isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Vui lòng nhập Password", Toast.LENGTH_SHORT).show();
        } else if (!StringUtils.isValidEmail(email)) {
            Toast.makeText(SignUpActivity.this, "Email không hợp lệ.\nVui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
        } else {
            if (binding.rbQuantrivien.isChecked()) {
                if (!email.contains(Constant.ADMIN_EMAIL_FORMAT)) {
                    Toast.makeText(SignUpActivity.this, "Email dành cho quản trị viên cần có định dạng \"...@admin.com\"", Toast.LENGTH_SHORT).show();
                } else {
                    signUp(email, passWord);
                }
                return;
            }
            if (binding.rbNguoidung.isChecked()) {
                if (email.contains(Constant.ADMIN_EMAIL_FORMAT)) {
                    Toast.makeText(SignUpActivity.this, "Email sai định dạng dành cho người dùng", Toast.LENGTH_SHORT).show();
                } else {
                    signUp(email, passWord);
                }

            }


        }
    }

    private void signUp(String email, String passWord) {
        binding.progressBar.setVisibility(View.VISIBLE);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, passWord)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        binding.progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {

                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user != null) {
                                User userObject = new User(user.getEmail(), passWord);
                                if (user.getEmail() != null && user.getEmail().contains(Constant.ADMIN_EMAIL_FORMAT)) {
                                    userObject.setAdmin(true);
                                }
                                DataStoreManager.setUser(userObject);
                                GlobalFunction.gotoMainActivity(SignUpActivity.this);
                                finishAffinity();
                            }
                        } else {
                            Toast.makeText(SignUpActivity.this, "Đăng ký không thành công \n Vui lòng thử lại",
                                    Toast.LENGTH_SHORT).show();


                        }

                    }
                });


    }
}