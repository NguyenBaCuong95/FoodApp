package com.example.foodorderapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.databinding.ItemFoodpopularBinding;
import com.example.foodorderapp.listener.OnClickFoodListener;
import com.example.foodorderapp.model.Food;
import com.example.foodorderapp.util.GlideUtils;

import java.util.List;

public class FoodPopularAdapter extends RecyclerView.Adapter<FoodPopularAdapter.FoodPopularViewHolder> {
    private List<Food> list;
    private OnClickFoodListener listener;

    public FoodPopularAdapter(List<Food> list, OnClickFoodListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FoodPopularViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodpopularBinding binding = ItemFoodpopularBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        binding.getRoot().setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));;

        return new FoodPopularViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodPopularViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class FoodPopularViewHolder extends RecyclerView.ViewHolder {
        private ItemFoodpopularBinding binding;

        public FoodPopularViewHolder(@NonNull View itemView, ItemFoodpopularBinding binding) {
            super(itemView);
            this.binding = binding;
        }

        public void onBind(Food food) {
            GlideUtils.loadImage(food.getBanner(),binding.imageFood);
//            Glide.with(binding.getRoot())
//                    .load(food.getBanner())
//                    .error(R.drawable.ic_error)
//                    .dontAnimate()
//                    .into(binding.imageFood);
            if (food.getSale() < 0) {
                binding.tvSaleOff.setVisibility(View.GONE);
            } else {
                binding.tvSaleOff.setVisibility(View.VISIBLE);
                String sale = "Giảm " + food.getSale() + " %";
                binding.tvSaleOff.setText(sale);
            }
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.foodOnClick(food);
                }
            });
        }
    }
}
