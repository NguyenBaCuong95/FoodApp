package com.example.foodorderapp.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.constant.Constant;
import com.example.foodorderapp.databinding.ItemfoodSuggestBinding;
import com.example.foodorderapp.listener.OnClickFoodListener;
import com.example.foodorderapp.model.Food;
import com.example.foodorderapp.util.GlideUtils;

import java.util.List;

public class FoodSuggestAdapter extends RecyclerView.Adapter<FoodSuggestAdapter.FoodViewHolder>{
    private List<Food> list;
    private OnClickFoodListener listener;

    public FoodSuggestAdapter(List<Food> list, OnClickFoodListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemfoodSuggestBinding binding = ItemfoodSuggestBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);

        return new FoodViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class FoodViewHolder extends RecyclerView.ViewHolder {
        private ItemfoodSuggestBinding binding;
        public FoodViewHolder(@NonNull View itemView, ItemfoodSuggestBinding binding) {
            super(itemView);
            this.binding = binding;
        }
        public void onBind(Food food){
            GlideUtils.loadImage(food.getImage(), binding.imgFood);
            if (food.getSale() <= 0) {
                binding.tvSaleOff.setVisibility(View.GONE);
                binding.tvPriceSale.setVisibility(View.GONE);

                String strPrice = food.getPrice() + Constant.CURRENCY;
                binding.tvPrice.setText(strPrice);
            } else {
//                binding.tvSaleOff.setVisibility(View.VISIBLE);
//                binding.tvPrice.setVisibility(View.VISIBLE);
                String strSale = "Giảm " + food.getSale() + "%";
                binding.tvSaleOff.setText(strSale);

                String strOldPrice = food.getPrice() + Constant.CURRENCY;
                binding.tvPrice.setText(strOldPrice);
                binding.tvPrice.setPaintFlags(binding.tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                String strRealPrice = food.getRealPrice() + Constant.CURRENCY;
                binding.tvPriceSale.setText(strRealPrice);
            }
            binding.tvFoodName.setText(food.getName());

            binding.layoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.foodOnClick(food);
                }
            });
        }
    }
}
