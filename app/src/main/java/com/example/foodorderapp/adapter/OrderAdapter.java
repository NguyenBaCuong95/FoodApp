package com.example.foodorderapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.R;
import com.example.foodorderapp.constant.Constant;
import com.example.foodorderapp.databinding.ItemOrderBinding;
import com.example.foodorderapp.model.Order;
import com.example.foodorderapp.util.DateTimeUtils;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

     public Context mContext;
    private List<Order> list;

    public OrderAdapter(Context mContext, List<Order> mListOrder) {
        this.mContext = mContext;
        this.list = mListOrder;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderBinding itemOrderBinding = ItemOrderBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new OrderViewHolder(itemOrderBinding.getRoot(),itemOrderBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = list.get(position);
holder.onBind(order);
    }

    @Override
    public int getItemCount() {

        return list.size();

    }

    public void release() {
        mContext = null;
    }

    public  class OrderViewHolder extends RecyclerView.ViewHolder {

        private final ItemOrderBinding binding;


        public OrderViewHolder(@NonNull View itemView, ItemOrderBinding binding) {
            super(itemView);
            this.binding = binding;
        }

        public void onBind(Order order) {
            if (order == null) {
                return;
            }
            if (order.isCompleted()) {
                binding.layoutItem.setBackgroundColor(mContext.getResources().getColor(R.color.black_overlay));
            } else {
               binding.layoutItem.setBackgroundColor(mContext.getResources().getColor(R.color.white));
            }
      binding.tvId.setText(String.valueOf(order.getId()));
            binding.tvName.setText(order.getName());
           binding.tvPhone.setText(order.getPhone());
            binding.tvAddress.setText(order.getAddress());
            binding.tvMenu.setText(order.getFoods());
            binding.tvDate.setText(DateTimeUtils.convertTimeStampToDate(order.getId()));

            String strAmount = order.getAmount() + Constant.CURRENCY;
            binding.tvTotalAmount.setText(strAmount);

            String paymentMethod = "";
            if (Constant.TYPE_PAYMENT_CASH == order.getPayment()) {
                paymentMethod = Constant.PAYMENT_METHOD_CASH;
            }
            binding.tvPayment.setText(paymentMethod);
        }
    }
}
