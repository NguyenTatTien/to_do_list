package com.example.todo_list;

import java.util.List;

public class Group {
    String Id;
    String Name;
    User Admin;

    public Group(){}
    public Group(String id, String name, User admin) {
        this.Id = id;
        this.Name = name;
        this.Admin = admin;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public User getAdmin() {
        return Admin;
    }

    public void setAdmin(User admin) {
        this.Admin = admin;
    }
}
