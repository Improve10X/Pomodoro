package com.improve10x.pomodoro;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.improve10x.pomodoro.databinding.ActivityUserLoginBinding;
import com.improve10x.pomodoro.dialogfragment.PomodoroActivityActionListener;
import com.improve10x.pomodoro.home.PomodoroActivity;

public class UserLoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private ActivityUserLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth = FirebaseAuth.getInstance();
        handleLoginBtn();
        handleGuestBtn();
    }



    private void handleGuestBtn() {
        binding.guestModeBtn.setOnClickListener(v -> {
            firebaseAuth.signInAnonymously()
                    .addOnCompleteListener(task -> {
                   Intent intent = new Intent(this,PomodoroActivity.class);
                           startActivity(intent);

        })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
                    });
        });
    }

    private void handleLoginBtn() {
        binding.loginBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}