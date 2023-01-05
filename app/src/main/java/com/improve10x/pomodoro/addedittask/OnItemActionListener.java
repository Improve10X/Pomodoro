package com.improve10x.pomodoro.addedittask;

import com.improve10x.pomodoro.addedittask.Task;

public interface OnItemActionListener {

    void onLongClicked(Task task);

    void onItemClick(Task task);

    void onChecked(Task task);

}
