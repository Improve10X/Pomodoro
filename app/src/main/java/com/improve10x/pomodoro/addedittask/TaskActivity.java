package com.improve10x.pomodoro.addedittask;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.view.MenuItem;
import android.view.View;

import com.google.android.material.tabs.TabLayoutMediator;
import com.improve10x.pomodoro.databinding.ActivityTaskBinding;
import com.improve10x.pomodoro.ui.main.SectionsPagerAdapter;

public class TaskActivity extends AppCompatActivity {

    private ActivityTaskBinding binding;

    private SectionsPagerAdapter sectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpAdapter();
        setUpViewPager();
        connectTabsWithViewPager();
    }

    private void setUpAdapter() {
        sectionsPagerAdapter = new SectionsPagerAdapter(this);
    }

    private void setUpViewPager() {
        binding.viewPager.setAdapter(sectionsPagerAdapter);
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
    private void connectTabsWithViewPager() {
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(binding.tabs, binding.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 1:
                        tab.setText("COMPLETED");
                        break;
                    case 0:
                    default:
                        tab.setText("TODO");
                }
            }
        });
        tabLayoutMediator.attach();
    }
}