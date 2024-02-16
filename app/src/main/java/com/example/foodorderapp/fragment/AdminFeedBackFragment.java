package com.example.foodorderapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodorderapp.MyApplycation;
import com.example.foodorderapp.R;
import com.example.foodorderapp.adapter.FeedbackAdapter;
import com.example.foodorderapp.databinding.FeedbackfragmentAdminBinding;
import com.example.foodorderapp.model.Feedback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class AdminFeedBackFragment extends Fragment {
    private FeedbackfragmentAdminBinding binding;
    private List<Feedback> list = new ArrayList<>();
    private FeedbackAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FeedbackfragmentAdminBinding.inflate(inflater, container, false);
        binding.rcvFeedback.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(getActivity().getDrawable(R.drawable.divider));
        binding.rcvFeedback.addItemDecoration(divider);
        adapter = new FeedbackAdapter(list);
        binding.rcvFeedback.setAdapter(adapter);
        binding.include2.txtTitle.setText("Phản hồi");
        getFeedback();
        return binding.getRoot();
    }

    private void getFeedback() {
        DatabaseReference reference = ((MyApplycation) getActivity().getApplication()).getDataFeedback();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(list.size()>=0){
                    list.clear();
                }
                for(DataSnapshot s : snapshot.getChildren()){
                    Feedback feedback = s.getValue(Feedback.class);
                    list.add(feedback);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
