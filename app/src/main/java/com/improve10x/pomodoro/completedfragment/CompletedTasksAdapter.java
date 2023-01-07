package com.improve10x.pomodoro.completedfragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.pomodoro.addedittask.Task;
import com.improve10x.pomodoro.databinding.CompletedItemBinding;
import com.squareup.picasso.Picasso;

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
        if (task.noOfPomodoros == task.expectedPomodoro) {
            Picasso.get().load("https://static.vecteezy.com/system/resources/previews/000/458/152/original/vector-abstract-3d-success-design.jpg").into(holder.binding.completeImageUrlImg);
        } else if (task.noOfPomodoros > task.expectedPomodoro) {
            Picasso.get().load("https://i.huffpost.com/gen/2186196/images/o-SECRETS-OF-SUCCESS-facebook.jpg").into(holder.binding.completeImageUrlImg);
        } else {
            Picasso.get().load("https://i.pinimg.com/originals/64/6e/41/646e41b261004f0605d63b6ef44b0be6.png").into(holder.binding.completeImageUrlImg);
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
