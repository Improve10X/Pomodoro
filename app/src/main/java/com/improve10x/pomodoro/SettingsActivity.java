package com.improve10x.pomodoro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.improve10x.pomodoro.addedittask.CreateTaskActivity;
import com.improve10x.pomodoro.databinding.ActivitySettingsBinding;
import com.improve10x.pomodoro.home.PomodoroActivity;

public class SettingsActivity extends AppCompatActivity {
    private ActivitySettingsBinding binding;
    private SettingsItem settingsItem;
    private int ringingVolume;
    private int tickingVolume;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        displayProgressBar();
        handleSave();
        fetchData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == R.id.logout) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    });
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void handleSoundVolumes(int ringingVolume, int tickingVolume) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        SettingsItem settingsItem = new SettingsItem();
        db.collection("/users/" + user.getUid() + "/settings").document().getId();
        settingsItem.ringingVolume = ringingVolume;
        settingsItem.tickingVolume = tickingVolume;

        db.collection("/users/" + user.getUid() + "/settings")
                .document("doc")
                .set(settingsItem)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(SettingsActivity.this, "success", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private void handleSave() {
        binding.saveBtn.setOnClickListener(view -> {
            ringingVolume = binding.ringingVolumeSb.getProgress();
            tickingVolume = binding.tickingVolumeSb.getProgress();
            handleSoundVolumes(ringingVolume, tickingVolume);
        });
    }

    private void fetchData() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        db.collection("/users/" + user.getUid() + "/settings")
                .document("doc")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Toast.makeText(SettingsActivity.this, "Save", Toast.LENGTH_SHORT).show();
                        hideProgressBar();
                        SettingsItem settingsItem = documentSnapshot.toObject(SettingsItem.class);
                        binding.tickingVolumeSb.setProgress(settingsItem.tickingVolume);
                        binding.ringingVolumeSb.setProgress(settingsItem.ringingVolume);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressBar();

                    }
                });
    }

    private void displayProgressBar() {
        binding.progress.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        binding.progress.setVisibility(View.GONE);
    }
}