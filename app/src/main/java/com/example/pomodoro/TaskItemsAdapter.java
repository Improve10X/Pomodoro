package com.example.pomodoro;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pomodoro.databinding.TodoItemBinding;

import java.util.ArrayList;

public class TaskItemsAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private ArrayList<Task> taskItems;

    void setTaskItems(ArrayList<Task> taskItems) {
        this.taskItems = taskItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TodoItemBinding binding = TodoItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        TaskViewHolder taskViewHolder = new TaskViewHolder(binding);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskItems.get(position);
        holder.binding.todoListTxt.setText(task.taskList);
    }

    @Override
    public int getItemCount() {
        return taskItems.size();
    }
}
