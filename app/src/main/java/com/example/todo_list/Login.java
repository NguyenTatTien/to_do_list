package com.example.todo_list;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Login extends AppCompatActivity {

    Button btnLogin;
    EditText email,password;
    AlarmManager alarmManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.btn_login);
        email = findViewById(R.id.email_login);
        password = findViewById(R.id.pasword_login);
        UserDetail userDetail= new UserDetail();
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("User");

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean kt = false;
                        for(DataSnapshot snapshotUser:snapshot.getChildren()){

                            userDetail.setUser(new User(snapshotUser.child("id").getValue(String.class),snapshotUser.child("name").getValue(String.class),snapshotUser.child("email").getValue(String.class),snapshotUser.child("password").getValue(String.class)));

                            assert userDetail != null;
                            if(email.getText().toString().equals(userDetail.getUser().getEmail())&&password.getText().toString().equals(userDetail.getUser().getPassword())){
                                kt = true;
                                DataSnapshot snapNo = snapshotUser.child("notification");
                                List<Nofication> noficationList = new ArrayList<>();
                                for (DataSnapshot snapNo1: snapNo.getChildren()) {

                                    noficationList.add(snapNo1.getValue(Nofication.class));
                                }
                                userDetail.setNoficationList(noficationList);
                                User user = userDetail.getUser();
                                Intent intent = new Intent(Login.this,Main__ToDo.class);
                                intent.putExtra("user", (Serializable) user);
                                startActivity(intent);
                                runServices(user);
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

                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(userDetail.getUser()!=null){
                            loadNofication(userDetail);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });



    }
    public void runServices(User user){
        Log.e("login","ffdsfsdfsd");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Group");
        myRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                String strDate = "";
                Date dateTask = null;
                List<AlarmManager> alarmManagers = new ArrayList<>();
                List<Task> tasks = new ArrayList<>();
                Date dateNow = Calendar.getInstance().getTime();
                for (DataSnapshot snap: snapshot.getChildren()) {

                    User getuser = snap.child("member").child(user.getId()).getValue(User.class);
                    if(getuser!=null){
                        Log.e("login3","ffdsfsdfsd");
                        DataSnapshot taskRef = snap.child("task");
                        for (DataSnapshot snapTask: taskRef.getChildren()) {
                            Task task = snapTask.getValue(Task.class);
                            Log.e("login4","ffdsfsdfsd");
                            if(!task.isCheck()){

                                try {
                                    strDate = task.getRemind();
                                    dateTask = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(strDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                assert dateTask != null;

                                if(dateNow.toInstant().isBefore(dateTask.toInstant())){
                                    Log.e("login6","ffdsfsdfsd");
                                    AlarmManager  alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                                    tasks.add(task);
                                    alarmManagers.add(alarmManager);

                                }

                            }

                        }

                    }
                }
                for (int i = 0;i<tasks.size();i++) {

                    String dayTask = tasks.get(i).getRemind();

                        Date time = null;
                        try {
                         time =  new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").parse(strDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent(Login.this,BroadRececiver.class);
                       // intent.putExtra("listGroupId",(Serializable) listGroupId);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(Login.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    assert time != null;
                    alarmManagers.get(i).set(AlarmManager.RTC_WAKEUP,time.getTime(),pendingIntent);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void loadNofication(UserDetail user){

        for (Nofication nofication: user.getNoficationList()) {
            if(!nofication.isStatus()){

                int NOTIFICATION_ID = 234;
                NotificationManager notificationManager = (NotificationManager) Login.this.getSystemService(Context.NOTIFICATION_SERVICE);
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
                NotificationCompat.Builder builder = new NotificationCompat.Builder(Login.this, CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(nofication.getTitle())
                        .setContentText(nofication.getContent());

                Intent resultIntent = new Intent(Login.this, MainActivity.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(Login.this);
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                builder.setContentIntent(resultPendingIntent);
                notificationManager.notify(NOTIFICATION_ID, builder.build());
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("User").child(user.getUser().getId()).child("notification").child(nofication.getId()).child("status");
                myRef.setValue(true);
            }
        }
    }
}