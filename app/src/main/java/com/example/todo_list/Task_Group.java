package com.example.todo_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Task_Group extends AppCompatActivity {

    ListView listView;
    Task_Todo_Adapter task_todo_adapter;
    List<Task> tasks;
    ImageButton add;
    String groupId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_group);
        listView = findViewById(R.id.list_task_group);
        add = findViewById(R.id.add_task_group);
        Intent intent = getIntent();
        groupId = intent.getStringExtra("groupId");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Task_Group.this,Add_Task.class);
                intent.putExtra("groupId",groupId);
                startActivity(intent);
            }
        });
        loadTask();
    }
    public void setAdapter(){

        task_todo_adapter = new Task_Todo_Adapter(this,R.layout.custom_list_task_today,tasks,groupId);
        listView.setAdapter(task_todo_adapter);

    }
    public  void addTask(){

    }
    public void loadTask(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Group").child(groupId).child("task");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tasks = new ArrayList<>();
                for (DataSnapshot snapTask: snapshot.getChildren()) {
                    Task task = snapTask.getValue(Task.class);
                    tasks.add(task);
                }
                setAdapter();
                task_todo_adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

    }
}