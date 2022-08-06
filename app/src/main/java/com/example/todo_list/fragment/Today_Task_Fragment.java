package com.example.todo_list.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.todo_list.adapter.Group_Task_Adapter;
import com.example.todo_list.adapter.Personal_Task_Adapter;
import com.example.todo_list.R;
import com.example.todo_list.model.Task;
import com.example.todo_list.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    private List<Task> tasksPersonal;
    private List<Task> tasksGroup;
    RecyclerView listPersonal;
    RecyclerView listGroup;
    User user;
    Personal_Task_Adapter personal_task_adapter;
    Group_Task_Adapter group_task_adapter;
    public Today_Task_Fragment(){}
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
        Today_Task_Fragment fragment = new Today_Task_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
        listPersonal = view.findViewById(R.id.list_personal_today);
        listGroup = view.findViewById(R.id.list_group_today);
        tasksPersonal = new ArrayList<>();
        tasksGroup = new ArrayList<>();
        listGroup.setLayoutManager(new LinearLayoutManager(getContext()));
        listPersonal.setLayoutManager(new LinearLayoutManager(getContext()));
        SetData();
        SetApdapter();
        return view;
    }
    public void SetApdapter(){
        personal_task_adapter = new Personal_Task_Adapter();

        group_task_adapter = new Group_Task_Adapter();
        group_task_adapter.setData(tasksGroup,"");
        personal_task_adapter.setData(tasksPersonal, user.getId());
        listPersonal.setAdapter(personal_task_adapter);
        listGroup.setAdapter(group_task_adapter);
        Log.e("dfs",tasksPersonal.size()+"");

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
                            Date dateStart = null;
                            Calendar date = null;
                            Date dateEnd = null;
                            Calendar endDate;
                            Calendar startDate;
                            try {
                                date = Calendar.getInstance();
                                dateStart = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(task.getStartTime());
                                dateEnd = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(task.getEndTime());
                                endDate = Calendar.getInstance();
                                startDate = Calendar.getInstance();
                                endDate.setTime(dateStart);
                                startDate.setTime(dateEnd);
                                if(String.valueOf(startDate.get(Calendar.DATE)).equals(String.valueOf(date.get(Calendar.DATE)))&&String.valueOf(startDate.get(Calendar.MONTH)).equals(String.valueOf(date.get(Calendar.MONTH)))&&String.valueOf(startDate.get(Calendar.YEAR)).equals(String.valueOf(date.get(Calendar.YEAR)))&&String.valueOf(startDate.get(Calendar.DATE)).equals(String.valueOf(endDate.get(Calendar.DATE)))&&String.valueOf(startDate.get(Calendar.MONTH)).equals(String.valueOf(endDate.get(Calendar.MONTH)))&&String.valueOf(startDate.get(Calendar.YEAR)).equals(String.valueOf(endDate.get(Calendar.YEAR)))){
                                    tasksGroup.add(task);

                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }




                        }


                    }
                    SetApdapter();
                    personal_task_adapter.notifyDataSetChanged();
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database.getReference("User").child(user.getId()).child("task");
        myRef1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot  snapTask: snapshot.getChildren()) {
                    Task task = snapTask.getValue(Task.class);

                    Date dateStart = null;
                    Date dateEnd = null;
                    Calendar endDate;
                    Calendar startDate;
                    Calendar date;
                    try {

                        date = Calendar.getInstance();
                        dateStart = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(task.getStartTime());
                        dateEnd = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(task.getEndTime());
                        endDate = Calendar.getInstance();
                        startDate = Calendar.getInstance();
                        endDate.setTime(dateStart);
                        startDate.setTime(dateEnd);
                        if(String.valueOf(startDate.get(Calendar.DATE)).equals(String.valueOf(date.get(Calendar.DATE)))&&String.valueOf(startDate.get(Calendar.MONTH)).equals(String.valueOf(date.get(Calendar.MONTH)))&&String.valueOf(startDate.get(Calendar.YEAR)).equals(String.valueOf(date.get(Calendar.YEAR)))&&String.valueOf(startDate.get(Calendar.DATE)).equals(String.valueOf(endDate.get(Calendar.DATE)))&&String.valueOf(startDate.get(Calendar.MONTH)).equals(String.valueOf(endDate.get(Calendar.MONTH)))&&String.valueOf(startDate.get(Calendar.YEAR)).equals(String.valueOf(endDate.get(Calendar.YEAR)))){
                            tasksPersonal.add(task);

                        }

                    } catch (ParseException e) {

                        e.printStackTrace();
                    }
                    SetApdapter();
                    personal_task_adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}