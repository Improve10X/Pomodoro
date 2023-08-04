package com.improve10x.pomodoro.todofragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.pomodoro.addedittask.OnItemActionListener;
import com.improve10x.pomodoro.databinding.TodoItemBinding;
import com.improve10x.pomodoro.addedittask.Task;

import java.util.List;

public class TaskItemsAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private List<Task> taskItems;
    private OnItemActionListener onItemActionListener;

    private ActionListener actionListener;

    void setTaskItems(List<Task> taskItems) {
        this.taskItems = taskItems;
        notifyDataSetChanged();
    }

    void setUpActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }


    void setOnItemClickListener(OnItemActionListener onItemActionListener) {
        this.onItemActionListener = onItemActionListener;
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
        holder.binding.todoListTxt.setText(task.title);

        holder.binding.getRoot().setOnLongClickListener(view -> {
            onItemActionListener.onLongClicked(task);
            return true;
        });

        holder.binding.getRoot().setOnClickListener(view -> {
            onItemActionListener.onItemClick(task);
        });
        holder.binding.checkboxCb.setOnCheckedChangeListener((compoundButton, b) -> {
            onItemActionListener.onChecked(task);
        });
        holder.binding.countBar.setNumStars(task.expectedPomodoro);
        if (task.noOfPomodoros > task.expectedPomodoro) {
            holder.binding.countBar.setRating(task.expectedPomodoro);
            holder.binding.countExtraBar.setVisibility(View.VISIBLE);
            holder.binding.countExtraBar.setNumStars(task.noOfPomodoros - task.expectedPomodoro);
            holder.binding.countExtraBar.setRating(task.noOfPomodoros - task.expectedPomodoro);
        } else {
            holder.binding.countBar.setRating(task.noOfPomodoros);
            holder.binding.countExtraBar.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return taskItems.size();
    }
}
