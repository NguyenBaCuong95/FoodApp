package com.example.foodorderapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.foodorderapp.databinding.ItemMoreImageBinding;
import com.example.foodorderapp.model.Image;
import com.example.foodorderapp.util.GlideUtils;

import java.util.List;

public class MoreImageAdapter extends RecyclerView.Adapter<MoreImageAdapter.MoreImageViewHolder>{
    private  List<Image> list;

    public MoreImageAdapter(List<Image> mListImages) {
        this.list = mListImages;
    }

    @NonNull
    @Override
    public MoreImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMoreImageBinding binding = ItemMoreImageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MoreImageViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MoreImageViewHolder holder, int position) {
        Image image = list.get(position);
        if (image == null) {
            return;
        }
        holder.onBind(image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MoreImageViewHolder extends RecyclerView.ViewHolder {

        private ItemMoreImageBinding binding;


        public MoreImageViewHolder(@NonNull View itemView, ItemMoreImageBinding binding) {
            super(itemView);
            this.binding = binding;
        }
        public void onBind(Image image){
            GlideUtils.loadImage(image.getUrl(),binding.imageFood);
        }
    }
}
