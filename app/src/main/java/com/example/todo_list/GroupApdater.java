package com.example.todo_list;

import android.content.Context;
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
        progressBar.setProgress(50);
        numberbar.setText("50%");



        return convertView;
    }
}
