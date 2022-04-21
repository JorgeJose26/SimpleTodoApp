package com.example.todoapp.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

import com.example.todoapp.models.Todo;

public class ReminderBroadcast extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        NotificationUtils _notificationUtils = new NotificationUtils(context);
        NotificationCompat.Builder _builder = _notificationUtils.setNotification("title", "description");
        _notificationUtils.getManager().notify(101, _builder.build());
    }
}