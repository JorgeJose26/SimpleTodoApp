package com.example.todoapp.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TodoDao {
    @Query("SELECT * FROM Todo")
    List<Todo> getAll();
    @Query("SELECT * FROM Todo WHERE Todo.completed = 0")
    List<Todo> getAllUncompleted();
    @Query("SELECT * FROM Todo WHERE Todo.completed = 1")
    List<Todo> getAllCompleted();
    @Insert
    long insertAll(Todo todo);
    @Update
    void update(Todo todd);
    @Delete
    void delete(Todo todo);
}
