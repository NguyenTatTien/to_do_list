package com.example.todo_list;

import android.content.pm.LabeledIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Group_Task_Adapter  extends RecyclerView.Adapter<Group_Task_Adapter.GroupTaskViewHolder>{
    List<Task> tasks;
    public void setData(List<Task> tasks){
        this.tasks = tasks;
        notifyDataSetChanged();

    }
    @NonNull
    @Override
    public GroupTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_task_today,parent,false);
        return new GroupTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupTaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        if(task != null){
            holder.name_task.setText(task.getName());
            holder.timestart.setText(task.getStartTime());
            holder.radioButton.setChecked(task.isCheck());

        }
    }

    @Override
    public int getItemCount() {
        if(tasks!=null){
            return  tasks.size();
        }
        return 0;
    }

    public class GroupTaskViewHolder extends RecyclerView.ViewHolder{
        TextView name_task;
        TextView timestart;
        RadioButton radioButton;
        public GroupTaskViewHolder(@NonNull View itemView) {
            super(itemView);
             name_task = (TextView) itemView.findViewById(R.id.name_task_today);
             timestart = itemView.findViewById(R.id.time_start_task_today);
             radioButton = (RadioButton) itemView.findViewById(R.id.check);
        }
    }
}
