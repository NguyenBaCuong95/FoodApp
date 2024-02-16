package com.example.foodorderapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodorderapp.MyApplycation;
import com.example.foodorderapp.activity.FoodDetailActivity;
import com.example.foodorderapp.adapter.FoodPopularAdapter;
import com.example.foodorderapp.adapter.FoodSuggestAdapter;
import com.example.foodorderapp.constant.Constant;
import com.example.foodorderapp.constant.GlobalFunction;
import com.example.foodorderapp.databinding.FragmentHomeBinding;
import com.example.foodorderapp.listener.OnClickFoodListener;
import com.example.foodorderapp.model.Food;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private List<Food> mListFood ;
    private List<Food> mListFoodPopular ;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (binding.viewpager2.getCurrentItem() == mListFoodPopular.size() - 1) {
                binding.viewpager2.setCurrentItem(0);
                return;
            }else {
                binding.viewpager2.setCurrentItem(binding.viewpager2.getCurrentItem() + 1);}

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        binding.layoutContent.setVisibility(View.VISIBLE);
        getListFoodFromFirebase("");
        initListener();

        return binding.getRoot();
    }



    private void initListener() {
        binding.edtSearchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Do nothing
            }

            @Override
            public void afterTextChanged(Editable s) {

                String strKey = s.toString().trim();
                Log.e("BaCuong",strKey);
                if (strKey.equals("") || strKey.length() == 0) {
                    if (mListFood != null)
                        mListFood.clear();
                    getListFoodFromFirebase("");
                }
            }
        });

        binding.imgSearch.setOnClickListener(view -> searchFood());

        binding.edtSearchName.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchFood();
                return true;
            }
            return false;
        });
    }

    private void displayListFoodPopular() {
        FoodPopularAdapter mFoodPopularAdapter = new FoodPopularAdapter(getListFoodPopular(), this::goToFoodDetail);
        binding.viewpager2.setAdapter(mFoodPopularAdapter);
        binding.indicator3.setViewPager(binding.viewpager2);

        binding.viewpager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                handler.removeCallbacks(runnable);
                handler.postDelayed(runnable, 3000);
            }
        });
    }

    private void displayListFoodSuggest() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        binding.rcvFood.setLayoutManager(gridLayoutManager);

        FoodSuggestAdapter adapter = new FoodSuggestAdapter(mListFood, new OnClickFoodListener() {
            @Override
            public void foodOnClick(Food food) {
                goToFoodDetail(food);
            }
        });

        binding.rcvFood.setAdapter(adapter);
    }

    private List<Food> getListFoodPopular() {
        mListFoodPopular = new ArrayList<>();
        if (mListFood == null || mListFood.isEmpty()) {
            return mListFoodPopular;
        }
        for (Food food : mListFood) {
            if (food.isPopular()) {
                mListFoodPopular.add(food);
            }
        }
        return mListFoodPopular;
    }

    private void getListFoodFromFirebase(String key) {
        if (getActivity() == null) {
            return;
        }
        MyApplycation.get(getActivity()).getDataFood().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                binding.layoutContent.setVisibility(View.VISIBLE);
                mListFood = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Food food = dataSnapshot.getValue(Food.class);
                    if (food == null) {
                        return;
                    }

                    if (key.isEmpty()) {
                        mListFood.add(0, food);
                    } else {
                        if (food.getName().trim().toLowerCase().contains(key.trim().toLowerCase())) {
                            mListFood.add(0, food);
                        }
                    }
                }
                displayListFoodPopular();
                displayListFoodSuggest();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void searchFood() {
        String strKey = binding.edtSearchName.getText().toString().trim();
        if (mListFood != null) mListFood.clear();
        getListFoodFromFirebase(strKey);
        GlobalFunction.hideKeyBoard(getActivity());
    }

    private void goToFoodDetail(@NonNull Food food) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.FOOD_OBJECT, food);
        GlobalFunction.startActivity(getActivity(), FoodDetailActivity.class, bundle);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 3000);
    }


    }

