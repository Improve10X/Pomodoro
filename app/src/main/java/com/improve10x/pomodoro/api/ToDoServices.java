package com.improve10x.pomodoro.api;

import com.improve10x.pomodoro.addedittask.Task;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ToDoServices {
    @GET("pomodorotodo")
    Call<List<Task>> fetchTasks();
}
