package com.improve10x.pomodoro.todofragment;

import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.pomodoro.databinding.TodoItemBinding;

public class TaskViewHolder extends RecyclerView.ViewHolder {
    TodoItemBinding binding;

    public TaskViewHolder(TodoItemBinding todoItemBinding) {
        super(todoItemBinding.getRoot());
        binding = todoItemBinding;
    }
}
