package com.example.todo_list;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Group_Task_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Group_Task_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    List<Group> groups;
    User user;
    EditText name_group;
    GroupApdater groupApdater;
    public Group_Task_Fragment(User user) {
        // Required empty public constructor
        this.user = user;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Group_Task_Fragment.
     */
//    // TODO: Rename and change types and number of parameters
    public static Group_Task_Fragment newInstance(String param1, String param2) {
//        Group_Task_Fragment fragment = new Group_Task_Fragment(user);
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
        View view =  inflater.inflate(R.layout.fragment_group__task_, container, false);
        ListView listView = view.findViewById(R.id.list_group);
        ImageButton add_group = view.findViewById(R.id.add_group);
        name_group = view.findViewById(R.id.add_name_group);
        groups = new ArrayList<>();
        LoadGroup();
        add_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddGroup();
            }
        });

        groupApdater = new GroupApdater(this.getContext(),R.layout.custom_list_group,groups);
        listView.setAdapter(groupApdater);
        // Inflate the layout for this fragment
        return view;
    }
    private void AddGroup(){
        if(name_group.getText().toString().equals("")){
            Toast.makeText(this.getContext(), "Please enter name group!", Toast.LENGTH_SHORT).show();
        }else{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Group");
            DatabaseReference groupRef = myRef.push();
            Group group = new Group(groupRef.getKey(),name_group.getText().toString(),user);
            groupRef.setValue(group);
            LoadGroup();
        }

//        List<MemberGroup> list1 = new ArrayList<>();
//        list1.addAll(Arrays.asList(
//                new MemberGroup(1,1,1),
//                new MemberGroup(2,1,2)
//
//        ));
//        List<MemberGroup> list2 = new ArrayList<>();
//        list2.addAll(Arrays.asList(
//                new MemberGroup(1,2,1),
//                new MemberGroup(2,2,2),
//                new MemberGroup(2,2,3)
//
//        ));
//        List<MemberGroup> list3 = new ArrayList<>();
//        list3.addAll(Arrays.asList(
//                new MemberGroup(1,3,1),
//                new MemberGroup(2,3,2),
//                new MemberGroup(2,3,3)
//
//        ));
//        groups.addAll(Arrays.asList(
//                new Group(1,"DH19LT",list1),
//                new Group(2,"DH19CT",list2),
//                new Group(3,"TrueTech",list3)
//        ));


    }
   public void LoadGroup(){
       FirebaseDatabase database = FirebaseDatabase.getInstance();
       DatabaseReference myRef = database.getReference("Group");
       myRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               for (DataSnapshot snapshotGroup:snapshot.getChildren()) {
                   Group group = snapshotGroup.getValue(Group.class);
                   assert group != null;
                   if(group.Admin.getId().equals(user.getId())){
                       groups.add(group);
                   }
               }
               groupApdater.notifyDataSetChanged();

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
    }
}