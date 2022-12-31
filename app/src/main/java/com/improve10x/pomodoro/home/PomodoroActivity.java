package com.improve10x.pomodoro.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;

import com.improve10x.pomodoro.R;
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
        setupProgressBar();
    }

    private void setupProgressBar() {
        long totalTime = 5 * 60 * 100;
        new CountDownTimer(totalTime ,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int percentage = (int) (millisUntilFinished * 100 / totalTime);
                binding.progressbar.setValue((int)millisUntilFinished);
                binding.timeTxt.setText((millisUntilFinished/1000) + "");
            }

            @Override
            public void onFinish() {
                binding.progressbar.setValue(0);
            }
        }.start();
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