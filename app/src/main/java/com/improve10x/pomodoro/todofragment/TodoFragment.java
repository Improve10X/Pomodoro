package com.improve10x.pomodoro.todofragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.improve10x.pomodoro.Constants;
import com.improve10x.pomodoro.addedittask.CreateTaskActivity;
import com.improve10x.pomodoro.addedittask.EditDialogFragment;
import com.improve10x.pomodoro.addedittask.OnItemActionListener;
import com.improve10x.pomodoro.base.BaseFragment;
import com.improve10x.pomodoro.databinding.FragmentTodoBinding;
import com.improve10x.pomodoro.addedittask.Task;
import com.improve10x.pomodoro.home.PomodoroActivity;

import java.util.ArrayList;
import java.util.List;


public class TodoFragment extends BaseFragment implements ActionListener {

    private ArrayList<Task> taskItems = new ArrayList<>();
    private FragmentTodoBinding binding;
    private TaskItemsAdapter taskItemsAdapter;

    private Task task;
    private ActionListener actionListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTodoBinding.inflate(getLayoutInflater());
        toDoRv();
      //  onRefreshDeleted();
        handleAddFab();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchData();
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
            public void onItemClick(Task task) {
                Toast.makeText(getActivity(), "777777", Toast.LENGTH_SHORT).show();
                setTask(task);
            }

            @Override
            public void onChecked(Task task) {
                Toast.makeText(getActivity(), "Checked Successfully", Toast.LENGTH_SHORT).show();
                onCheck(task);
            }
        });

        taskItemsAdapter.setTaskItems(taskItems);
        taskItemsAdapter.setUpActionListener(actionListener);
        binding.todoRv.setAdapter(taskItemsAdapter);
        
    }
   /* private void onRefreshDeleted() {
        actionListener = new ActionListener() {
            @Override
            public void onRefresh(Task task) {
                fetchData();
            }
        };
    }*/

    private void handleAddFab() {
        binding.addFab.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), CreateTaskActivity.class);
            startActivity(intent);
        });
    }

    private void fetchData() {
        showProgressBar();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("/users/" + user.getUid() + "/tasks")
                .whereEqualTo("status", "pending")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            hideProgressBar();
                           List<Task> tasks = task.getResult().toObjects(Task.class);
                           taskItemsAdapter.setTaskItems(tasks);
                        } else {
                            Toast.makeText(getContext(), "Failed to load data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void setTask(Task task) {
            Intent intent = new Intent(getActivity(), PomodoroActivity.class);
            intent.putExtra(Constants.KEY_TASK, task);
            startActivity(intent);
    }

    private void onLongClick(Task task) {
      EditDialogFragment editDialogFragment = new EditDialogFragment();
      Bundle bundle = new Bundle();
      bundle.putSerializable(Constants.KEY_TASK, task);
      editDialogFragment.setArguments(bundle);
      editDialogFragment.show(getActivity().getSupportFragmentManager(), this.getClass().getSimpleName());
    }

    private void onCheck(Task task) {
        showProgressBar();
       task.status = "Completed";
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("/users/" + user.getUid() + "/tasks").document(task.id)
                .set(task)
                .addOnSuccessListener(unused -> {
                    hideProgressBar();
                    Toast.makeText(getActivity(), "Successfully Updated", Toast.LENGTH_SHORT).show();
                    fetchData();
                })
                .addOnFailureListener(e -> {
                    hideProgressBar();
                    Toast.makeText(getActivity(), "Failed to update", Toast.LENGTH_SHORT).show();

                });
    }

    private void showProgressBar() {
        binding.progress.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        binding.progress.setVisibility(View.GONE);
    }

    @Override
    public void onRefresh(Task task) {
        actionListener = new TodoFragment();
        fetchData();
    }
}
