package com.improve10x.pomodoro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.improve10x.pomodoro.databinding.ActivityEditTaskBinding;
import com.improve10x.pomodoro.fragment.Task;
import com.improve10x.pomodoro.fragment.TaskActivity;

public class EditTaskActivity extends AppCompatActivity {

    private ActivityEditTaskBinding binding;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Edit Task");
        if (getIntent().hasExtra(Constants.KEY_TASK)) {
            task = (Task) getIntent().getSerializableExtra(Constants.KEY_TASK);
            showData();
            editTask(task.id);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    private void showData() {
        binding.editTaskTxt.setText(task.title);
        binding.pomodoroEditSb.getProgress();
    }

    protected void editTask(String id) {
        binding.saveBtn.setOnClickListener(view -> {
            Intent intent = new Intent(this, TaskActivity.class);
            task.title = binding.editTaskTxt.getText().toString();
            task.editPomodoros = binding.pomodoroEditSb.getProgress();
            startActivity(intent);
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("tasks")
                    .document(id)
                    .set(task)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(EditTaskActivity.this, "Successfully edited", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
        });
    }
}
