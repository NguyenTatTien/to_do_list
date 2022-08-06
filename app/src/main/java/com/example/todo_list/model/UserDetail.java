package com.example.todo_list.model;

import java.util.List;

public class UserDetail {
    User user;
    List<Nofication> noficationList;
    List<Task> tasks;

    public UserDetail(){}
    public UserDetail(User user, List<Nofication> noficationList,List<Task> tasks) {
        this.user = user;
        this.noficationList = noficationList;
        this.tasks =tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Nofication> getNoficationList() {
        return noficationList;
    }

    public void setNoficationList(List<Nofication> noficationList) {
        this.noficationList = noficationList;
    }
}
