package com.improve10x.pomodoro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.improve10x.pomodoro.databinding.ActivityEditTaskBinding;

public class EditTaskActivity extends AppCompatActivity {

    private ActivityEditTaskBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Edit Task");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void handleSave(){
        binding.saveBtn.setOnClickListener(view -> {
            String editTask = binding.editTaskTxt.getText().toString();
        });
    }
}