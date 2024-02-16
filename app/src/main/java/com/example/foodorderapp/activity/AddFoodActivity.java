package com.example.foodorderapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodorderapp.MyApplycation;
import com.example.foodorderapp.R;
import com.example.foodorderapp.constant.Constant;
import com.example.foodorderapp.constant.GlobalFunction;
import com.example.foodorderapp.databinding.ActivityAddFoodBinding;
import com.example.foodorderapp.model.Food;
import com.example.foodorderapp.model.FoodObject;
import com.example.foodorderapp.model.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddFoodActivity extends AppCompatActivity {
    private ActivityAddFoodBinding binding;
    private Food food;
    private Boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getDataIntent();
        initToolbar();
        initView();

        binding.btnAddOrEdit.setOnClickListener(v -> addOrEditFood());
    }


    private void getDataIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            food = (Food) bundle.get(Constant.FOOD_OBJECT);
            isUpdate = true;
        }

    }

    private void initToolbar() {
        binding.include.imageCart.setVisibility(View.GONE);
        binding.include.imageBack.setVisibility(View.VISIBLE);
        binding.include.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void initView() {
        if (isUpdate) {
            binding.include.txtTitle.setText(getString(R.string.edit_food));
            binding.btnAddOrEdit.setText(getString(R.string.action_edit));

            binding.edtName.setText(food.getName());
            binding.edtDescription.setText(food.getDescription());
            binding.edtPrice.setText(String.valueOf(food.getPrice()));
            binding.edtDiscount.setText(String.valueOf(food.getSale()));
            binding.edtImage.setText(food.getImage());
            binding.edtImageBanner.setText(food.getBanner());
            binding.chbPopular.setChecked(food.isPopular());
            binding.edtOtherImage.setText(getTextOtherImages());
        } else {
            binding.include.txtTitle.setText(getString(R.string.add_food));
            binding.btnAddOrEdit.setText(getString(R.string.action_add));
        }
    }

    private String getTextOtherImages() {
        String result = "";
        if (food == null || food.getImages() == null || food.getImages().isEmpty()) {
            return result;
        } else {
            for (Image image : food.getImages()) {
                if (result.isEmpty()) {
                    result = result + image.getUrl();
                } else {
                    result = result + ";" + image.getUrl();
                }
            }
        }
        return result;
    }

    private void addOrEditFood() {
        String strName = binding.edtName.getText().toString().trim();
        String strDescription = binding.edtDescription.getText().toString().trim();
        String strPrice = binding.edtPrice.getText().toString().trim();
        String strDiscount = binding.edtDiscount.getText().toString().trim();
        String strImage = binding.edtImage.getText().toString().trim();
        String strImageBanner = binding.edtImageBanner.getText().toString().trim();
        boolean isPopular = binding.chbPopular.isChecked();
        String strOtherImages = binding.edtOtherImage.getText().toString().trim();
        List<Image> imageList = new ArrayList<>();
        if (!strOtherImages.isEmpty()) {
            String[] strings = strOtherImages.split(";");
            for (String str : strings) {
                Image image = new Image(str);
                imageList.add(image);
            }
        }
        if (strName.isEmpty()) {
            Toast.makeText(this, getString(R.string.msg_name_food_require), Toast.LENGTH_SHORT).show();
        } else if (strDescription.isEmpty()) {
            Toast.makeText(this, getString(R.string.msg_description_food_require), Toast.LENGTH_SHORT).show();
        } else if (strPrice.isEmpty()) {
            Toast.makeText(this, getString(R.string.msg_price_food_require), Toast.LENGTH_SHORT).show();

        } else if (strDiscount.isEmpty()) {
            Toast.makeText(this, getString(R.string.msg_discount_food_require), Toast.LENGTH_SHORT).show();

        } else if (strImage.isEmpty()) {
            Toast.makeText(this, getString(R.string.msg_image_food_require), Toast.LENGTH_SHORT).show();

        } else if (strImageBanner.isEmpty()) {
            Toast.makeText(this, getString(R.string.msg_image_banner_food_require), Toast.LENGTH_SHORT).show();

        }
//updateFood
        if (isUpdate) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", strName);
            map.put("description", strDescription);
            map.put("price", Integer.parseInt(strPrice));
            map.put("sale", Integer.parseInt(strDiscount));
            map.put("image", strImage);
            map.put("banner", strImageBanner);
            map.put("popular", isPopular);
            if (!imageList.isEmpty()) {
                map.put("images", imageList);
            }

            ((MyApplycation) this.getApplication()).getDataFood()
                    .child(String.valueOf(food.getId())).updateChildren(map, (error, ref) -> {

                        Toast.makeText(AddFoodActivity.this,
                                getString(R.string.msg_edit_food_success), Toast.LENGTH_SHORT).show();
                        GlobalFunction.hideKeyBoard(this);
                    });
            return;
        }
        // Add food

        long foodId = System.currentTimeMillis();
        FoodObject food1 = new FoodObject(foodId, strName, strDescription, Integer.parseInt(strPrice),
                Integer.parseInt(strDiscount), strImage, strImageBanner, isPopular);
        if (!imageList.isEmpty()) {
            food1.setImages(imageList);
        }
        ((MyApplycation) this.getApplication()).getDataFood()
                .child(String.valueOf(foodId)).setValue(food1, (error, ref) -> {
                 
                   binding.edtName.setText("");
                   binding.edtDescription.setText("");
                   binding.edtPrice.setText("");
                   binding.edtDiscount.setText("");
                   binding.edtImage.setText("");
                   binding.edtImageBanner.setText("");
                   binding.chbPopular.setChecked(false);
                   binding.edtOtherImage.setText("");
                    GlobalFunction.hideKeyBoard(this);
                    Toast.makeText(this, getString(R.string.msg_add_food_success), Toast.LENGTH_SHORT).show();
                });
    }
}