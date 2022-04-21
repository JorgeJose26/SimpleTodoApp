package com.example.todoapp.models;

public interface TodoSaveListener {
    void onTodoCompleted(Todo todo);
    void removeTodoCompleted(Todo todo);
}
