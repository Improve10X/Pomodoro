package com.improve10x.pomodoro.fragment;


import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.pomodoro.databinding.CompletedItemBinding;
import com.improve10x.pomodoro.databinding.FragmentCompletedBinding;


public class CompletedTaskViewHolder extends RecyclerView.ViewHolder {

    CompletedItemBinding binding;
    public CompletedTaskViewHolder(CompletedItemBinding completedItemBinding) {
        super(completedItemBinding.getRoot());
        binding = completedItemBinding;
    }
}
