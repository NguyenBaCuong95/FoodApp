package com.example.foodorderapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import android.annotation.SuppressLint;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodorderapp.R;
import com.example.foodorderapp.adapter.MoreImageAdapter;
import com.example.foodorderapp.constant.Constant;
import com.example.foodorderapp.database.FoodDatabase;
import com.example.foodorderapp.databinding.ActivityFoodDetailBinding;
import com.example.foodorderapp.event.ReloadListCartEvent;
import com.example.foodorderapp.model.Food;
import com.example.foodorderapp.model.Image;
import com.example.foodorderapp.util.GlideUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class FoodDetailActivity extends AppCompatActivity {
private ActivityFoodDetailBinding binding;
private Food food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFoodDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getDataIntent();
        initToolbar();
        setDataFood();
        listener();

    }

    private void listener() {
        binding.tvAddToCart.setOnClickListener(v -> onClickAddToCart());
        binding.toolbar.imageCart.setOnClickListener(v -> onClickAddToCart());
    }

    private void onClickAddToCart() {

        @SuppressLint("InflateParams") View viewDialog = getLayoutInflater().inflate(R.layout.layout_bottom_sheet_cart, null);

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(viewDialog);

        ImageView imgFoodCart = viewDialog.findViewById(R.id.img_food_cart);
        TextView tvFoodNameCart = viewDialog.findViewById(R.id.tv_food_name_cart);
        TextView tvFoodPriceCart = viewDialog.findViewById(R.id.tv_food_price_cart);
        TextView tvSubtractCount = viewDialog.findViewById(R.id.tv_subtract);
        TextView tvCount = viewDialog.findViewById(R.id.tv_count);
        TextView tvAddCount = viewDialog.findViewById(R.id.tv_add);
        TextView tvCancel = viewDialog.findViewById(R.id.tv_cancel);
        TextView tvAddCart = viewDialog.findViewById(R.id.tv_add_cart);

        GlideUtils.loadImage(food.getImage(), imgFoodCart);
        tvFoodNameCart.setText(food.getName());

        int totalPrice = food.getRealPrice();
        String strTotalPrice = totalPrice + Constant.CURRENCY;
        tvFoodPriceCart.setText(strTotalPrice);

        food.setCount(1);
        food.setTotalPrice(totalPrice);

        tvSubtractCount.setOnClickListener(v -> {
            int count = Integer.parseInt(tvCount.getText().toString());
            if (count <= 1) {
                return;
            }
            int newCount = Integer.parseInt(tvCount.getText().toString()) - 1;
            tvCount.setText(String.valueOf(newCount));

            int totalPrice1 = food.getRealPrice() * newCount;
            String strTotalPrice1 = totalPrice1 + Constant.CURRENCY;
            tvFoodPriceCart.setText(strTotalPrice1);

            food.setCount(newCount);
            food.setTotalPrice(totalPrice1);
        });

        tvAddCount.setOnClickListener(v -> {
            int newCount = Integer.parseInt(tvCount.getText().toString()) + 1;
            tvCount.setText(String.valueOf(newCount));

            int totalPrice2 = food.getRealPrice() * newCount;
            String strTotalPrice2 = totalPrice2 + Constant.CURRENCY;
            tvFoodPriceCart.setText(strTotalPrice2);

            food.setCount(newCount);
            food.setTotalPrice(totalPrice2);
        });

        tvCancel.setOnClickListener(v -> bottomSheetDialog.dismiss());

        tvAddCart.setOnClickListener(v -> {
            FoodDatabase.getInstance(FoodDetailActivity.this).getFoodDao().insertFood(food);
            bottomSheetDialog.dismiss();
            setStatusButton();
            EventBus.getDefault().post(new ReloadListCartEvent());
        });

        bottomSheetDialog.show();
    }

    private void setDataFood() {
        GlideUtils.loadImage(food.getBanner(), binding.imageFood);
        if (food.getSale() <= 0) {
            binding.tvSaleOff.setVisibility(View.GONE);
            binding.tvPrice.setVisibility(View.GONE);

            String strPrice = food.getPrice() + Constant.CURRENCY;
            binding.tvPriceSale.setText(strPrice);
        } else {
            binding.tvSaleOff.setVisibility(View.VISIBLE);
            binding.tvPrice.setVisibility(View.VISIBLE);

            String strSale = "Giảm " + food.getSale() + "%";
            binding.tvSaleOff.setText(strSale);

            String strPriceOld = food.getPrice() + Constant.CURRENCY;
            binding.tvPrice.setText(strPriceOld);
            binding.tvPrice.setPaintFlags(binding.tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            String strRealPrice = food.getRealPrice() + Constant.CURRENCY;
            binding.tvPriceSale.setText(strRealPrice);
        }
        binding.tvFoodName.setText(food.getName());
        binding.tvFoodDescription.setText(food.getDescription());

        displayMoreImages();

        setStatusButton();
    }

    private void setStatusButton() {
        if (isFoodInCart()) {
            binding.tvAddToCart.setBackgroundResource(R.drawable.bg_gray_shape_corner_6);
            binding.tvAddToCart.setText(getString(R.string.added_to_cart));
            binding.tvAddToCart.setTextColor(ContextCompat.getColor(this, R.color.black));
            binding.toolbar.imageCart.setVisibility(View.GONE);
        } else {
            binding.tvAddToCart.setBackgroundResource(R.drawable.bg_green_shape_corner_6);
            binding.tvAddToCart.setText(getString(R.string.add_to_cart));
            binding.tvAddToCart.setTextColor(ContextCompat.getColor(this, R.color.white));
            binding.toolbar.imageCart.setVisibility(View.VISIBLE);
        }
    }

    private boolean isFoodInCart() {
        List<Food> list = FoodDatabase.getInstance(this).getFoodDao().checkFoodInCart(food.getId());
        return list != null && !list.isEmpty();
    }

    private void displayMoreImages() {
        List<Image> list = food.getImages();

        if(list.size()>0){
            binding.tvMoreImageLabel.setVisibility(View.VISIBLE);
            MoreImageAdapter adapter = new MoreImageAdapter(list);
            binding.rcvImages.setAdapter(adapter);
            binding.rcvImages.setLayoutManager(new GridLayoutManager(this,2));
        }
        else {
            binding.tvMoreImageLabel.setVisibility(View.GONE);
        }

    }

    private void initToolbar() {
        binding.toolbar.imageBack.setVisibility(View.VISIBLE);
        binding.toolbar.imageCart.setVisibility(View.VISIBLE);
        binding.toolbar.txtTitle.setText(getString(R.string.food_detail_title));

        binding.toolbar.imageBack.setOnClickListener(v -> onBackPressed());
    }

    private void getDataIntent() {
        Bundle bundle = getIntent().getExtras();
        if(bundle!= null){
        food = (Food) bundle.get(Constant.FOOD_OBJECT);}
    }
}