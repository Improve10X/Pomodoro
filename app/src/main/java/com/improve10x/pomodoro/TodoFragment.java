package com.improve10x.pomodoro;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.improve10x.pomodoro.base.BaseFragment;
import com.improve10x.pomodoro.databinding.FragmentTodoBinding;
import com.improve10x.pomodoro.fragment.Task;

import java.util.ArrayList;
import java.util.List;


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
        taskItemsAdapter.setOnItemClickListener(new OnItemActionListener() {
            @Override
            public void onLongClicked(Task task) {
                Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
                onLongClick(task);
            }

            @Override
            public void onEdit(Task task) {

            }

            @Override
            public void onDelete(String id) {

            }
        });
        taskItemsAdapter.setTaskItems(taskItems);
        binding.todoRv.setAdapter(taskItemsAdapter);
    }

    private void fetchData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("tasks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                           List<Task> tasks = task.getResult().toObjects(Task.class);
                           taskItemsAdapter.setTaskItems(tasks);
                        } else {
                            Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void onLongClick(Task task) {
      EditDialogFragment editDialogFragment = new EditDialogFragment();
      //editDialogFragment.show();
        editDialogFragment.show(getActivity().getSupportFragmentManager(), this.getClass().getSimpleName());
    }
}