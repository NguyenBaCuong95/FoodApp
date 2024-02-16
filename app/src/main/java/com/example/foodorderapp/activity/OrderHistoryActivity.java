package com.example.foodorderapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.foodorderapp.MyApplycation;
import com.example.foodorderapp.R;
import com.example.foodorderapp.adapter.OrderAdapter;
import com.example.foodorderapp.databinding.ActivityOrderHistoryBinding;
import com.example.foodorderapp.model.Order;
import com.example.foodorderapp.preference.DataStoreManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {
    private ActivityOrderHistoryBinding binding;
    private List<Order> mListOrder;
    private OrderAdapter mOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolbar();
        initView();
        getListOrders();

    }
    private void initToolbar() {
        binding.toolbar.imageBack.setVisibility(View.VISIBLE);
        binding.toolbar.imageCart.setVisibility(View.GONE);
        binding.toolbar.txtTitle.setText("Lịch sử đặt hàng");

        binding.toolbar.imageBack.setOnClickListener(v -> onBackPressed());
    }
    private void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rcvOrderHistory.setLayoutManager(linearLayoutManager);
    }

    public void getListOrders() {
        MyApplycation.get(this).getBookingDatabaseReference()
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (mListOrder != null) {
                            mListOrder.clear();
                        } else {
                            mListOrder = new ArrayList<>();
                        }
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Order order = dataSnapshot.getValue(Order.class);
                            if (order != null) {
                                String strEmail = DataStoreManager.getUser().getEmail();
                                if (strEmail.equalsIgnoreCase(order.getEmail())) {
                                    mListOrder.add(0, order);
                                }
                            }
                        }
                        mOrderAdapter = new OrderAdapter(OrderHistoryActivity.this, mListOrder);
                        binding.rcvOrderHistory.setAdapter(mOrderAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mOrderAdapter != null) {
            mOrderAdapter.release();
        }
    }
}