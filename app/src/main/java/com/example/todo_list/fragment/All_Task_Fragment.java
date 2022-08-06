package com.example.todo_list.fragment;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.todo_list.R;
import com.example.todo_list.model.Task;
import com.example.todo_list.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link All_Task_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class All_Task_Fragment extends Fragment {
    private User user;
    List<Task> taskGroups;
    List<Task> taskPers;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int numberGroup;
    int numberPer;
    private ProgressBar groupBar;
    private ProgressBar personalBar;

    public All_Task_Fragment(User user) {
        this.user = user;
    }

    public All_Task_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment List_Task_Fragment.
     */
    // TODO: Rename and change types and numberGroup of parameters
    public static All_Task_Fragment newInstance(String param1, String param2) {
        All_Task_Fragment fragment = new All_Task_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_all__task_, container, false);
        LinearLayout layoutPresonal = view.findViewById(R.id.personal_all);
        LinearLayout layoutGroup = view.findViewById(R.id.group_all);
        groupBar = view.findViewById(R.id.proGroup);
        personalBar = view.findViewById(R.id.proPersonal);
        taskGroups = new ArrayList<>();
        taskPers = new ArrayList<>();
        numberGroup = 0;
        numberPer = 0;
        setGroupBar();
        setPerBar();

        layoutPresonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Personal_Task_Fragment  personal_task_fragment = new Personal_Task_Fragment(user);
              replacefragment(personal_task_fragment);
            }
        });
        layoutGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Group_Task_Fragment  group_task_fragment = new Group_Task_Fragment(user);
                replacefragment(group_task_fragment);
            }
        });
        return view;
    }
    private void setGroupBar(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef2 = database.getReference("Group");
        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()) {
                    User getuser = snap.child("member").child(user.getId()).getValue(User.class);
                    if(getuser!=null){
                        DataSnapshot snapshot1 = snap.child("task");
                        for (DataSnapshot snapTask: snapshot1.getChildren()) {
                            Task task = snapTask.getValue(Task.class);
                            taskGroups.add(task);

                        }
                    }
                }
                int countTrue = 0;
                int countFalse = 0;

                for (Task task: taskGroups) {
                    if(task.isCheck()){
                        countTrue++;
                    }
                    else {
                        countFalse++;
                    }
                }
                if(countFalse!=0){

                    numberGroup =  (int) ((float)countTrue/taskGroups.size()*100);

                }
                else if(countTrue>0&&countFalse==0){
                    numberGroup =  100;
                }
                showGroupBar();
                
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
     
        
    }
    public void setPerBar() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef2 = database.getReference("User").child(user.getId()).child("task");
        myRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapTask : snapshot.getChildren()) {
                    Task task = snapTask.getValue(Task.class);
                    taskPers.add(task);

                }
                int countTrue = 0;
                int countFalse = 0;

                for (Task task : taskPers) {
                    if (task.isCheck()) {
                        countTrue++;
                    } else {
                        countFalse++;
                    }
                }
                if (countFalse != 0) {

                    numberPer = (int) ((float) countTrue / taskGroups.size() * 100);

                } else if (countTrue > 0 && countFalse == 0) {
                    numberPer = 100;
                }
                showPerBar();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void showPerBar(){

        personalBar.setProgress(numberPer);
        if(numberPer>0 && numberPer <=25){
            personalBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
        }else  if(numberPer>25 && numberPer <=50){
            personalBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.prink)));
        }
        else  if(numberPer>50 && numberPer <=75){
            personalBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
        }else if(numberPer>75 && numberPer <=100){
            personalBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
        }

    }
    public void showGroupBar(){

        groupBar.setProgress(numberGroup);
        if(numberGroup>0 && numberGroup <=25){
            groupBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
        }else  if(numberGroup>25 && numberGroup <=50){
            groupBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.prink)));
        }
        else  if(numberGroup>50 && numberGroup <=75){
            groupBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple_500)));
        }else if(numberGroup>75 && numberGroup <=100){
            groupBar.setProgressTintList(ColorStateList.valueOf(getResources().getColor(R.color.blue)));
        }
    }
    private void replacefragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}