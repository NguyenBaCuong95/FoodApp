package com.example.foodorderapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodorderapp.MainActivity;
import com.example.foodorderapp.MyApplycation;
import com.example.foodorderapp.constant.GlobalFunction;
import com.example.foodorderapp.databinding.FeedbackFragmentBinding;
import com.example.foodorderapp.model.Feedback;
import com.example.foodorderapp.preference.DataStoreManager;

public class FeedbackFragment extends Fragment {
    private FeedbackFragmentBinding mFragmentFeedbackBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentFeedbackBinding = FeedbackFragmentBinding.inflate(inflater, container, false);

        mFragmentFeedbackBinding.edtEmail.setText(DataStoreManager.getUser().getEmail());
        mFragmentFeedbackBinding.tvSendFeedback.setOnClickListener(v -> onClickSendFeedback());

        return mFragmentFeedbackBinding.getRoot();
    }

    private void onClickSendFeedback() {
        if (getActivity() == null) {
            return;
        }
        //MainActivity activity = (MainActivity) getActivity();

        String strName = mFragmentFeedbackBinding.edtName.getText().toString();
        String strPhone = mFragmentFeedbackBinding.edtPhone.getText().toString();
        String strEmail = mFragmentFeedbackBinding.edtEmail.getText().toString();
        String strComment = mFragmentFeedbackBinding.edtComment.getText().toString();

        if (strName.isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng nhập họ và tên", Toast.LENGTH_SHORT).show();
        } else if (strComment.isEmpty()) {
            Toast.makeText(getActivity(), "Vui lòng nhập phản hồi", Toast.LENGTH_SHORT).show();
        } else {

            Feedback feedback = new Feedback(strName, strPhone, strEmail, strComment);
            MyApplycation.get(getActivity()).getDataFeedback()
                    .child(String.valueOf(System.currentTimeMillis()))
                    .setValue(feedback, (databaseError, databaseReference) -> {
                       // activity.showProgressDialog(false);
                        sendFeedbackSuccess();
                    });
        }
    }

    public void sendFeedbackSuccess() {
        GlobalFunction.hideKeyBoard(getActivity());
        Toast.makeText(getActivity(), "Gửi phản hồi thành công", Toast.LENGTH_SHORT).show();
        mFragmentFeedbackBinding.edtName.setText("");
        mFragmentFeedbackBinding.edtPhone.setText("");
        mFragmentFeedbackBinding.edtComment.setText("");
    }


}
