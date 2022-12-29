package com.example.pomodoro;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pomodoro.databinding.FragmentTodoBinding;

import java.util.ArrayList;


public class TodoFragment extends Fragment {
    private ArrayList<Task> taskItems;
    private FragmentTodoBinding binding;
    private TaskItemsAdapter taskItemsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTodoBinding.inflate(getLayoutInflater());
        todoData();
        toDoRv();
        return binding.getRoot();
    }

    private void toDoRv() {
        binding.todoRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        taskItemsAdapter = new TaskItemsAdapter();
        taskItemsAdapter.setTaskItems(taskItems);
        binding.todoRv.setAdapter(taskItemsAdapter);
    }

    private void todoData() {
        taskItems = new ArrayList<>();
        Task task = new Task();
        task.taskList = "Good MorningGood MorningGood MorningGood MorningGood MorningGood MorningGood Morning";
        taskItems.add(task);
        taskItems.add(task);
        taskItems.add(task);
    }
}