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
import com.improve10x.pomodoro.dialogfragment.MotivationalDialogueFragment;
import com.improve10x.pomodoro.dialogfragment.PomodoroActivityActionListener;
import com.improve10x.pomodoro.Constants;
import com.improve10x.pomodoro.addedittask.CreateTaskActivity;
import com.improve10x.pomodoro.SettingsActivity;
import com.improve10x.pomodoro.dialogfragment.SuccessDialogFragment;
import com.improve10x.pomodoro.databinding.ActivityPomodoroBinding;
import com.improve10x.pomodoro.addedittask.Task;
import com.improve10x.pomodoro.addedittask.TaskActivity;
import com.improve10x.pomodoro.utils.DateUtils;

public class PomodoroActivity extends AppCompatActivity implements PomodoroActivityActionListener {

    protected ActivityPomodoroBinding binding;
    private CountDownTimer timer;
    private Task task;
    private String timerType = "Pomodoro";
    private static final int RESET_BREAK_TIME_IN_MILLIS = 25 * 60 * 1000;
    private static final int START_LONG_BREAK_TIME_IN_MILLIS = 20 * 60 * 1000;
    private static final int START_SHORT_BREAK_TIME_IN_MILLIS = 5 * 60 * 1000;
    //private static final int START_TIMER_IN_MILLIS = 1 * 60 * 1000;


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
        updateTask();
    }

    private void showData() {
     binding.taskNameTxt.setText(task.title);
   }

   private void startShortBreakInfo() {
       String remainingTime = DateUtils.getFormattedTime(START_SHORT_BREAK_TIME_IN_MILLIS);
       binding.progressbar.setMaxValue(START_SHORT_BREAK_TIME_IN_MILLIS);
       binding.progressbar.setValue(START_SHORT_BREAK_TIME_IN_MILLIS);
       binding.timeTxt.setText(remainingTime);
       binding.startBtn.setVisibility(View.VISIBLE);
       binding.cancelBtn.setVisibility(View.GONE);
       timerType = "Short Break";
   }

   private void resetLongBreakInfo() {
       String remainingTime = DateUtils.getFormattedTime(START_LONG_BREAK_TIME_IN_MILLIS);
       binding.progressbar.setMaxValue(START_LONG_BREAK_TIME_IN_MILLIS);
       binding.progressbar.setValue(START_LONG_BREAK_TIME_IN_MILLIS);
       binding.timeTxt.setText(remainingTime);
       binding.startBtn.setVisibility(View.VISIBLE);
       binding.cancelBtn.setVisibility(View.GONE);
       timerType = "Long Break";
   }

    private void resetBreakInfo() {
        String remainingTime = DateUtils.getFormattedTime(RESET_BREAK_TIME_IN_MILLIS);
        binding.progressbar.setMaxValue(RESET_BREAK_TIME_IN_MILLIS);
        binding.progressbar.setValue(RESET_BREAK_TIME_IN_MILLIS);
        binding.timeTxt.setText(remainingTime);
        binding.startBtn.setVisibility(View.VISIBLE);
        binding.cancelBtn.setVisibility(View.GONE);
        timerType = "Reset Break";
    }

    private void startTimer(int millis) {
        timer = new CountDownTimer(millis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.progressbar.setValue((int)millisUntilFinished);
                String remainingTime = DateUtils.getFormattedTime(millisUntilFinished);
                binding.timeTxt.setText(remainingTime);
                timerType = "Start Timer";
            }

            @Override
            public void onFinish() {
                binding.progressbar.setValue(0);
                updatePomodoro(task);
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
            startTimer(10000);
        });
    }

    private void handleCancel() {
        binding.cancelBtn.setOnClickListener(v -> {
            timer.cancel();
            resetBreakInfo();
        });
    }

    private void showMotivationalDialog() {
        MotivationalDialogueFragment motivationalDialogueFragment = new MotivationalDialogueFragment();
       motivationalDialogueFragment.show(this.getSupportFragmentManager(),this.getClass().getSimpleName());
   }

    private void showSuccessDialog() {
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
                        //motivationalDialog();
                         showSuccessDialog();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(PomodoroActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void skipBreak() {
        Toast.makeText(this, "skip Break", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startShortBreak() {
        Toast.makeText(this, "Start short Break", Toast.LENGTH_SHORT).show();
        resetBreakInfo();
        startShortBreakInfo();
    }

    @Override
    public void startLongBreak() {
        Toast.makeText(this, "start long Break", Toast.LENGTH_SHORT).show();
        resetLongBreakInfo();
    }
}