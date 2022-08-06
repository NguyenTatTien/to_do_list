package com.example.todo_list.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todo_list.model.Nofication;
import com.example.todo_list.R;
import com.example.todo_list.model.User;
import com.example.todo_list.model.UserDetail;
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
                                   List<Nofication> noficationList = new ArrayList<>();
                                   UserDetail user = new UserDetail(new User("lll",edUser.getText().toString(),edEmail.getText().toString(),edPassword.getText().toString()),noficationList,null);
                                   DatabaseReference reference =  myRef.push();
                                   reference.child("id").setValue(reference.getKey());
                                   reference.child("name").setValue(user.getUser().getName());
                                   reference.child("email").setValue(user.getUser().getEmail());
                                   reference.child("password").setValue(user.getUser().getPassword());
                                   DatabaseReference noficationRef =  reference.child("notification");
                                   DatabaseReference child = noficationRef.push();
                                   child.setValue(new Nofication(child.getKey(),"Thông báo","Đăng ký thành công",false));

                                   int NOTIFICATION_ID = 234;
                                   NotificationManager notificationManager = (NotificationManager) Sign_Up.this.getSystemService(Context.NOTIFICATION_SERVICE);
                                   String CHANNEL_ID = "";
                                   if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                       CHANNEL_ID = "my_channel_01";
                                       CharSequence name = "my_channel";
                                       String Description = "This is my channel";
                                       int importance = NotificationManager.IMPORTANCE_HIGH;
                                       NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
                                       mChannel.setDescription(Description);
                                       notificationManager.createNotificationChannel(mChannel);
                                   }
                                   NotificationCompat.Builder builder = new NotificationCompat.Builder(Sign_Up.this, CHANNEL_ID)
                                           .setSmallIcon(R.mipmap.ic_launcher)
                                           .setContentTitle("Thông báo")
                                           .setLargeIcon(BitmapFactory.decodeResource( getResources(), R.drawable.message))
                                           .setContentText("Đăng ký thành công!");

                                   Intent resultIntent = new Intent(Sign_Up.this, MainActivity.class);
                                   TaskStackBuilder stackBuilder = TaskStackBuilder.create(Sign_Up.this);
                                   stackBuilder.addParentStack(MainActivity.class);
                                   stackBuilder.addNextIntent(resultIntent);
                                   PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                                   builder.setContentIntent(resultPendingIntent);
                                   notificationManager.notify(NOTIFICATION_ID, builder.build());
                                   FirebaseDatabase database = FirebaseDatabase.getInstance();
                                   DatabaseReference myRef = database.getReference("User").child(reference.getKey()).child("notification").child(child.getKey()).child("status");
                                   myRef.setValue(true);
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