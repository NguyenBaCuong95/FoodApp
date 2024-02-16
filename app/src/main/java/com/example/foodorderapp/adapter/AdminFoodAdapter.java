package com.example.foodorderapp.adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.constant.Constant;
import com.example.foodorderapp.databinding.ItemfoodAdminBinding;
import com.example.foodorderapp.listener.IoManagerFoodListener;
import com.example.foodorderapp.model.Food;
import com.example.foodorderapp.util.GlideUtils;

import java.util.List;

public class AdminFoodAdapter extends RecyclerView.Adapter<AdminFoodAdapter.FoodViewHolder> {
    private List<Food> list;

    private IoManagerFoodListener listener;

    public AdminFoodAdapter(List<Food> list, IoManagerFoodListener listener) {
        this.list = list;
        this.listener = listener;
    }
    public void setListAdapter(List<Food> listFood){
        list.clear();
        list.addAll(listFood);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemfoodAdminBinding binding = ItemfoodAdminBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
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

    public class FoodViewHolder extends RecyclerView.ViewHolder {
        private ItemfoodAdminBinding binding;
        private Food food;

        public FoodViewHolder(@NonNull View itemView, ItemfoodAdminBinding binding) {
            super(itemView);
            this.binding = binding;

        }

        public void onBind(Food food) {

            GlideUtils.loadImage(food.getImage(),binding.imgFood);
            if (food.getSale() <= 0) {
               binding.tvSaleOff.setVisibility(View.GONE);
              // binding.txtPrice.setVisibility(View.GONE);

                String strPrice = food.getPrice() + Constant.CURRENCY;
                binding.txtPrice.setText(strPrice);
               // binding.txtPricesale.setText(strPrice);
            } else {
                binding.tvSaleOff.setVisibility(View.VISIBLE);
               binding.txtPrice.setVisibility(View.VISIBLE);
                String strSale = "Giảm " + food.getSale() + "%";
                binding.tvSaleOff.setText(strSale);

                String strOldPrice = food.getPrice() + Constant.CURRENCY;
              binding.txtPrice.setText(strOldPrice);
                binding.txtPrice.setPaintFlags(binding.txtPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                String strRealPrice = food.getRealPrice() + Constant.CURRENCY;
               binding.txtPricesale.setText(strRealPrice);
            }
            binding.txtFoodname.setText(food.getName());
            binding.txtDescription.setText(food.getDescription());
            if (food.isPopular()) {
                binding.txtPopular.setText("Có");
            } else if(food.isPopular()==false) {
              binding.txtPopular.setText("Không");
            }

            binding.imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.updateFood(food);
                }
            });
           binding.imgDelete.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   listener.deleteFood(food);
               }
           });



        }

    }
}
