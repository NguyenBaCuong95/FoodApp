package com.example.foodorderapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodorderapp.model.Food;

@Database(entities = {Food.class}, version = 1)
public abstract class FoodDatabase extends RoomDatabase {
    private static FoodDatabase instance;
    public abstract FoodDao getFoodDao();
    public static FoodDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context,FoodDatabase.class,"food.db")
                    .allowMainThreadQueries().build();
        }
        return instance;
    }
}
