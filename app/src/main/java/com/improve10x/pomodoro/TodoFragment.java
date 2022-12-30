package com.improve10x.pomodoro;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.improve10x.pomodoro.databinding.FragmentTodoBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TodoFragment extends BaseFragment {

    private ArrayList<Task> taskItems = new ArrayList<>();
    private FragmentTodoBinding binding;
    private TaskItemsAdapter taskItemsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTodoBinding.inflate(getLayoutInflater());
        fetchData();
        toDoRv();
        return binding.getRoot();
    }

    private void toDoRv() {
        binding.todoRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        taskItemsAdapter = new TaskItemsAdapter();
        taskItemsAdapter.setTaskItems(taskItems);
        binding.todoRv.setAdapter(taskItemsAdapter);
    }

    private void fetchData() {
        Call<List<Task>> call = toDoServices.fetchTasks();
        call.enqueue(new Callback<List<Task>>() {
            @Override
            public void onResponse(Call<List<Task>> call, Response<List<Task>> response) {
                List<Task> tasks = response.body();
                taskItemsAdapter.setTaskItems(tasks);
            }

            @Override
            public void onFailure(Call<List<Task>> call, Throwable t) {

            }
        });
    }
}