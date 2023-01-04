package com.improve10x.pomodoro;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.improve10x.pomodoro.databinding.TodoItemBinding;
import com.improve10x.pomodoro.fragment.Task;

import java.util.List;

public class TaskItemsAdapter extends RecyclerView.Adapter<TaskViewHolder> {

    private List<Task> taskItems;
    private OnItemActionListener onItemActionListener;

    void setTaskItems(List<Task> taskItems) {
        this.taskItems = taskItems;
        notifyDataSetChanged();
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
    }

    @Override
    public int getItemCount() {
        return taskItems.size();
    }
}
