package com.example.foodorderapp.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodorderapp.MyApplycation;
import com.example.foodorderapp.R;
import com.example.foodorderapp.activity.AddFoodActivity;
import com.example.foodorderapp.adapter.AdminFoodAdapter;
import com.example.foodorderapp.constant.Constant;
import com.example.foodorderapp.constant.GlobalFunction;
import com.example.foodorderapp.databinding.HomefragmentAdminBinding;
import com.example.foodorderapp.listener.IoManagerFoodListener;
import com.example.foodorderapp.model.Food;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeFragment extends Fragment {
    private HomefragmentAdminBinding binding;
    private List<Food> list;
    private AdminFoodAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = HomefragmentAdminBinding.inflate(inflater, container, false);
        initView();
        initListener();
        getListFood("");
        return binding.getRoot();
    }


    private void initView() {
        list = new ArrayList<>();
        binding.recycleviewHomeadmin.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new AdminFoodAdapter(list, new IoManagerFoodListener() {
            @Override
            public void updateFood(Food food) {
                updatefood(food);
            }

            @Override
            public void deleteFood(Food food) {
                deletefood(food);
            }
        });
        binding.recycleviewHomeadmin.setAdapter(adapter);
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(getActivity().getDrawable(R.drawable.divider));
        binding.recycleviewHomeadmin.addItemDecoration(divider);
    }

    private void initListener() {
        binding.imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchFood();
            }
        });
        binding.floatingbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewFood();
            }
        });
        binding.txtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    searchFood();
                    return true;
                }
                return false;
            }
        });
        binding.txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String strKey = s.toString().trim();
                if (strKey.equals("") || strKey.length() == 0) {
                    searchFood();
                }

            }
        });
    }

    private void addNewFood() {
        GlobalFunction.startActivity(getContext(), AddFoodActivity.class);
    }

    private void searchFood() {
        String key = binding.txtSearch.getText().toString().trim();
        if (list.size() >= 0) {
            list.clear();
        } else {
            list = new ArrayList<>();
        }
        getListFood(key);
        GlobalFunction.hideKeyBoard(getActivity());
    }

    private void getListFood(String key) {
        DatabaseReference fooddb =  MyApplycation.get(getActivity()).getDataFood();
        //List<Food> list1 = new ArrayList<>();
        fooddb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Food food = snapshot.getValue(Food.class);
                if (key.isEmpty()) {
                    list.add(0, food);
                } else {
                    if (food.getName().trim().toLowerCase().contains(key.trim().toLowerCase())) {
                        list.add(0, food);
                    }
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Food food = snapshot.getValue(Food.class);
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getId() == food.getId()) {
                        list.set(i, food);
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Food food = snapshot.getValue(Food.class);
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getId() == food.getId()) {
                        list.remove(list.get(i));
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void deletefood(Food food) {
        new AlertDialog.Builder(getContext())
                .setTitle("Xác nhận xoá")
                .setMessage("Bạn có chắc chắn muốn xoá mục này không?")
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference fooddb = ((MyApplycation) requireActivity().getApplication()).getDataFood();
                        fooddb.child(String.valueOf(food.getId())).removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                Toast.makeText(getContext(), "Xoá món ăn thành công", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                })
                .setNegativeButton("Huỷ bỏ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .show();
    }

    private void updatefood(Food food) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.FOOD_OBJECT, food);
        GlobalFunction.startActivity(getActivity(), AddFoodActivity.class, bundle);
    }
}
