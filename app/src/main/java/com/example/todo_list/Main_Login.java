package com.example.todo_list;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main_Login extends AppCompatActivity {
    Button login_main,sign_up_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        login_main = (Button) findViewById(R.id.btnlogin_main);
        sign_up_main = (Button) findViewById(R.id.sign_up_main);
        login_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_Login.this, Login.class);
                startActivity(intent);
            }
        });
        sign_up_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_Login.this, Sign_Up.class);
                startActivity(intent);
            }
        });
    }
}