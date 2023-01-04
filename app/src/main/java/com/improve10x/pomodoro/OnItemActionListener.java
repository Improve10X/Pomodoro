package com.improve10x.pomodoro;

import com.improve10x.pomodoro.fragment.Task;

public interface OnItemActionListener {

    void onLongClicked(Task task);

    void onItemClick(Task task);

    void onChecked(Task task);

}
