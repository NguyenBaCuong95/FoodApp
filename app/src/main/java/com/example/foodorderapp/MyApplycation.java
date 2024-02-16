package com.example.foodorderapp;

import android.app.Application;
import android.content.Context;

import com.example.foodorderapp.preference.DataStoreManager;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyApplycation extends Application {
    private static final String FIREBASE_URL = "https://foodapp-f4104-default-rtdb.firebaseio.com";
    private FirebaseDatabase mFirebaseDatabase;
    public static MyApplycation get(Context context) {
        return (MyApplycation) context.getApplicationContext();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance(FIREBASE_URL);
        DataStoreManager.init(getApplicationContext());
    }
    public DatabaseReference getDataFood(){
        return mFirebaseDatabase.getReference("/food");
    }
    public DatabaseReference getDataFeedback(){
        return mFirebaseDatabase.getReference("/feedback");

    }
    public DatabaseReference getBookingDatabaseReference() {
        return mFirebaseDatabase.getReference("/booking");
    }
}
