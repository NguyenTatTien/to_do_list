package com.example.todo_list;

import android.app.AlarmManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Today_Task_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Today_Task_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Task_Todo_Adapter task_todo_adapter;
    private List<Task> tasks;
    User user;
    public Today_Task_Fragment(User user) {
        this.user = user;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Today_Task_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Today_Task_Fragment newInstance(String param1, String param2) {
//        Today_Task_Fragment fragment = new Today_Task_Fragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_today__task_, container, false);
        ListView listView = (ListView) view.findViewById(R.id.list_task_today);
        tasks = new ArrayList<>();
        SetData();
        task_todo_adapter = new Task_Todo_Adapter(this.getContext(),R.layout.custom_list_task_today,tasks,null);
        listView.setAdapter(task_todo_adapter);
        return view;
    }
    private void SetData(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Group");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()) {
                    User getuser = snap.child("member").child(user.getId()).getValue(User.class);
                    if (getuser != null) {
                        DataSnapshot taskRef = snap.child("task");
                        for (DataSnapshot snapTask : taskRef.getChildren()) {
                            Task task = snapTask.getValue(Task.class);
//                            Date date = null;
//                            Date dateTask = null;
//                            try {
//                                date = new SimpleDateFormat("dd/MM/yyyy").parse(Calendar.getInstance().getTime().toString());
//                                dateTask = new SimpleDateFormat("dd/MM/yyyy").parse(task.);
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }

                            tasks.add(task);

                        }

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}