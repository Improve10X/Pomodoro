package com.improve10x.pomodoro.base;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.improve10x.pomodoro.api.ToDoApi;
import com.improve10x.pomodoro.api.ToDoServices;

public class BaseFragment extends Fragment {
    protected ToDoServices toDoServices;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTodoApi();
    }

    private void setupTodoApi() {
        ToDoApi toDoApi = new ToDoApi();
        toDoServices = toDoApi.createTodoServices();
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}
