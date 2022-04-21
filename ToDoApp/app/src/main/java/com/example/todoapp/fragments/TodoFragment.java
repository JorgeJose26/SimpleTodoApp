package com.example.todoapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todoapp.R;
import com.example.todoapp.adapters.TodoAdapter;
import com.example.todoapp.models.AppDatabase;
import com.example.todoapp.models.Todo;
import com.example.todoapp.models.TodoDao;
import com.example.todoapp.models.TodoSaveListener;

import java.util.ArrayList;
import java.util.List;

public class TodoFragment extends Fragment implements TodoAdapter.ItemClickListener, TodoSaveListener {
    private TodoDao todoDao;
    private List<Todo> todoList = new ArrayList<>();
    private static final String ARG_TODO = "Todo";
    private List<Todo> completedItems = new ArrayList<>();
    private Todo todo;
    private TodoAdapter adapter;
    private TodoSaveListener listener;
    private Button addButton;
    private Button archiveButton;
    private EditText editText;
    private RecyclerView recyclerView;
    public static final String ITEM_KEY = "item_key";


    public static TodoFragment newInstance(Todo todo) {
        TodoFragment fragment = new TodoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TODO, todo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TodoViewModel todoViewModel = TodoViewModel.getInstance();
        todoViewModel.initializeDataBase(this.getContext().getApplicationContext());
//        new Thread( new Runnable() { @Override public void run() {
//            todoDao = todoViewModel.getTodoDao();
//            todoList = todoViewModel.getTodoDao().getAllUncompleted();
//        } } ).start();

    }

    @Override
    public void onResume() {
        super.onResume();
        todoList.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                TodoViewModel todoViewModel = TodoViewModel.getInstance();
                todoDao = todoViewModel.getTodoDao();
                todoList = todoViewModel.getTodoDao().getAllUncompleted();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // update UI
                        setAdapter();
                    }
                });
            }
        }).start();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_todo, container, false);
        recyclerView = rootView.findViewById(R.id.titleNameList);
        addButton = rootView.findViewById(R.id.addButton);
        editText = rootView.findViewById(R.id.todoEditText);
        archiveButton = rootView.findViewById(R.id.archivedButton);
        createListView();
        return rootView;
    }

    private void setAdapter() {
        adapter = new TodoAdapter(getContext(), todoList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        createListView();


    }

    private void createListView() {
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String str = editText.getText().toString();
                final Todo todo = new Todo(str);
                todo.setTitle(str);
                if (!str.isEmpty()) {
                    editText.setText("");
                    adapter.addItem(todo);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            long id = todoDao.insertAll(todo);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // update UI
                                    todo.setUid((int) id);
                                    TodoDetailFragment fragment = TodoDetailFragment.newInstance(todo);
                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    transaction.replace(R.id.container, fragment, "Detail fragment");
                                    transaction.addToBackStack(null);
                                    transaction.commit();
                                }
                            });
                        }
                    }).start();
                } else {
                    Toast.makeText(getContext(), "please Enter a todo", Toast.LENGTH_LONG).show();
                }

            }
        });
        archiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                archivedFragment fragment = archivedFragment.newInstance();
                fragment.setRemoveListener(listener);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragment, "Archived fragment");
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }

    @Override
    public void onItemClick(Todo todo) {
        TodoDetailFragment fragment = TodoDetailFragment.newInstance(todo);
        fragment.setListener(this);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment, "Detail fragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onTodoCompleted(Todo todo) {
        todo.setCompleted(true);
        adapter.removeItem(todo);
        completedItems.add(todo);
    }

    @Override
    public void removeTodoCompleted(Todo todo) {
        todo.setCompleted(false);
        adapter.addItem(todo);
        completedItems.remove(todo);
    }
}
