package com.example.todoapp.fragments;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.todoapp.R;
import com.example.todoapp.activites.MainActivity;
import com.example.todoapp.models.Todo;
import com.example.todoapp.models.TodoDao;
import com.example.todoapp.models.TodoSaveListener;
import com.example.todoapp.utils.NotificationUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class TodoDetailFragment extends Fragment {
    private Todo todo;
    private static final String ARG_TODO = "Todo";
    private EditText titleTextView;
    private EditText descriptionTextView;
    private TextView completedText;
    private CheckBox checkBox;
    private Button saveBut;
    private Button changeDate;
    private TextView dueDateText;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private TodoSaveListener listener;
    private TodoDao todoDao;


    public void setListener(TodoSaveListener todoSaveListener) {
        listener = todoSaveListener;
    }

    public TodoDetailFragment  () {


    }
    public void onItemClick(Todo todo) {
        getActivity().getSupportFragmentManager().popBackStackImmediate();

    }
    public static TodoDetailFragment newInstance(Todo todo) {
       TodoDetailFragment fragment = new TodoDetailFragment();
       Bundle args = new Bundle();
       args.putSerializable(ARG_TODO, todo);
       fragment.setArguments(args);
       return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        todo = (Todo) getArguments().getSerializable(ARG_TODO);
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_todo_detail, container, false);
        titleTextView = rootView.findViewById(R.id.todo_title);
        descriptionTextView = rootView.findViewById(R.id.todo_description);
        datePicker = rootView.findViewById(R.id.datePicker);
        checkBox = rootView.findViewById(R.id.checkBox);
        saveBut = rootView.findViewById(R.id.saveButton);
        dueDateText = rootView.findViewById(R.id.todoDueDate);
        changeDate = rootView.findViewById(R.id.changeDate);
        completedText = rootView.findViewById(R.id.completedText);
        timePicker = rootView.findViewById(R.id.timePicker);
        datePicker.setVisibility(View.GONE);
        timePicker.setVisibility(View.GONE);
        titleTextView.setText(todo.getTitle());


        if(todo.getDescription() != null){
            descriptionTextView.setText(todo.getDescription());
        }
        checkBox.setChecked(todo.isCompleted());

        if(todo.getDate()  !=null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
            String dateString = sdf.format(todo.getDate());
            dueDateText.setText("Due Date: " + dateString);
            datePicker.setVisibility(View.GONE);
        }else {
            datePicker.setVisibility(View.GONE);
            dueDateText.setVisibility(View.GONE);
        }

        timePicker.setIs24HourView(false);
        saveBut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                todo.setDescription(descriptionTextView.getText().toString());
                todo.setTitle(titleTextView.getText().toString());
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();
                int hour = timePicker.getCurrentHour();
                int min = timePicker.getCurrentMinute();
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day, hour, min);
                todo.setDate(calendar.getTime());
                datePicker.setVisibility(View.GONE);
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy hh:mm a");
                String editDateString = sdf.format(todo.getDate());
                dueDateText.setText("Due Date " + editDateString);
                dueDateText.setVisibility(View.VISIBLE);
                todo.setCompleted(checkBox.isChecked());
                TodoViewModel todoViewModel = TodoViewModel.getInstance();
                todoDao = todoViewModel.getTodoDao();
                reminderNotification(todo);
                new Thread( new Runnable() { @Override public void run() {
                   todoDao.update(todo);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // update UI
                            Toast.makeText(getContext(), "SAVED", Toast.LENGTH_SHORT).show();
                            onItemClick(todo);
                        }
                    });
                } } ).start();

                /*NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(),"My Notification");
                builder.setContentTitle("Your Todo");
                builder.setContentText("Hello Your Todo is due");
                builder.setSmallIcon(R.drawable.ic_launcher_background);
                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
                builder.setAutoCancel(true);
                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getContext());
                managerCompat.notify(todo.uid, builder.build());*/

            }
        });
        changeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(todo.getDate() !=null) {
                    datePicker.setVisibility(View.GONE);
                }else {
                    datePicker.setVisibility(View.VISIBLE);
                    dueDateText.setVisibility(View.GONE);
                }
                datePicker.setVisibility(View.VISIBLE);
                timePicker.setVisibility(View.VISIBLE);
            }
        });
        return rootView;
        }
    public void reminderNotification(Todo todo)
    {
        NotificationUtils _notificationUtils = new NotificationUtils(getContext());
//        long _currentTime = System.currentTimeMillis();
        long todoInMils = todo.getDate().getTime();
//        long tenSeconds = 1000 * 10;
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(todoInMils);
        c.set(Calendar.SECOND, 0);
        long _triggerReminder = c.getTimeInMillis(); //triggers a reminder after 10 seconds.
        //Convert date to millis and place it on setReminder
        _notificationUtils.setReminder(_triggerReminder,todo);
    }

    }
