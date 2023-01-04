package com.improve10x.pomodoro.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.improve10x.MotivationalDialogueFragment;
import com.improve10x.pomodoro.Constants;
import com.improve10x.pomodoro.CreateTaskActivity;
import com.improve10x.pomodoro.SettingsActivity;
import com.improve10x.pomodoro.SuccessDialogFragment;
import com.improve10x.pomodoro.databinding.ActivityPomodoroBinding;
import com.improve10x.pomodoro.fragment.Task;
import com.improve10x.pomodoro.fragment.TaskActivity;
import com.improve10x.pomodoro.utils.DateUtils;

public class PomodoroActivity extends AppCompatActivity {

    protected ActivityPomodoroBinding binding;
    private CountDownTimer timer;
    private Task task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPomodoroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
       if (getIntent().hasExtra(Constants.KEY_TASK)) {
            task = (Task) getIntent().getSerializableExtra(Constants.KEY_TASK);
            showData();
       }
        handleTaskList();
        handleSettings();
        handleStart();
        handleCancel();
        resetBreakInfo();
        resetLongBreakInfo();
        updateTask();
    }

    private void showData() {
     binding.taskNameTxt.setText(task.title);
   }

   private void resetLongBreakInfo() {
       int timeInMillis = 20 * 60 * 1000;
       String remainingTime = DateUtils.getFormattedTime(timeInMillis);
       binding.progressbar.setMaxValue(timeInMillis);
       binding.progressbar.setValue(timeInMillis);
       binding.timeTxt.setText(remainingTime);
       binding.startBtn.setVisibility(View.VISIBLE);
       binding.cancelBtn.setVisibility(View.GONE);
   }

    private void resetBreakInfo() {
        int timeInMillis = 5 * 10 * 1000;
        String remainingTime = DateUtils.getFormattedTime(timeInMillis);
        binding.progressbar.setMaxValue(timeInMillis);
        binding.progressbar.setValue(timeInMillis);
        binding.timeTxt.setText(remainingTime);
        binding.startBtn.setVisibility(View.VISIBLE);
        binding.cancelBtn.setVisibility(View.GONE);
    }

    private void startTaskTimer() {
        long timeInMillis = 1 * 10 * 1000;
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
                updatePomodoro(task);
                successDialog();
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

    private void motivationalDialog() {
        MotivationalDialogueFragment motivationalDialogueFragment = new MotivationalDialogueFragment();
       motivationalDialogueFragment.show(this.getSupportFragmentManager(),this.getClass().getSimpleName());
   }

    private void successDialog() {
        SuccessDialogFragment successDialogFragment = new SuccessDialogFragment();
        successDialogFragment.show(this.getSupportFragmentManager(),this.getClass().getSimpleName());
    }

   private void updateTask() {
        binding.taskNameTxt.setOnClickListener(view -> {
            binding.taskNameTxt.getText().toString();
            Intent intent = new Intent(this, CreateTaskActivity.class);
            startActivity(intent);
        });
   }

    private void updatePomodoro(Task task) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        task.noOfPomodoros = task.noOfPomodoros + 1;
        db.collection("tasks").document(task.id)
                .set(task)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(PomodoroActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                        motivationalDialog();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PomodoroActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}