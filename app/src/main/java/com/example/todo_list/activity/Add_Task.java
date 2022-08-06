package com.example.todo_list.activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.todo_list.model.Nofication;
import com.example.todo_list.R;
import com.example.todo_list.model.Task;
import com.example.todo_list.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Add_Task extends AppCompatActivity {
    LocalDateTime date;
    TextView DateEnd,TimeEnd,DateRemind,TimeRemind;
    ImageButton add_task;
    String id;
    EditText nameTask;
    List<User> members;
    AlarmManager alarmManager;
    String strObject;
    String nameGroup;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        DateEnd = findViewById(R.id.day_end);
        DateRemind = findViewById(R.id.day_remind);
        TimeEnd = findViewById(R.id.time_end);
        TimeRemind = findViewById(R.id.time_remind);
        add_task = findViewById(R.id.add_new_task_group);
        nameTask = findViewById(R.id.new_name_task);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = getIntent();
        nameGroup = "";
        strObject = intent.getStringExtra("data");
        id = intent.getStringExtra("id");
        date = LocalDateTime.now();
        members = new ArrayList<>();
        DateEnd.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                ShowDatePickKerDiaLog(DateEnd);
            }
        });
        TimeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowTimePickerDialog(TimeEnd);
            }
        });
        DateRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDatePickKerDiaLog(DateRemind);
            }
        });
        TimeRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowTimePickerDialog(TimeRemind);
            }
        });
        add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(strObject.equals("personal")){
                    Log.e("user","presonal");
                    addTask("User");
                }
                else {
                    Log.e("group","group");
                    addTask("Group");

                }

            }
        });
        sendNotification("");

    }
//    public void addTaskPersonal(){
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("User").child(id).child("task");
//        DatabaseReference taskRef = myRef.push();
//        final Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
//
//        String timeStart = formatter.format(calendar.getTime());
//        if (nameTask.getText().equals("")) {
//            Toast.makeText(Add_Task.this, "Please enter name task", Toast.LENGTH_LONG).show();
//        } else {
//            sendNotification();
//            Task task = new Task(taskRef.getKey(), nameTask.getText().toString(), false, timeStart, DateEnd.getText().toString() + " " + TimeEnd.getText().toString(), DateRemind.getText().toString() + " " + TimeRemind.getText().toString());
//            taskRef.setValue(task);
//
//
//        }
//    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addTask(String name) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(name).child(id).child("task");
        DatabaseReference taskRef = myRef.push();
        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        String timeStart = formatter.format(calendar.getTime());
        if (nameTask.getText().equals("")) {
            Toast.makeText(Add_Task.this, "Please enter name task", Toast.LENGTH_LONG).show();
        } else {
            if(name.equals("Group")){
                FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                DatabaseReference myRef1 = database.getReference(name).child(id);
                myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        nameGroup = snapshot.child("name").getValue(String.class);
                        sendNotification(nameGroup);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            else{
                sendNotificationUser(id);
            }

            Task task = new Task(taskRef.getKey(), nameTask.getText().toString(), false, timeStart, DateEnd.getText().toString() + " " + TimeEnd.getText().toString(), DateRemind.getText().toString() + " " + TimeRemind.getText().toString());
            taskRef.setValue(task);
        }

    }
    public void sendNotificationUser(String id){
        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database1.getReference("User").child(id).child("notification");
        DatabaseReference noficationRef = userRef.push();
        noficationRef.setValue(new Nofication(noficationRef.getKey(),"Thông báo","Bạn có nhiệm vụ mới",false));
    }
    public void sendNotification(String groupName){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Group");

       myRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               DataSnapshot snapshot1 = snapshot.child(id).child("member");
               members = new ArrayList<>();
               for (DataSnapshot snapMember: snapshot1.getChildren()) {
                   User member = snapMember.getValue(User.class);
                   members.add(member);

               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

        for (User user: members) {
            FirebaseDatabase database1 = FirebaseDatabase.getInstance();
            DatabaseReference userRef = database1.getReference("User").child(user.getId()).child("notification");
            DatabaseReference noficationRef = userRef.push();
            noficationRef.setValue(new Nofication(noficationRef.getKey(),"Thông báo","Bạn có nhiệm vụ mới trong nhóm "+groupName,false));

        }


    }

    public void ShowDatePickKerDiaLog(TextView textView){
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
                textView.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },year,month,day);
        datePickerDialog.show();
    }
    public void ShowTimePickerDialog(TextView textView){
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(0,0,0,hourOfDay,minute);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                textView.setText(simpleDateFormat.format(calendar.getTime()));

            }
        },hour,minute,true);
        timePickerDialog.show();
    }


}