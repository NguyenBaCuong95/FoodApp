package com.example.foodorderapp.util;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.foodorderapp.R;

public class GlideUtils {
    public static void loadImage(String url, ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(url)
                .error(R.drawable.ic_error)
                .into(imageView);

    }
}
