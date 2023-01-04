package com.improve10x;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.improve10x.pomodoro.R;
import com.improve10x.pomodoro.databinding.FragmentMotivationalDialogueBinding;


public class MotivationalDialogueFragment extends DialogFragment {

    private FragmentMotivationalDialogueBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMotivationalDialogueBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }


}