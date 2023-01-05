package com.improve10x.pomodoro.completedfragment;


import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.pomodoro.databinding.CompletedItemBinding;


public class CompletedTaskViewHolder extends RecyclerView.ViewHolder {

    CompletedItemBinding binding;
    public CompletedTaskViewHolder(CompletedItemBinding completedItemBinding) {
        super(completedItemBinding.getRoot());
        binding = completedItemBinding;
    }
}
