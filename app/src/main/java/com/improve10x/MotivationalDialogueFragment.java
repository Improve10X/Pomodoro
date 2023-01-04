package com.improve10x;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.improve10x.pomodoro.PomodoroActivityActionListener;
import com.improve10x.pomodoro.R;
import com.improve10x.pomodoro.databinding.FragmentMotivationalDialogueBinding;
import com.improve10x.pomodoro.fragment.Task;
import com.improve10x.pomodoro.home.PomodoroActivity;


public class MotivationalDialogueFragment extends DialogFragment {

    private FragmentMotivationalDialogueBinding binding;
    private PomodoroActivityActionListener actionListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMotivationalDialogueBinding.inflate(getLayoutInflater());
        handleShortBreak();
        actionListener = (PomodoroActivityActionListener) getActivity();
        return binding.getRoot();
    }

    private void handleShortBreak() {
        binding.startShortBreakBtn.setOnClickListener(view -> {
            actionListener.startShortBreak();
            dismiss();
        });
    }
}