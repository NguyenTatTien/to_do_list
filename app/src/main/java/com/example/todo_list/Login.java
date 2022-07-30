package com.example.todo_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class Login extends AppCompatActivity {

    Button btnLogin;
    EditText email,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btn_login);
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.pasword_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("User");
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean kt = false;
                        for(DataSnapshot snapshotUser:snapshot.getChildren()){
                            User user = snapshotUser.getValue(User.class);

                            assert user != null;
                            if(email.getText().toString().equals(user.getEmail())&&password.getText().toString().equals(user.getPassword())){
                                kt = true;
                                Intent intent = new Intent(Login.this,Main__ToDo.class);
                                intent.putExtra("user", (Serializable) user);
                                startActivity(intent);
                                break;
                            }
                        }
                        if(!kt){
                            Toast.makeText(Login.this,"Account already exists!",Toast.LENGTH_LONG).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }
}