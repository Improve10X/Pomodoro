package com.improve10x.pomodoro.completedfragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.improve10x.pomodoro.addedittask.Task;
import com.improve10x.pomodoro.databinding.FragmentCompletedBinding;

import java.util.ArrayList;
import java.util.List;

public class CompletedFragment extends Fragment {

    private Task task;
    private ArrayList<Task> tasks = new ArrayList<>();
    private FragmentCompletedBinding binding;
    private CompletedTasksAdapter completedTasksAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCompletedBinding.inflate(getLayoutInflater());
        getData();
        completedRv();
        return binding.getRoot();
    }

    private void completedRv() {
        binding.completedRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        completedTasksAdapter = new CompletedTasksAdapter();
        completedTasksAdapter.setData(tasks);
        binding.completedRv.setAdapter(completedTasksAdapter);
    }

    private void getData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("/users/" + user.getUid() + "/tasks")
                .whereEqualTo("status", "Completed")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Task> tasks = task.getResult().toObjects(Task.class);
                            completedTasksAdapter.setData(tasks);
                        } else {
                            Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}