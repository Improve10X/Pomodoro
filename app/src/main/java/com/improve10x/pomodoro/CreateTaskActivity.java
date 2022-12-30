package com.improve10x.pomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.improve10x.pomodoro.databinding.ActivityCreateTaskBinding;
import com.improve10x.pomodoro.fragment.Task;
import com.improve10x.pomodoro.fragment.TaskActivity;
import com.improve10x.pomodoro.home.PomodoroActivity;

public class CreateTaskActivity extends AppCompatActivity {

    private ActivityCreateTaskBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Create Task");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        handleSaveBtn();
        handleStartBtn();
    }

    private void handleStartBtn() {
        binding.startBtn.setOnClickListener(view -> {
            binding.addTaskTxt.getText().toString();

            Intent intent = new Intent(this, PomodoroActivity.class);
            startActivity(intent);
        });
    }

    private void handleSaveBtn() {
        binding.saveBtn.setOnClickListener(view -> {
           String title = binding.addTaskTxt.getText().toString();
           addTask(title);
            Intent intent = new Intent(this, TaskActivity.class);
            startActivity(intent);
        });
    }

    private void addTask(String title) {
        Task task = new Task();
        task.title = title;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("tasks")
                .add(task)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(CreateTaskActivity.this, "success", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}