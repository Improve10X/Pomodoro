package com.improve10x.pomodoro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ToDoServices {
    @GET("pomodorotodo")
    Call<List<Task>> fetchTasks();
}
