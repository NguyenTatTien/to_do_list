package com.example.todo_list;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Personal_Task_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Personal_Task_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    User user;
    Personal_Task_Adapter personal_task_adapter;

     List<Task> tasks;
    public Personal_Task_Fragment(User user) {
        // Required empty public constructor
        this.user = user;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Personal_Task_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Personal_Task_Fragment newInstance(String param1, String param2) {
//        Personal_Task_Fragment fragment = new Personal_Task_Fragment();
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
        View view =  inflater.inflate(R.layout.fragment_personal__task_, container, false);
        ImageButton add_task = view.findViewById(R.id.add_task_personal);
        ListView listView = view.findViewById(R.id.list_task_personal);
        tasks =new ArrayList<>();
        getAllTask();
        personal_task_adapter = new Personal_Task_Adapter(getContext(),R.layout.custom_list_task_today,tasks, user.getId());
        listView.setAdapter(personal_task_adapter);
        add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Add_Task.class);
                intent.putExtra("data","personal");
                intent.putExtra("id",user.getId());
                startActivity(intent);
            }
        });
        return view;
    }
    public void getAllTask(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("User").child(user.getId()).child("task");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tasks =new ArrayList<>();
                for (DataSnapshot snapTask: snapshot.getChildren()) {
                    Task task = snapTask.getValue(Task.class);
                    tasks.add(task);
                }
                personal_task_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}