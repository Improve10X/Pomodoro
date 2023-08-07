package com.improve10x.pomodoro.addedittask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.improve10x.pomodoro.Constants;
import com.improve10x.pomodoro.databinding.ActivityCreateTaskBinding;
import com.improve10x.pomodoro.home.PomodoroActivity;

public class CreateTaskActivity extends AppCompatActivity {

    private ActivityCreateTaskBinding binding;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Create Task");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        handleSaveBtn();
        handleStartBtn();
        showProgress();
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
            String taskName = binding.addTaskTxt.getText().toString();
            int expectedPomodoro = binding.addSeekbarSb.getProgress();
            startAddTask(taskName, expectedPomodoro);
            Intent intent = new Intent(this, PomodoroActivity.class);
            startActivity(intent);

        });
    }

    private void handleSaveBtn() {
        binding.saveBtn.setOnClickListener(view -> {
            String title = binding.addTaskTxt.getText().toString();
            int expectedPomodoro = binding.addSeekbarSb.getProgress();
            saveAddTask(title, expectedPomodoro);
            finish();
        });
    }

    private void saveAddTask(String title, int expectedPomodoro) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Task task = new Task();
        task.id = db.collection("/users/" + user.getUid() + "/tasks").document().getId();
        task.title = title;
        task.status = "pending";
        task.expectedPomodoro = expectedPomodoro;
        task.noOfPomodoros = 0;

       db.collection("/users/" + user.getUid() + "/tasks")
               .document(task.id)
                .set(task)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CreateTaskActivity.this, "saved successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CreateTaskActivity.this , TaskActivity.class);
                        intent.putExtra(Constants.KEY_TASK, task);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    private void startAddTask(String title, int expectedPomodoro) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Task task = new Task();
        task.id = db.collection("/users/" + user.getUid() + "/tasks").document().getId();
        task.title = title;
        task.status = "pending";
        task.expectedPomodoro = expectedPomodoro;
        task.noOfPomodoros = 0;

        db.collection("/users/" + user.getUid() + "/tasks")
                .document(task.id)
                .set(task)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(CreateTaskActivity.this, "started successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CreateTaskActivity.this , PomodoroActivity.class);
                        intent.putExtra(Constants.KEY_TASK, task);
                        startActivity(intent);
                        finish();
                    }
                });
    }

    private void showProgress() {
        binding.addSeekbarSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                String message = "No of Pomodoros: " + progress;
                Toast.makeText(CreateTaskActivity.this, message, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}