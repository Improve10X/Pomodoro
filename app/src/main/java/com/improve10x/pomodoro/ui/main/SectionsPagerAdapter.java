package com.improve10x.pomodoro.ui.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.improve10x.pomodoro.completedfragment.CompletedFragment;
import com.improve10x.pomodoro.todofragment.TodoFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStateAdapter {

    private static final String[] TAB_TITLES = new String[]{"Todo", "Completed"};

    public SectionsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new TodoFragment();
        } else {
            return new CompletedFragment();
        }
    }


    @Override
    public int getItemCount() {
        return TAB_TITLES.length;
    }
}