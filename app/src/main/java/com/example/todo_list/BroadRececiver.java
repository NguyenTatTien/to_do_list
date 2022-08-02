package com.example.todo_list;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class BroadRececiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context,NotifitionServices.class);
        List<String> listGroupId = (List<String>) intent.getExtras().getSerializable("listGroupId");
        Log.e("ruserives", Calendar.getInstance().getTime().toString());
        intent1.putExtra("listGroupId", (Serializable) listGroupId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent1);
        } else {
            context.startService(intent1);
        }
    }
}
