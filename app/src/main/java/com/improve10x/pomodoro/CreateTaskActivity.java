package com.improve10x.pomodoro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return false;
        } else {
            return super.onOptionsItemSelected(item);
        }
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
            int expectedPomodoro = binding.addSeekbarSb.getProgress();
            addTask(title, expectedPomodoro);
            Intent intent = new Intent(this, TaskActivity.class);
            startActivity(intent);
        });
    }

    private void addTask(String title, int expectedPomodoro) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Task task = new Task();
        task.id = db.collection("tasks").document().getId();
        task.title = title;
        task.expectedPomodoro = expectedPomodoro;

        db.collection("tasks")
                .document(task.id)
                .set(task)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CreateTaskActivity.this, "success", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}