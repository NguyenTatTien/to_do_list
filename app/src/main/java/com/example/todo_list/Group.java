package com.example.todo_list;

import java.util.List;

public class Group {
    String Id;
    String Name;
    User Admin;
    List<MemberGroup> member;

    public Group(){}

    public Group(String id, String name, User admin, List<MemberGroup> member) {
        Id = id;
        Name = name;
        Admin = admin;
        this.member = member;
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

    public List<MemberGroup> getMember() {
        return member;
    }

    public void setMember(List<MemberGroup> member) {
        this.member = member;
    }
}
