package com.example.foodorderapp.listener;

import com.example.foodorderapp.model.Food;

public interface IoManagerFoodListener {
   void updateFood(Food food);
    void deleteFood(Food food);

}
