package com.improve10x.pomodoro;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.pomodoro.databinding.TodoItemBinding;

import java.util.List;

public class TaskItemsAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private List<Task> taskItems;

    void setTaskItems(List<Task> taskItems) {
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
