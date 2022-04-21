package com.example.todoapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.activites.MainActivity;
import com.example.todoapp.fragments.TodoDetailFragment;
import com.example.todoapp.fragments.TodoFragment;
import com.example.todoapp.models.Todo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder>{
    private List<Todo> todoList;
    private Context todoContext;
    private ItemClickListener clickListener;
    public static final String ITEM_KEY = "item_key";


    public TodoAdapter (Context context, List<Todo> todoList, ItemClickListener clickListener){
        this.todoList = todoList;
        this.todoContext = context;
        this.clickListener = clickListener;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView titleTxt;
        public View mView;
        public MyViewHolder(final View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.todoTitle);
            mView = itemView;
        }
    }
    @NonNull
    @Override
    public TodoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_items,parent,false);
        return new MyViewHolder(itemView);

    }
    @Override
    public void onBindViewHolder(@NonNull TodoAdapter.MyViewHolder holder, int position) {
        Todo todo = todoList.get(position);
        holder.titleTxt.setText(todo.getTitle());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(todoList.get(position));
            }
        });
    }
    @Override
    public int getItemCount() {
        return todoList.size();
    }
    public void addItem(Todo todo){
        todoList.add(todo);
        notifyDataSetChanged();

    }
    public void removeItem (Todo todo){
        todoList.remove(todo);
        notifyDataSetChanged();
    }
    public interface ItemClickListener {

        public void onItemClick(Todo todo);
    }
}

