package com.example.todo_list.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todo_list.R;
import com.example.todo_list.model.User;

import java.util.List;

public class MemberAdapter extends ArrayAdapter<User> {
    Context context;
    int resource;
    List<User> users;
    public MemberAdapter(@NonNull Context context, int resource,List<User> users) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.users = users;

    }

    @Override
    public int getCount() {
        return users.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(resource,null);
        TextView name_member = (TextView) convertView.findViewById(R.id.name_member);
        TextView email_member = convertView.findViewById(R.id.email_member);
        LinearLayout layout = convertView.findViewById(R.id.layout_member);

        Resources res = convertView.getResources();
        int color = res.getColor(R.color.white);
        if (position % 2 == 0) {
            color = res.getColor(R.color.white);
        } else {
            color = res.getColor(R.color.blue50);
        }
        layout.setBackgroundColor(color);
        User user = users.get(position);
        name_member.setText(user.getName());
        email_member.setText("Email:"+user.getEmail());
        return convertView;
    }
}
