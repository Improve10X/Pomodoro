package com.improve10x.pomodoro.addedittask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
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
        handleStartBtn(task);
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

    private void handleStartBtn(Task task) {
        binding.startBtn.setOnClickListener(view -> {
            binding.addTaskTxt.getText().toString();
            Intent intent = new Intent(this, PomodoroActivity.class);
            //intent.putExtra(Constants.KEY_Task, task);
            Toast.makeText(this, "Successfully added task", Toast.LENGTH_SHORT).show();
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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Task task = new Task();
        task.id = db.collection("/users/" + user.getUid() + "/tasks").document().getId();
        task.title = title;
        task.status = "pending";
        task.expectedPomodoro = expectedPomodoro;

       db.collection("/users/" + user.getUid() + "/tasks")
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