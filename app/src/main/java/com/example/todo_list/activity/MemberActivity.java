package com.example.todo_list.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.todo_list.adapter.MemberAdapter;
import com.example.todo_list.model.Nofication;
import com.example.todo_list.R;
import com.example.todo_list.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MemberActivity extends AppCompatActivity {
    EditText search_member;
    Button btn_add_member;
    ListView listView;
    MemberAdapter memberAdapter;
    List<User> member;
    User userLogin;
    User newMember;
    String nameGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        Intent intent = getIntent();
        String groupId = intent.getStringExtra("groupId");
        nameGroup = "";
        userLogin = (User) intent.getSerializableExtra("user");
        search_member = findViewById(R.id.seach_member);
        btn_add_member = findViewById(R.id.btn_add_member);
        getListUser(groupId);
        listView = findViewById(R.id.list_member);
        setAdapter();
        btn_add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(search_member.getText().toString().equals("")){
                    Toast.makeText(MemberActivity.this,"Please enter email!",Toast.LENGTH_LONG).show();
                }
                else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("User");
                    myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean kt = false;
                            for (DataSnapshot userSanshop: snapshot.getChildren()) {
                                User user = new User(userSanshop.child("id").getValue(String.class),userSanshop.child("name").getValue(String.class),userSanshop.child("email").getValue(String.class),userSanshop.child("password").getValue(String.class));

                                assert user != null;
                                if(search_member.getText().toString().equals(user.getEmail())){
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef2 = database.getReference("Group").child(groupId).child("member");
                                    myRef2.child(user.getId()).setValue(user);
                                    getListUser(groupId);
                                    setAdapter();
                                    kt = true;
                                    newMember = user;

                                    sendNofication(groupId);
                                    break;
                                }
                            }
                            if(!kt){
                                Toast.makeText(MemberActivity.this,"Email not found!",Toast.LENGTH_LONG).show();
                            }
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }
        });



    }

    public void sendNofication(String groupId){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Toast.makeText(MemberActivity.this,groupId,Toast.LENGTH_LONG).show();

        DatabaseReference myRef = database.getReference("Group");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()) {
                    String id = snap.child("id").getValue(String.class);

                    assert id != null;
                    if(id.equals(groupId))
                    {
                        nameGroup = snap.child("name").getValue(String.class);
                        FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                        DatabaseReference myRef2 = database.getReference("User").child(newMember.getId()).child("notification");
                        DatabaseReference noficationRef = myRef2.push();
                        //Toast.makeText(MemberActivity.this,nameGroup,Toast.LENGTH_LONG).show();
                        noficationRef.setValue(new Nofication(noficationRef.getKey(),"Thông báo","Bạn đã được thêm vào nhóm "+nameGroup,false));

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    public void setAdapter(){
        memberAdapter = new MemberAdapter(this,R.layout.custom_list_member,member);
        listView.setAdapter(memberAdapter);
    }
    public void getListUser(String groupId){
        member = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Group").child(groupId).child("member");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot snapshot1:snapshot.getChildren()){
                    User user = snapshot1.getValue(User.class);
                    Log.e("fdsfsdfdsf",user.getId());
                    member.add(user);

                }
                memberAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}