package com.example.todo_list.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.todo_list.adapter.Group_Task_Adapter;
import com.example.todo_list.R;
import com.example.todo_list.model.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Task_Group extends AppCompatActivity {

    //ListView listView;

    List<Task> tasks;
    ImageButton add;
    String groupId;
    RecyclerView listView;
    Group_Task_Adapter group_task_adapter;
    TextView namegroup,memberGroup;
    ProgressBar barGroup;
    List<Task> newTasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_group);
        listView = findViewById(R.id.list_task_group);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(linearLayoutManager);
        tasks = new ArrayList<>();
        add = findViewById(R.id.add_task_group);
        namegroup = findViewById(R.id.task_name_group);
        memberGroup = findViewById(R.id.task_member_group);
        barGroup = findViewById(R.id.task_pro_group);
        Intent intent = getIntent();
        groupId = intent.getStringExtra("groupId");
        LoadData();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Task_Group.this,Add_Task.class);
                intent.putExtra("data","group");
                intent.putExtra("id",groupId);
                startActivity(intent);
            }
        });

        loadTask();
        setAdapter();
        ItemTouchHelper itemTouchHelper  = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Group").child(groupId).child("task").child(tasks.get(position).getId());
                myRef.removeValue();
                tasks.remove(position);
                group_task_adapter.notifyDataSetChanged();
            }
        });
        itemTouchHelper.attachToRecyclerView(listView);

    }
    public void setAdapter(){
        group_task_adapter = new Group_Task_Adapter();
       // task_todo_adapter = new Task_Todo_Adapter(this,R.layout.custom_list_task_today,tasks,groupId);
        //listView.setAdapter(task_todo_adapter);
        group_task_adapter.setData(tasks,groupId);
        listView.setAdapter(group_task_adapter);


    }
    public void LoadData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Group");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                newTasks = new ArrayList<>();
                for (DataSnapshot snap1 : snapshot.getChildren()) {
                    String id = snap1.child("id").getValue(String.class);
                    if (id.equals(groupId)) {
                        String name = snap1.child("name").getValue(String.class);
                        int mumberMember = (int) snap1.child("member").getChildrenCount();

                        namegroup.setText(name);
                        memberGroup.setText("Thành viên:" + mumberMember);
                        DataSnapshot snapTask = snap1.child("task");
                        for (DataSnapshot snapChildTask : snapTask.getChildren()) {
                            Task task = snapChildTask.getValue(Task.class);
                            tasks.add(task);
                        }
                        int countTrue = 0;
                        int countFalse = 0;
                        int number = 0;
                        for (Task task : tasks) {
                            if (task.isCheck()) {
                                countTrue++;
                            } else {
                                countFalse++;
                            }
                        }
                        if (countFalse != 0) {

                            number = (int) ((float) countTrue / newTasks.size() * 100);

                        } else if (countTrue > 0 && countFalse == 0) {
                            number = 100;
                        }
                        showDataPro(number);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void showDataPro(int number){

        barGroup.setProgress(number);
        if(number>0 && number <=25){
            barGroup.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
        }else  if(number>25 && number <=50){
            barGroup.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.prink)));
        }
        else  if(number>50 && number <=75){
            barGroup.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
        }else if(number>75 && number <=100){
            barGroup.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
        }

    }
    public void loadTask() {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Group").child(groupId).child("task");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        tasks = new ArrayList<>();
                        for (DataSnapshot snapTask : snapshot.getChildren()) {
                            Task task = snapTask.getValue(Task.class);
                            tasks.add(task);

                        }
                        setAdapter();
                        group_task_adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });

            }
}