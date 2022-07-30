package com.example.todo_list;

import java.io.Serializable;

public class User implements Serializable {
    private String Id;
    private String  Name;
    private String Email;
    private String Password;
    public User(){

    }
    public User(String id, String name, String email, String password) {
        Id = id;
        Name = name;
        Email = email;
        Password = password;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
