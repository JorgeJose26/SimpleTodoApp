package com.example.todoapp.fragments;

import android.content.Context;

import androidx.room.Room;

import com.example.todoapp.models.AppDatabase;
import com.example.todoapp.models.TodoDao;

public class TodoViewModel {
    private TodoDao todoDao;
    private static TodoViewModel instance = new TodoViewModel();

    public static TodoViewModel getInstance() {
        return instance;
    }

    private TodoViewModel() {


    }

    public void initializeDataBase(Context context) {
        AppDatabase db = Room.databaseBuilder(context,
                AppDatabase.class, "Tododatabase").build();
        todoDao = db.todoDao();

    }
    public TodoDao getTodoDao(){
        return todoDao;
    }
}

