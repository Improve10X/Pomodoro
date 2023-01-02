package com.improve10x.pomodoro;


import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import com.improve10x.pomodoro.R;
import com.improve10x.pomodoro.databinding.EditFragmentDialogBinding;
import com.improve10x.pomodoro.fragment.Task;


public class EditDialogFragment extends DialogFragment {

    private EditFragmentDialogBinding binding;
    protected Task task;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = EditFragmentDialogBinding.inflate(getLayoutInflater());
        View view = (binding.getRoot());
        Bundle bundle = getArguments();
        task = (Task) bundle.getSerializable(Constants.KEY_Task);
        handleEdit(task);
        return view;
    }

    private void handleEdit(Task task) {
        binding.editBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), EditTaskActivity.class);
            intent.putExtra(Constants.KEY_Task, task);
            startActivity(intent);
        });
    }
}