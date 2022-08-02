package com.example.todo_list;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main__ToDo extends AppCompatActivity {
    private SlidingRootNav slidingRootNav;
    ListView lvNavigation;
    NavigationAdapter navigationAdapter;
    List<MenuNavigation> menuNavigationList;
    Group_Task_Fragment group_task_fragment;
    Today_Task_Fragment today_task_fragment;
    Personal_Task_Fragment personal_task_fragment;
    All_Task_Fragment all_task_fragment;
    List<Fragment> fragments;
    User user;
    TextView name_user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_to_do);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        group_task_fragment = new Group_Task_Fragment(user);
        today_task_fragment = new Today_Task_Fragment(user);
        personal_task_fragment = new Personal_Task_Fragment(user);
        all_task_fragment = new All_Task_Fragment();
        fragments = new ArrayList<>();
        fragments.add(today_task_fragment);
        fragments.add(personal_task_fragment);
        fragments.add(group_task_fragment);
        fragments.add(all_task_fragment);
        setSupportActionBar(toolbar);
        replacefragment(today_task_fragment);

        menuNavigationList = new ArrayList<>();
        menuNavigationList.addAll(
                Arrays.asList(
                        new MenuNavigation(R.drawable.today, "Today"),
                        new MenuNavigation(R.drawable.personal, "Personal"),
                        new MenuNavigation(R.drawable.group,"Group"),
                        new MenuNavigation(R.drawable.task,"Task"),
                        new MenuNavigation(R.drawable.setting,"Setting")
                ));

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withDragDistance(180)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_navigation)
                .inject();
        name_user = findViewById(R.id.name_user);
        name_user.setText(user.getName());
        navigationAdapter = new NavigationAdapter(this,R.layout.custom_list_view_navigtion,menuNavigationList);
        lvNavigation = (ListView) findViewById(R.id.list_view_navigation);
        lvNavigation.setAdapter(navigationAdapter);
        lvNavigation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                replacefragment(fragments.get(position));
                slidingRootNav.closeMenu();
            }
        });
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Group");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void replacefragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
//    private void removeFragment(Fragment fragment){
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.remove(fragment);
//        fragmentTransaction.commit();
//    }


}