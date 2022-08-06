package com.example.todo_list.adapter;

import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo_list.R;
import com.example.todo_list.model.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public  class Personal_Task_Adapter extends RecyclerView.Adapter<Personal_Task_Adapter.PersonalTaskViewHoller>{
    List<Task> tasks;
    String userId;
    public void setData(List<Task> tasks,String userId){
        this.tasks = tasks;
        this.userId = userId;
        notifyDataSetChanged();

    }
    @NonNull
    @Override

    public PersonalTaskViewHoller onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_task_today,parent,false);
        return new PersonalTaskViewHoller(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PersonalTaskViewHoller holder, int position) {
        Task task = tasks.get(position);
        Log.e("fdsfsd", tasks.size() + "");
        if (task != null) {
            holder.name_task.setText(task.getName());
            holder.timestart.setText(task.getStartTime());
            holder.radioButton.setChecked(task.isCheck());
            holder.radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!tasks.get(position).isCheck()) {
                        tasks.get(position).setCheck(true);
                        holder.radioButton.setChecked(true);
                        holder.name_task.setPaintFlags(holder.name_task.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("User").child(userId).child("task").child(task.getId());
                        myRef.child("check").setValue(true);
                    } else {
                        tasks.get(position).setCheck(false);
                        holder.radioButton.setChecked(false);
                        holder.name_task.setPaintFlags(holder.name_task.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("User").child(userId).child("task").child(task.getId());
                        myRef.child("check").setValue(false);

                    }

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(tasks!=null){
            return  tasks.size();
        }
        return 0;
    }

    class PersonalTaskViewHoller extends RecyclerView.ViewHolder{
        TextView name_task;
        TextView timestart;
        RadioButton radioButton;
        public PersonalTaskViewHoller(@NonNull View itemView) {
            super(itemView);
            name_task = (TextView) itemView.findViewById(R.id.name_task_today);
            timestart = itemView.findViewById(R.id.time_start_task_today);
            radioButton = (RadioButton) itemView.findViewById(R.id.check);
        }
    }

}
