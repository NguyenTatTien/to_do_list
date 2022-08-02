package com.example.todo_list;

import android.content.Context;
import android.graphics.Paint;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Struct;
import java.util.List;

public class Task_Todo_Adapter extends ArrayAdapter<Task> {
    private Context context;
    private int resource;
    List<Task> tasks;
    String groupId;
    public Task_Todo_Adapter(@NonNull Context context, int resource, List<Task> tasks,String groupId) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.tasks = tasks;
        this.groupId = groupId;

    }
    @Override
    public int getCount() {
        return tasks.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(resource,null);
        TextView name_task = (TextView) convertView.findViewById(R.id.name_task_today);
        TextView timestart = convertView.findViewById(R.id.time_start_task_today);
        RadioButton radioButton = (RadioButton) convertView.findViewById(R.id.check);
        Task task = tasks.get(position);
        name_task.setText(task.getName());
        timestart.setText(task.getStartTime().toString());
        radioButton.setChecked(task.isCheck());
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!tasks.get(position).isCheck()){
                    tasks.get(position).setCheck(true);
                    radioButton.setChecked(true);
                    name_task.setPaintFlags(name_task.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Group").child(groupId).child("task").child(task.getId());
                    myRef.child("check").setValue(true);
                }
                else{
                    tasks.get(position).setCheck(false);
                    radioButton.setChecked(false);
                    name_task.setPaintFlags(name_task.getPaintFlags()& (~Paint.STRIKE_THRU_TEXT_FLAG));
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("Group").child(groupId).child("task").child(task.getId());
                    myRef.child("check").setValue(false);

                }

            }
        });
        return convertView;
    }
}
