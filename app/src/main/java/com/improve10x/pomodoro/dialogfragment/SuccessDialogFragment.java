package com.improve10x.pomodoro.dialogfragment;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.improve10x.pomodoro.databinding.FragmentSuccessDialogBinding;
import com.improve10x.pomodoro.dialogfragment.PomodoroActivityActionListener;


public class SuccessDialogFragment extends DialogFragment {

    private FragmentSuccessDialogBinding binding;
    private PomodoroActivityActionListener actionListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSuccessDialogBinding.inflate(getLayoutInflater());
        actionListener = (PomodoroActivityActionListener) getActivity();
        handleLongBreak();
        handleShortBreak();
        handleSkip();
        return binding.getRoot();
    }

    private void handleSkip() {
        binding.skipBreakBtn.setOnClickListener(view -> {
            actionListener.skipBreak();
            dismiss();
        });
    }

    private void handleLongBreak() {
        binding.startLongBreakBtn.setOnClickListener(view -> {
            actionListener.startLongBreak();
            dismiss();
        });
    }

    private void handleShortBreak() {
        binding.startShortBreakBtn.setOnClickListener(view -> {
            actionListener.startShortBreak();
            dismiss();
        });
    }
}