package com.improve10x.pomodoro.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.improve10x.pomodoro.SettingsActivity;
import com.improve10x.pomodoro.databinding.ActivityPomodoroBinding;
import com.improve10x.pomodoro.fragment.TaskActivity;
import com.improve10x.pomodoro.utils.DateUtils;

public class PomodoroActivity extends AppCompatActivity {

    protected ActivityPomodoroBinding binding;
    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPomodoroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        handleTaskList();
        handleSettings();
        handleStart();
        handleCancel();
        resetBreakInfo();
    }

    private void resetBreakInfo() {
        long timeInMillis = 25 * 60 * 1000;
        String remainingTime = DateUtils.getFormattedTime(timeInMillis);
        binding.progressbar.setValue((int)timeInMillis);
        binding.timeTxt.setText(remainingTime);
        binding.startBtn.setVisibility(View.VISIBLE);
        binding.cancelBtn.setVisibility(View.GONE);
    }

    private void startTaskTimer() {
        long timeInMillis = 25 * 60 * 1000;
        timer = new CountDownTimer(timeInMillis ,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.progressbar.setValue((int)millisUntilFinished);
                String remainingTime = DateUtils.getFormattedTime(millisUntilFinished);
                binding.timeTxt.setText(remainingTime);
            }

            @Override
            public void onFinish() {
                binding.progressbar.setValue(0);
            }
        }.start();
    }

    private void handleTaskList() {
        binding.taskListBtn.setOnClickListener(view -> {
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

    private void handleStart() {
        binding.startBtn.setOnClickListener(v -> {
            binding.cancelBtn.setVisibility(View.VISIBLE);
            binding.startBtn.setVisibility(View.GONE);
            startTaskTimer();
        });
    }

    private void handleCancel() {
        binding.cancelBtn.setOnClickListener(v -> {
            timer.cancel();
            resetBreakInfo();
        });
    }
}