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

import java.sql.Struct;
import java.util.List;

public class Task_Todo_Adapter extends ArrayAdapter<Task> {
    private Context context;
    private int resource;
    List<Task> tasks;
    public Task_Todo_Adapter(@NonNull Context context, int resource, List<Task> tasks) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.tasks = tasks;

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
                    String text = "<strike>+"+name_task.getText().toString()+"</strike>";
                    name_task.setText(Html.fromHtml(text));
                    tasks.get(position).setCheck(true);
                }
                else{
                    tasks.get(position).setCheck(false);
                }
                notifyDataSetChanged();
            }
        });
        return convertView;
    }
}
