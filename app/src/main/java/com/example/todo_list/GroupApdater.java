package com.example.todo_list;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class GroupApdater extends ArrayAdapter<Group> {
    Context context;
    int resource;
    List<Group> groups;
    public GroupApdater(@NonNull Context context, int resource,List<Group> groups) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.groups = groups;
    }
    @Override
    public int getCount() {
        return groups.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(resource,null);
        TextView name = (TextView) convertView.findViewById(R.id.name_group);
        TextView member = convertView.findViewById(R.id.member_group);
        ProgressBar progressBar = convertView.findViewById(R.id.progressBar_group);
        TextView numberbar = convertView.findViewById(R.id.number_pbar_group);
        name.setText(groups.get(position).Name);
        member.setText("Thành viên:"+groups.get(position).getMember().size());
        int countTrue = 0;
        int countFalse = 0;
        int number=0;
        if(groups.get(position).tasks!=null){
            for (Task task: groups.get(position).tasks) {
                if(task.isCheck()){
                    countTrue ++;
                }
                else {
                    countFalse++;
                }
            }
            if(countFalse!=0){
                Log.e("fsdf",groups.get(position).tasks.size()+"");
                Log.e("sdfdsf",countTrue+"");
                number = (int) ((float)countTrue/groups.get(position).tasks.size()*100);

            }
            else if(countTrue>0&&countFalse==0){
                number = 100;
            }

        }
        if(number>0 && number <=25){
            progressBar.setProgressTintList(ColorStateList.valueOf(convertView.getResources().getColor(R.color.red)));
        }else  if(number>25 && number <=50){
            progressBar.setProgressTintList(ColorStateList.valueOf(convertView.getResources().getColor(R.color.prink)));
        }
        else  if(number>50 && number <=75){
            progressBar.setProgressTintList(ColorStateList.valueOf(convertView.getResources().getColor(R.color.purple_500)));
        }else if(number>75 && number <=100){
            progressBar.setProgressTintList(ColorStateList.valueOf(convertView.getResources().getColor(R.color.blue)));
        }

        progressBar.setProgress(number);

        numberbar.setText(number+"%");
        return convertView;
    }
}
