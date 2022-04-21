package com.example.todoapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todoapp.R;
import com.example.todoapp.adapters.ArchivedAdapter;
import com.example.todoapp.adapters.TodoAdapter;
import com.example.todoapp.models.Todo;
import com.example.todoapp.models.TodoDao;
import com.example.todoapp.models.TodoSaveListener;

import java.util.ArrayList;
import java.util.List;


public class archivedFragment extends Fragment {
    private TodoDao todoDao;
    private Todo todo;
    private List<Todo> todoList = new ArrayList<>();
    private ArchivedAdapter adapter;
    private RecyclerView recyclerView;
    private TodoSaveListener listener;
    public static final String ITEM_KEY = "item_key";


    public archivedFragment() {
        // Required empty public constructor
    }
    public void setRemoveListener(TodoSaveListener removeListener){
        listener = removeListener;
    }


    public static archivedFragment newInstance() {
        archivedFragment fragment = new archivedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private void setAdapter() {
        adapter = new ArchivedAdapter(getContext(),todoList, listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TodoViewModel todoViewModel = TodoViewModel.getInstance();
        new Thread( new Runnable() { @Override public void run() {
            todoList = todoViewModel.getTodoDao().getAllCompleted();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // update UI
                   setAdapter();
                }
            });

        } } ).start();





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_archived, container, false);
        recyclerView = rootView.findViewById(R.id.archivedRecycler);
        return rootView;
    }

}
