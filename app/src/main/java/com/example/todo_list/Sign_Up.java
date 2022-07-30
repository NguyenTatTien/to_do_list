package com.example.todo_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Sign_Up extends AppCompatActivity {

    EditText edUser,edEmail,edPassword,edConfirmPassword;
    Button btnSignUp;
    TextView txtSignIn;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edUser = findViewById(R.id.user_name_signup);
        edPassword = findViewById(R.id.password_signup);
        edEmail =findViewById(R.id.email_signup);
        edConfirmPassword = findViewById(R.id.pasword_confirm);
        btnSignUp = findViewById(R.id.btn_sign_up);
        txtSignIn = findViewById(R.id.txt_sign_in);
        edEmail.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(edUser.getText().toString().equals("")||edEmail.getText().toString().equals("")||edPassword.getText().toString().equals("")||edConfirmPassword.getText().toString().equals("")){
                   Toast.makeText(Sign_Up.this,"You have not entered all the information",Toast.LENGTH_LONG).show();
               }
               else {
                   if(edPassword.getText().toString().equals(edConfirmPassword.getText().toString())){
                       FirebaseDatabase database = FirebaseDatabase.getInstance();
                       DatabaseReference myRef = database.getReference("User");
                       myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot snapshot) {
                               List<User> users = new ArrayList<>();
                               for(DataSnapshot snapshot1: snapshot.getChildren()){
                                   User user = new User();
                                   user = snapshot1.getValue(User.class);
                                   users.add(user);
                               }
                               Boolean kt= false;
                               for (User user:users) {
                                   if(edEmail.getText().toString().equals(user.getEmail())){
                                       kt = true;
                                   }
                                   else {
                                       kt = false;
                                   }
                               }
                               if(kt){
                                   Toast.makeText(Sign_Up.this,"Email already exists",Toast.LENGTH_LONG).show();
                               }
                               else{
                                   int id = 1123785;
                                   id +=snapshot.getChildrenCount();
                                   User user = new User("lll",edUser.getText().toString(),edEmail.getText().toString(),edPassword.getText().toString());
                                   DatabaseReference reference =  myRef.push();
                                   reference.child("Id").setValue(reference.getKey());
                                   reference.child("Name").setValue(user.getName());
                                   reference.child("Email").setValue(user.getEmail());
                                   reference.child("Password").setValue(user.getPassword());

                                   Toast.makeText(Sign_Up.this,"Add user",Toast.LENGTH_LONG).show();
                               }


                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError error) {

                           }
                       });
                   }
                   else{
                       Toast.makeText(Sign_Up.this, "Passwor don't confirm password!", Toast.LENGTH_SHORT).show();
                   }


               }
            }
        });
    }
}