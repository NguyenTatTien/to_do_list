package com.example.todo_list;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
    ListView listView;
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
        View contextView =  inflater.inflate(R.layout.fragment_group__task_, container, false);
        listView = contextView.findViewById(R.id.list_group);
        ImageButton add_group = contextView.findViewById(R.id.add_group);
        name_group = contextView.findViewById(R.id.add_name_group);
        groups = new ArrayList<>();

        LoadGroup();
        add_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddGroup();
            }
        });
        registerForContextMenu(listView);

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent intent = new Intent(getContext(),MemberActivity.class);
               intent.putExtra("groupId",groups.get(position).getId());
               intent.putExtra("user",(Serializable) user);
               startActivity(intent);
           }
       });
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Group");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                LoadGroup();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // Inflate the layout for this fragment
        return contextView;
    }
    private void AddGroup(){
        if(name_group.getText().toString().equals("")){
            Toast.makeText(this.getContext(), "Please enter name group!", Toast.LENGTH_SHORT).show();
        }else{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Group");
            DatabaseReference groupRef = myRef.push();
            List<Task> tasks = new ArrayList<>();
            List<User> memberGroups = new ArrayList<>();
            memberGroups.add(user);
            Group group = new Group(groupRef.getKey(),name_group.getText().toString(),user,memberGroups,tasks);
            groupRef.child("id").setValue(group.getId());
            groupRef.child("name").setValue(group.getName());
            groupRef.child("admin").setValue(user);
            DatabaseReference memberRef = groupRef.child("member");
            for (User member: memberGroups) {

                memberRef.child(member.getId()).setValue(member);

            }
            DatabaseReference taskRef = groupRef.child("task");
            for (Task task:tasks) {
                taskRef.setValue(task);
            }
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
    public void setAdapter(){

        groupApdater = new GroupApdater(this.getContext(),R.layout.custom_list_group,groups);
        listView.setAdapter(groupApdater);
        Log.e("",groups.size()+"");



    }
   public void LoadGroup(){

       FirebaseDatabase database = FirebaseDatabase.getInstance();
       DatabaseReference myRef = database.getReference("Group");
       myRef.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               groups = new ArrayList<>();
               for (DataSnapshot snapshotGroup:snapshot.getChildren()) {
                   Group gr = new Group();
                   gr.setId(snapshotGroup.child("id").getValue(String.class));
                   gr.setName(snapshotGroup.child("name").getValue(String.class));
                   gr.setAdmin(snapshotGroup.child("admin").getValue(User.class));
                   DataSnapshot memberData = snapshotGroup.child("member");
                   List<User> memberGroups = new ArrayList<>();
                    boolean kt = false;
                   for (DataSnapshot dataSnapshot: memberData.getChildren()) {

                           User memberGroup = dataSnapshot.getValue(User.class);

                           memberGroups.add(memberGroup);
                           if(memberGroup.getId().equals(user.getId())){
                               kt = true;
                           }

                   }
                   gr.setMember(memberGroups);


                   if(kt){
                       groups.add(gr);
                   }
               }
               setAdapter();
               groupApdater.notifyDataSetChanged();

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });

    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.menu_group,menu);
        super.onCreateContextMenu(menu, v, menuInfo);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo i = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.menu_member:
                Intent intent = new Intent(getContext(),MemberActivity.class);
                intent.putExtra("groupId",groups.get(i.position).getId());
                intent.putExtra("user",(Serializable) user);
                startActivity(intent);
                break;
            case R.id.menu_task:
                Intent intent1 = new Intent(getContext(),Task_Group.class);
                intent1.putExtra("groupId",groups.get(i.position).getId());
                intent1.putExtra("user",(Serializable) user);
                startActivity(intent1);

        }
        return super.onContextItemSelected(item);
    }
}