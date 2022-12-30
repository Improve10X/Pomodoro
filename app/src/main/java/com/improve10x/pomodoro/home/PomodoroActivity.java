package com.improve10x.pomodoro.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.improve10x.pomodoro.EditDialogFragment;
import com.improve10x.pomodoro.SettingsActivity;
import com.improve10x.pomodoro.databinding.ActivityPomodoroBinding;
import com.improve10x.pomodoro.fragment.TaskActivity;

public class PomodoroActivity extends AppCompatActivity {

    protected ActivityPomodoroBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPomodoroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        handleTaskList();
        handleSettings();
        //EditDialogFragment fragment = new EditDialogFragment();
        //fragment.show(getSupportFragmentManager(), this.getClass().getSimpleName());
    }

    private void handleTaskList() {
        binding.tasklistBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, TaskActivity.class);
            startActivity(intent);
        });
    }

    private void handleSettings() {
        binding.settingsIb.setOnClickListener(view -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        });
    }
}