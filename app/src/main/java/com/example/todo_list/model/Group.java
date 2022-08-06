package com.example.todo_list.model;

import java.util.List;

public class Group {
   public String Id;
   public String Name;
   public User Admin;
   public  List<User> member;
   public List<Task> tasks;
    public Group(){}

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Group(String id, String name, User admin, List<User> member, List<Task> tasks) {
        Id = id;
        Name = name;
        Admin = admin;
        this.member = member;
        this.tasks = tasks;
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

    public User getAdmin() {
        return Admin;
    }

    public void setAdmin(User admin) {
        Admin = admin;
    }

    public List<User> getMember() {
        return member;
    }

    public void setMember(List<User> member) {
        this.member = member;
    }
}
