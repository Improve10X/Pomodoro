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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private static final int START_LONG_BREAK_TIME_IN_MILLIS = 1 * 5 * 1000;
    private static final int START_SHORT_BREAK_TIME_IN_MILLIS = 1 * 2 * 1000;
    private static final int POMODORO_TIMER_IN_MILLIS = 1 * 3 * 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPomodoroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        if (getIntent().hasExtra(Constants.KEY_TASK)) {
            task = (Task) getIntent().getSerializableExtra(Constants.KEY_TASK);
            showData();
        } else {
            task = new Task();
        }
        handleTaskList();
        handleSettings();
        handleStart();
        handleCancel();
        resetPomodoroInfo();
        updateTask();
    }

    private void showData() {
        binding.taskNameTxt.setText(task.title);
        binding.countDownBar.setNumStars(task.expectedPomodoro);
        if (task.noOfPomodoros > task.expectedPomodoro) {
            binding.countDownBar.setRating(task.expectedPomodoro);
            binding.countDownExtraBar.setVisibility(View.VISIBLE);
            binding.countDownExtraBar.setNumStars(task.noOfPomodoros - task.expectedPomodoro);
            binding.countDownExtraBar.setRating(task.noOfPomodoros - task.expectedPomodoro);
        } else {
            binding.countDownBar.setRating(task.noOfPomodoros);
            binding.countDownExtraBar.setVisibility(View.GONE);
        }
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

    private void startLongBreakInfo() {
        String remainingTime = DateUtils.getFormattedTime(START_LONG_BREAK_TIME_IN_MILLIS);
        binding.progressbar.setMaxValue(START_LONG_BREAK_TIME_IN_MILLIS);
        binding.progressbar.setValue(START_LONG_BREAK_TIME_IN_MILLIS);
        binding.timeTxt.setText(remainingTime);
        binding.startBtn.setVisibility(View.VISIBLE);
        binding.cancelBtn.setVisibility(View.GONE);
        timerType = "Long Break";
    }

    private void resetPomodoroInfo() {
        String remainingTime = DateUtils.getFormattedTime(POMODORO_TIMER_IN_MILLIS);
        binding.progressbar.setMaxValue(POMODORO_TIMER_IN_MILLIS);
        binding.progressbar.setValue(POMODORO_TIMER_IN_MILLIS);
        binding.timeTxt.setText(remainingTime);
        binding.startBtn.setVisibility(View.VISIBLE);
        binding.cancelBtn.setVisibility(View.GONE);
        timerType = "Pomodoro";
    }

    private void startTimer(int millis) {
        timer = new CountDownTimer(millis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                binding.progressbar.setValue((int) millisUntilFinished);
                String remainingTime = DateUtils.getFormattedTime(millisUntilFinished);
                binding.timeTxt.setText(remainingTime);
            }

            @Override
            public void onFinish() {
                binding.progressbar.setValue(0);
                if (timerType.equalsIgnoreCase("Pomodoro")) {
                    updatePomodoro(task);
                } else {
                    resetPomodoroInfo();
                }
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
            if (timerType.equalsIgnoreCase("Pomodoro")) {
                startTimer(POMODORO_TIMER_IN_MILLIS);
            } else if (timerType.equalsIgnoreCase("Short Break")) {
                startTimer(START_SHORT_BREAK_TIME_IN_MILLIS);
            } else {
                startTimer(START_LONG_BREAK_TIME_IN_MILLIS);
            }
        });
    }

    private void handleCancel() {
        binding.cancelBtn.setOnClickListener(v -> {
            timer.cancel();
            resetPomodoroInfo();
        });
    }

    private void showMotivationalDialog() {
        MotivationalDialogueFragment motivationalDialogueFragment = new MotivationalDialogueFragment();
        motivationalDialogueFragment.show(this.getSupportFragmentManager(), this.getClass().getSimpleName());
    }

    private void showSuccessDialog() {
        SuccessDialogFragment successDialogFragment = new SuccessDialogFragment();
        successDialogFragment.show(this.getSupportFragmentManager(), this.getClass().getSimpleName());
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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        task.noOfPomodoros = task.noOfPomodoros + 1;
        db.collection("/users/" + user.getUid() + "/tasks").document(task.id)
                .set(task)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(PomodoroActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
                        showData();
                        if (task.noOfPomodoros == 1) {
                            showMotivationalDialog();
                        } else if (task.noOfPomodoros == 2) {
                            showMotivationalDialog();
                        } else if (task.noOfPomodoros == 3) {
                            showMotivationalDialog();
                        } else {
                            showSuccessDialog();
                        }
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
        resetPomodoroInfo();
    }

    @Override
    public void startShortBreak() {
        Toast.makeText(this, "Start short Break", Toast.LENGTH_SHORT).show();
        startShortBreakInfo();
    }

    @Override
    public void startLongBreak() {
        Toast.makeText(this, "start long Break", Toast.LENGTH_SHORT).show();
        startLongBreakInfo();
    }
}