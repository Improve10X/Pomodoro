package com.example.pomodoro;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pomodoro.databinding.TodoItemBinding;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    TodoItemBinding binding;

    public TaskViewHolder(TodoItemBinding todoItemBinding) {
        super(todoItemBinding.getRoot());
        binding = todoItemBinding;
    }
}
