package com.example.foodorderapp.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodorderapp.MyApplycation;
import com.example.foodorderapp.R;
import com.example.foodorderapp.adapter.RevenueAdapter;
import com.example.foodorderapp.constant.Constant;
import com.example.foodorderapp.constant.GlobalFunction;
import com.example.foodorderapp.databinding.ActivityAdminReportBinding;
import com.example.foodorderapp.listener.IOSingleClickListener;
import com.example.foodorderapp.model.Order;
import com.example.foodorderapp.util.DateTimeUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminReportActivity extends AppCompatActivity {
    private ActivityAdminReportBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminReportBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolbar();
        initListener();
        getListRevenue();
    }

    private void getListRevenue() {
        MyApplycation.get(this).getBookingDatabaseReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Order> list = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Order order = dataSnapshot.getValue(Order.class);
                    if (canAddOrder(order)) {
                        list.add(0, order);
                    }
                }
                handleDataHistories(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void handleDataHistories(List<Order> list) {
        if (list == null) {
            return;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rcvOrderHistory.setLayoutManager(linearLayoutManager);
        RevenueAdapter revenueAdapter = new RevenueAdapter(list);
        binding.rcvOrderHistory.setAdapter(revenueAdapter);

        // Calculate total
        String strTotalValue = getTotalValues(list) + Constant.CURRENCY;
        binding.tvTotalValue.setText(strTotalValue);
    }
    private int getTotalValues(List<Order> list) {
        if (list == null || list.isEmpty()) {
            return 0;
        }

        int total = 0;
        for (Order order : list) {
            total += order.getAmount();
        }
        return total;
    }
    private boolean canAddOrder(@Nullable Order order) {
        if (order == null) {
            return false;
        }
        if (!order.isCompleted()) {
            return false;
        }
        String strDateFrom = binding.tvDateFrom.getText().toString();
        String strDateTo = binding.tvDateTo.getText().toString();
        if (strDateFrom.isEmpty() && strDateTo.isEmpty()) {
            return true;
        }
        String strDateOrder = DateTimeUtils.convertTimeStampToDate_2(order.getId());
        long longOrder = Long.parseLong(DateTimeUtils.convertDate2ToTimeStamp(strDateOrder));

        if (strDateFrom.isEmpty() && !strDateTo.isEmpty()) {
            long longDateTo = Long.parseLong(DateTimeUtils.convertDate2ToTimeStamp(strDateTo));
            return longOrder <= longDateTo;
        }
        if (!strDateFrom.isEmpty() && strDateTo.isEmpty()) {
            long longDateFrom = Long.parseLong(DateTimeUtils.convertDate2ToTimeStamp(strDateFrom));
            return longOrder >= longDateFrom;
        }
        long longDateTo = Long.parseLong(DateTimeUtils.convertDate2ToTimeStamp(strDateTo));
        long longDateFrom = Long.parseLong(DateTimeUtils.convertDate2ToTimeStamp(strDateFrom));
        return longOrder >= longDateFrom && longOrder <= longDateTo;
    }


    private void initListener() {
        binding.tvDateFrom.setOnClickListener(new IOSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                GlobalFunction.showDatePicker(AdminReportActivity.this,
                        binding.tvDateFrom.getText().toString(), date -> {
                            binding.tvDateFrom.setText(date);
                            getListRevenue();
                        });
            }
        });

        binding.tvDateTo.setOnClickListener(new IOSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                GlobalFunction.showDatePicker(AdminReportActivity.this,
                        binding.tvDateTo.getText().toString(), date -> {
                            binding.tvDateTo.setText(date);
                            getListRevenue();
                        });
            }
        });
    }

    private void initToolbar() {
        binding.toolbar.imageBack.setVisibility(View.VISIBLE);
        binding.toolbar.imageCart.setVisibility(View.GONE);
        binding.toolbar.txtTitle.setText("Doanh thu:");

        binding.toolbar.imageBack.setOnClickListener(v -> onBackPressed());
    }
}