package com.improve10x.pomodoro.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.improve10x.pomodoro.databinding.FragmentCompletedBinding;
import com.improve10x.pomodoro.fragment.CompletedTasksAdapter;
import com.improve10x.pomodoro.fragment.Task;

import java.util.ArrayList;

public class CompletedFragment extends Fragment {
    
    
    private ArrayList<Task> tasks = new ArrayList<>();
    private FragmentCompletedBinding binding;
    private CompletedTasksAdapter completedTasksAdapter;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCompletedBinding.inflate(getLayoutInflater());
        setupData();
        completedRv();
        return binding.getRoot();

    }

    private void setupData() {
        Task task = new Task();
        task.title = "hello ndanabafadkadafjdakdnafamdajdnasdmaknajdakjbasndan world";
        tasks.add(task);
    }

    private void completedRv() {
        binding.completedRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        completedTasksAdapter = new CompletedTasksAdapter();
        completedTasksAdapter.setData(tasks);
        binding.completedRv.setAdapter(completedTasksAdapter);
    }

}