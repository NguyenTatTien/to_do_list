package com.example.todo_list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todo_list.R;
import com.example.todo_list.model.MenuNavigation;

import java.util.List;

public class NavigationAdapter extends ArrayAdapter<MenuNavigation> {
    private Context context;
    private int resource;
    List<MenuNavigation> menuNavigations;
    public NavigationAdapter(@NonNull Context context, int resource,List<MenuNavigation> menuNavigations) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.menuNavigations = menuNavigations;

    }
    @Override
    public int getCount() {
        return menuNavigations.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(resource,null);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.txt_name_menu_navigation);
        ImageView image = (ImageView) convertView.findViewById(R.id.img_menu_navigation);
        MenuNavigation menuNavigation = menuNavigations.get(position);
        txtTitle.setText(menuNavigation.getName());
        image.setImageResource(menuNavigation.getImage());
        return convertView;
    }
}
