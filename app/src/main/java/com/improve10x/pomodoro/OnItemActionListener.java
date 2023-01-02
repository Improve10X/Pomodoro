package com.improve10x.pomodoro;

import com.improve10x.pomodoro.fragment.Task;

public interface OnItemActionListener {

    void onLongClicked(Task task);

    void onEdit(Task task);

    void onDelete(String id);
}
