package com.improve10x.pomodoro.completedfragment;

import android.view.LayoutInflater;
import android.view.View;
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
        holder.binding.countDownBar.setNumStars(task.expectedPomodoro);
        if (task.noOfPomodoros == task.expectedPomodoro) {
            holder.binding.countDownBar.setRating(task.noOfPomodoros);
            holder.binding.extraCountDownBar.setVisibility(View.GONE);
            Picasso.get().load("https://play-lh.googleusercontent.com/lvzhOnGwhocUQ-eGWpJZvVskxKByATRGU2tkSXhjjq_5YItUL-Z_oCjmHJUJsMJqQEw").into(holder.binding.completeImageUrlImg);
           // Picasso.get().load("https://static.vecteezy.com/system/resources/previews/000/458/152/original/vector-abstract-3d-success-design.jpg").into(holder.binding.completeImageUrlImg);
        } else if (task.noOfPomodoros > task.expectedPomodoro) {
            holder.binding.countDownBar.setRating(task.expectedPomodoro);
            holder.binding.extraCountDownBar.setVisibility(View.VISIBLE);
            holder.binding.extraCountDownBar.setNumStars(task.noOfPomodoros - task.expectedPomodoro);
            holder.binding.extraCountDownBar.setRating(task.noOfPomodoros - task.expectedPomodoro);
            Picasso.get().load("https://images-cdn.ubuy.co.in/63586d8fb67f9947294667ce-tomato-kitchen-timer-cooking-reminder.jpg").into(holder.binding.completeImageUrlImg);
            //Picasso.get().load("https://i.huffpost.com/gen/2186196/images/o-SECRETS-OF-SUCCESS-facebook.jpg").into(holder.binding.completeImageUrlImg);
        } else {
            holder.binding.countDownBar.setRating(task.noOfPomodoros);
            holder.binding.extraCountDownBar.setVisibility(View.GONE);
            Picasso.get().load("https://st4.depositphotos.com/14768666/20580/v/450/depositphotos_205805970-stock-illustration-tomato-timer-mockup-realistic-style.jpg").into(holder.binding.completeImageUrlImg);
           // Picasso.get().load("https://i.pinimg.com/originals/64/6e/41/646e41b261004f0605d63b6ef44b0be6.png").into(holder.binding.completeImageUrlImg);
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
