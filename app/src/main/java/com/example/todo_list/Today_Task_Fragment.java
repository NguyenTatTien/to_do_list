package com.example.todo_list;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
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
    public Today_Task_Fragment() {

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
        View view = inflater.inflate(R.layout.fragment_today__task_, container, false);
        ListView listView = (ListView) view.findViewById(R.id.list_task_today);
        tasks = new ArrayList<>();
        SetData();
        task_todo_adapter = new Task_Todo_Adapter(this.getContext(),R.layout.custom_list_task_today,tasks);
        listView.setAdapter(task_todo_adapter);
        return view;
    }
    private void SetData(){

//        nt id, String name, boolean check, TimeZone startTime, java.util.TimeZone
//        endTime, java.util.TimeZone remind
        tasks.add(new Task("1","Đi học",false,"7:00","10:30","6:45"));
        tasks.add(new Task("2","Học bài",false,"19:00","20:00","18:45"));
        tasks.add(new Task("3","Chơi game",false,"20:00","21:00","19:45"));
        tasks.add(new Task("4","Làm việc",false,"21:00","23:00","20:45"));
    }
}