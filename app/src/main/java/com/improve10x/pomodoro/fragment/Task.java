package com.improve10x.pomodoro.fragment;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Task implements Serializable {

    public String id;
    public String title;
    public int expectedPomodoro;
}
