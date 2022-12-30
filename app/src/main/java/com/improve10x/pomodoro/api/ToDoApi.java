package com.improve10x.pomodoro.api;

import com.improve10x.pomodoro.api.ToDoServices;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ToDoApi {

    public ToDoServices createTodoServices(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://crudcrud.com/api/479dd07f8c1d482e9219f7dcb48e25f4/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ToDoServices toDoServices = retrofit.create(ToDoServices.class);
        return toDoServices;
    }
}
