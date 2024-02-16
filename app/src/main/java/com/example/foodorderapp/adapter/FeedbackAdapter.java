package com.example.foodorderapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderapp.databinding.ItemFeedbackBinding;
import com.example.foodorderapp.model.Feedback;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {
    private List<Feedback> list;

    public FeedbackAdapter(List<Feedback> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFeedbackBinding binding = ItemFeedbackBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false);
        return new FeedbackViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        Feedback feedback = list.get(position);
        holder.onBind(feedback);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FeedbackViewHolder extends RecyclerView.ViewHolder {
        private ItemFeedbackBinding binding;

        public FeedbackViewHolder(@NonNull View itemView, ItemFeedbackBinding binding) {
            super(itemView);
            this.binding = binding;
        }

        public void onBind(Feedback feedback) {
            binding.tvEmail.setText(feedback.getEmail());
            binding.tvFeedback.setText(feedback.getComment());
        }
    }
}
