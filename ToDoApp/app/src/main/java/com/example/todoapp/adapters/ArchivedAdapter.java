package com.example.todoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.models.Todo;
import com.example.todoapp.models.TodoSaveListener;

import java.util.ArrayList;
import java.util.List;

public class ArchivedAdapter extends RecyclerView.Adapter<ArchivedAdapter.MyViewHolder> {
    private List<Todo> todoList;
    private TodoSaveListener listener;
    private Context todoContext;
    public static final String ITEM_KEY = "item_key";

    public ArchivedAdapter(Context context, List<Todo> todoList, TodoSaveListener listener) {
        this.listener = listener;
        this.todoList = todoList;
        this.todoContext = context;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView archivedText;
        public View mView;

        public MyViewHolder(final View itemView) {
            super(itemView);
            archivedText = itemView.findViewById(R.id.textView);
            mView = itemView;
        }
    }


    @NonNull
    @Override
    public ArchivedAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.archvied_todo, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArchivedAdapter.MyViewHolder holder, int position) {
        Todo todo = todoList.get(position);
        holder.archivedText.setText(todo.getTitle());

    }

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public void addItem(Todo todo) {
        todoList.add(todo);
        notifyDataSetChanged();

    }
}
