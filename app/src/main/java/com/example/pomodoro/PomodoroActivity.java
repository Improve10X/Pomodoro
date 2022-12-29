package com.example.pomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.pomodoro.databinding.ActivityPomodoroBinding;

public class PomodoroActivity extends AppCompatActivity {

    protected ActivityPomodoroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPomodoroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        handleTaskList();
    }

    private void handleTaskList() {
        binding.tasklistBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, TaskListActivity.class);
            startActivity(intent);
        });

    }
}