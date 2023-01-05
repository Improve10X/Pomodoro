package com.improve10x.pomodoro.dialogfragment;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.improve10x.pomodoro.databinding.FragmentMotivationalDialogueBinding;


public class MotivationalDialogueFragment extends DialogFragment {

    private FragmentMotivationalDialogueBinding binding;
    private PomodoroActivityActionListener actionListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMotivationalDialogueBinding.inflate(getLayoutInflater());
        actionListener = (PomodoroActivityActionListener) getActivity();
        handleShortBreak();
        handleLongBreak();
        handleSkipBreak();
        return binding.getRoot();
    }

    private void handleShortBreak() {
        binding.startShortBreakBtn.setOnClickListener(view -> {
            actionListener.startShortBreak();
            dismiss();
        });
    }

    private void handleLongBreak() {
        binding.startLongBreakBtn.setOnClickListener(view -> {
            actionListener.startLongBreak();
            dismiss();
        });
    }

    private void handleSkipBreak() {
        binding.skipBreak.setOnClickListener(view -> {
            actionListener.skipBreak();
            dismiss();
        });
    }
}