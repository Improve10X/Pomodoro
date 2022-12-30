package com.improve10x.pomodoro.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.pomodoro.databinding.CompletedItemBinding;

import java.util.List;

public class CompletedTasksAdapter extends RecyclerView.Adapter<CompletedTaskViewHolder> {
    
    private List<Task> tasks;
    
    public void setData(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }
    
    @NonNull
    @Override
    public CompletedTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CompletedItemBinding binding = CompletedItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        CompletedTaskViewHolder completedTaskViewHolder = new CompletedTaskViewHolder(binding);
        return completedTaskViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedTaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.binding.completedListTxt.setText(task.title);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
